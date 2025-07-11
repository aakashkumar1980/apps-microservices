package com.example.tutorial.microservices.offer.write.service;

import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.common.dto.offer.OfferStatus;
import com.example.tutorial.microservices.offer.write.repository.OfferCommandRepository;
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

  @Autowired
  private OfferCommandRepository offerCommandRepository;

  @Autowired
  private CouchbaseTemplate couchbaseTemplate;

  @Value("${offer.counter.key:offer_counter}")
  private String offerCounterKey;


  /**
   * Deactivates all offers associated with a given campaign ID.
   *
   * @param campaignId the ID of the campaign whose offers are to be deactivated
   */
  public void deactivateOffers(String campaignId) {
    List<Offer> existingOffers = offerCommandRepository.findByCampaignId(campaignId);
    if (existingOffers != null && !existingOffers.isEmpty()) {
      existingOffers.forEach(offer -> {
        offer.setOfferStatus(OfferStatus.INACTIVE);
        couchbaseTemplate.save(offer);
      });
    }
  }

}
