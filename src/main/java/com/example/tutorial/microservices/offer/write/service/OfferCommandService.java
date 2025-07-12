package com.example.tutorial.microservices.offer.write.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.common.dto.offer.OfferStatus;
import com.example.tutorial.microservices.offer.write.repository.OfferCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  /**
   * Deactivates all offers associated with a given campaign ID.
   *
   * @param campaignId the ID of the campaign whose offers are to be deactivated
   */
  public void deactivateOffers(String campaignId) {
    log.info("Deactivating offers for campaign ID: {}", campaignId);

    List<BaseDto<Offer>> existingBaseDtoOffers = offerCommandRepository.findByDataCampaignId(campaignId);
    if (existingBaseDtoOffers != null && !existingBaseDtoOffers.isEmpty()) {
      existingBaseDtoOffers.forEach(baseDto -> {
        baseDto.getData().setOfferStatus(OfferStatus.INACTIVE);

        log.info("Deactivating offer with ID: {}", baseDto.getData().getOfferId());
        offerCommandRepository.save(baseDto);
      });
    }
  }

}
