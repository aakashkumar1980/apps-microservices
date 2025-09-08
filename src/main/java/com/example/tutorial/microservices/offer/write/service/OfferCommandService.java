package com.example.tutorial.microservices.offer.write.service;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.offer.Offer;
import com.example.tutorial.common.exceptions.ApplicationFunctionalException;
import com.example.tutorial.microservices.offer.write.repository.OfferCommandRepository;
import com.example.tutorial.common.utils.validation.OfferValidation;
import com.example.tutorial.common.utils.validation.MerchantValidation;
import com.example.tutorial.common.utils.validation.CampaignValidation;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.CompositeFuture;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class OfferCommandService {
  private static final Logger log = LoggerFactory.getLogger(OfferCommandService.class);

  private final Vertx vertx;

  public OfferCommandService(Vertx vertx) {
    this.vertx = vertx;
  }

  /**
   * Create a new offer after validating the input data.
   *
   * @param offer The offer to be created.
   * @return A Future containing an Optional of BaseDto with the created offer,
   * or an empty Optional if creation failed.
   */
  public Future<Optional<BaseDto<Offer>>> createOffer(Offer offer) {
    log.info("[START] Creating offer: {}", offer);

    /** **************** **/
    /** DATA VALIDATIONS **/
    /** **************** **/

    /** Validate Merchant ID and Campaign ID in sequence using "compose" function.
     *  The syntax of "compose" is:
     *  futureA.compose(resultA -> {
     *      return futureB;
     *    }
     *  )
     * **/
    Future<List<String>> merchantAndCampaignValidationFuture =
        new MerchantValidation().validateMerchantIdAsync(offer.getMerchantId())
            .compose(merchantErrors -> {
              if (!merchantErrors.isEmpty()) {
                // If merchant validation fails, return only merchant errors
                return Future.succeededFuture(merchantErrors);
              }
              // If merchant validation passes, validate campaign and combine errors, use "map" to collect results of both validations
              return new CampaignValidation().validateCampaignIdAsync(offer.getCampaignId())
                  .map(campaignErrors -> {
                    List<String> allErrors = new ArrayList<>(merchantErrors);
                    allErrors.addAll(campaignErrors);
                    return allErrors;
                  });
            });

    /** Validate Offer data in parallel with Merchant and Campaign
     *  Using "CompositeFuture.all" to run both validations concurrently.
     *  Then use "compose" to handle the combined results.
     * */
    Future<List<String>> offerValidationFuture =
        new OfferValidation().validateCreateAsync(offer);
    return CompositeFuture.all(offerValidationFuture, merchantAndCampaignValidationFuture)
        .compose(composite -> {
          // Collect all errors
          List<String> allErrors = new ArrayList<>();
          allErrors.addAll(composite.resultAt(0)); // offer errors
          allErrors.addAll(composite.resultAt(1)); // merchant + campaign errors
          if (CollectionUtils.isNotEmpty(allErrors)) {
            return Future.failedFuture(new ApplicationFunctionalException("Validation failed", allErrors));
          }

          /** **************** **/
          /** DATA PERSISTENCE **/
          /** **************** **/
          var repository = new OfferCommandRepository(vertx);
          log.info("[RETURN] Creating offer");
          return repository.createOffer(offer);
        });
  }
}
