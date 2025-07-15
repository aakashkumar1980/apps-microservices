package com.example.tutorial.microservices.offer.write.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.common.dto.offer.OfferStatus;
import com.example.tutorial.common.utils.DBUtils;
import com.example.tutorial.microservices.offer.write.repository.OfferCommandRepository;
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

  /**
   * Creates a new offer and saves it to the repository.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param offer the offer to be created
   * @return the ID of the created offer
   */
  public String createOffer(Offer offer) {
    log.info("Creating offer: {}", offer);

    // generate a unique ID for the offer using a counter
    String id = "offer::" + DBUtils.getUniqueCounter(couchbaseTemplate, offerCounterKey);
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

    // Retrieve all offers from the repository
    List<BaseDto<Offer>> allOffers = offerCommandRepository.findAll();
    // Filter offers that match the given campaign ID
    List<BaseDto<Offer>> existingBaseDtoOffers = allOffers.stream()
      .filter(baseDto -> baseDto.getData() != null && campaignId.equals(baseDto.getData().getCampaignId()))
      .toList();
    if (existingBaseDtoOffers != null && !existingBaseDtoOffers.isEmpty()) {
      // Iterate through the filtered offers and set their status to INACTIVE
      existingBaseDtoOffers.forEach(baseDto -> {
        baseDto.getData().setOfferStatus(OfferStatus.INACTIVE);

        log.info("Deactivating offer with ID: {}", baseDto.getId());
        offerCommandRepository.save(baseDto);
      });

    } else {
      log.warn("No offers found for campaign ID: {}", campaignId);
    }
  }

}
