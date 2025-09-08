package com.example.tutorial.common.utils.validation;

import com.example.tutorial.common.datamodel.offer.Offer;
import org.apache.commons.lang3.StringUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OfferValidation {
  private static final Logger log = LoggerFactory.getLogger(OfferValidation.class);

  public OfferValidation() {}

  /**
   * Asynchronously validates the given Offer for creation.
   *
   * @param offer The Offer to validate.
   * @return A Future with a list of validation error messages. Empty if valid.
   */
  public Future<List<String>> validateCreateAsync(Offer offer) {
    return Future.succeededFuture(validateCreate(offer));
  }

  // -- PRIVATE METHODS -- //
  private List<String> validateCreate(Offer offer) {
    log.info("Validating offer: {}", offer);
    List<String> errors = new ArrayList<>();

    if (offer == null) {
      errors.add("Offer payload is required");
      return errors;
    }

    // Basic string fields commonly present on Offer
    if (StringUtils.isBlank(offer.getName())) {
      errors.add("Offer.name is required");
    }
    if (StringUtils.isBlank(offer.getCampaignId())) {
      errors.add("Offer.campaignId is required");
    }
    if (StringUtils.isBlank(offer.getMerchantId())) {
      errors.add("Offer.merchantId is required");
    }

    // Optional temporal checks if your Offer exposes start/to
    LocalDateTime from = offer.getValidFrom();
    LocalDateTime to   = offer.getValidTo();
    if (from != null && to != null && to.isBefore(from)) {
      errors.add("Offer.endDate must be after startDate");
    }

    return errors;
  }
}
