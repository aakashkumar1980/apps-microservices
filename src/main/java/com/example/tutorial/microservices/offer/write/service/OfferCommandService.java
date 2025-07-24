package com.example.tutorial.microservices.offer.write.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.common.dto.offer.OfferStatus;
import com.example.tutorial.common.utils.DBUtils;
import com.example.tutorial.common.utils.validation.CampaignValidation;
import com.example.tutorial.microservices.offer.write.repository.OfferCommandRepository;
import com.example.tutorial.microservices.offer.write.service.events.publisher.OfferEventPublisher;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing offer commands, including deactivating offers associated with a campaign.
 */
@Service
public class OfferCommandService {

  private static final Logger log = LoggerFactory.getLogger(OfferCommandService.class);

  @Autowired
  private OfferCommandRepository offerCommandRepository;

  @Value("${offer.counter.key:offer_counter}")
  private String offerCounterKey;

  @Autowired
  private CouchbaseTemplate couchbaseTemplate;

  @Autowired
  private DBUtils dbUtils;

  @Value("${campaigns.api.url}")
  private String campaignsApiUrl;

  @Autowired
  private CampaignValidation campaignValidation;

  @Autowired
  private OfferEventPublisher offerEventPublisher;

  /**
   * Creates a new offer and saves it to the repository.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param offer the offer to be created
   * @return the ID of the created offer
   */
  public String createOffer(Offer offer) {
    log.info("Creating offer: {}", offer);
    // build the BaseDto for the offer with default values
    BaseDto<Offer> baseOffer = BaseDto.build(offer);

    /** DATA VALIDATION **/
    // Validate that the offer has a valid campaign
    campaignValidation.validateCampaign(offer.getCampaignId(), campaignsApiUrl);

    /** PERSIST DATA **/
    // generate a unique ID for the offer using a counter
    String id = "offer::" + dbUtils.getUniqueCounter(couchbaseTemplate, offerCounterKey);
    baseOffer.setId(id);
    // Save the offer to the repository
    BaseDto<Offer> savedOffer = offerCommandRepository.save(baseOffer);

    /** PUBLISH EVENTS **/
    // Publish an event for the created offer
    offerEventPublisher.publishCreateOfferEvent(savedOffer);

    return savedOffer.getId();
  }

  /**
   * Cancels all offers associated with a given campaign ID.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param campaignId the ID of the campaign whose offers are to be cancelled
   */
  public void cancelOffers(String campaignId) {
    log.info("Cancelling offers for campaign ID: {}", campaignId);

    /** PERSIST DATA **/
    // Retrieve all offers associated with the given campaign ID
    List<BaseDto<Offer>> offersByCampaign = offerCommandRepository.getOffersByCampaignId(campaignId);
    if (CollectionUtils.isNotEmpty(offersByCampaign)) {
      // Iterate through the filtered offers and set their status to INACTIVE
      offersByCampaign.forEach(offer -> {
        offer.getData().setStatus(OfferStatus.CANCELLED);

        log.info("Cancelling offer with ID: {}", offer.getId());
        offerCommandRepository.save(offer);
      });

    } else {
      log.warn("No offers found for campaign ID: {}", campaignId);
    }
  }

}
