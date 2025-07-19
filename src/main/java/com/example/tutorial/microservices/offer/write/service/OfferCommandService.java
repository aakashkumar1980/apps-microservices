package com.example.tutorial.microservices.offer.write.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.common.dto.offer.OfferStatus;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.DBUtils;
import com.example.tutorial.common.utils.validation.CampaignValidation;
import com.example.tutorial.microservices.offer.write.repository.OfferCommandRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.redis.core.RedisTemplate;
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
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private APIUtils apiUtils;

  @Autowired
  private DBUtils dbUtils;

  @Value("${campaigns.api.url}")
  private String campaignsApiUrl;

  @Autowired
  private CampaignValidation campaignValidation;

  /**
   * Creates a new offer and saves it to the repository.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param offer the offer to be created
   * @return the ID of the created offer
   */
  public String createOffer(Offer offer) {
    log.info("Creating offer: {}", offer);

    /** DATA VALIDATION **/
    // Validate that the offer has a valid campaign
    campaignValidation.validateCampaign(offer.getCampaignId(), campaignsApiUrl);

    // generate a unique ID for the offer using a counter
    String id = "offer::" + dbUtils.getUniqueCounter(couchbaseTemplate, offerCounterKey);
    // build the BaseDto for the offer with default values
    BaseDto<Offer> baseDto = BaseDto.build(offer);
    baseDto.setId(id);
    // Save the offer to the repository
    BaseDto<Offer> savedDto = offerCommandRepository.save(baseDto);

    return savedDto.getId();
  }


  /**
   * Deactivates all offers associated with a given campaign ID.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param campaignId the ID of the campaign whose offers are to be deactivated
   */
  public void deactivateOffers(String campaignId) {
    log.info("Deactivating offers for campaign ID: {}", campaignId);

    // Retrieve all offers associated with the given campaign ID
    List<BaseDto<Offer>> offers = offerCommandRepository.getOffersByCampaignId(campaignId);
    if (offers != null && !offers.isEmpty()) {
      // Iterate through the filtered offers and set their status to INACTIVE
      offers.forEach(baseDto -> {
        baseDto.getData().setStatus(OfferStatus.INACTIVE);

        log.info("Deactivating offer with ID: {}", baseDto.getId());
        offerCommandRepository.save(baseDto);
      });

    } else {
      log.warn("No offers found for campaign ID: {}", campaignId);
    }
  }

}
