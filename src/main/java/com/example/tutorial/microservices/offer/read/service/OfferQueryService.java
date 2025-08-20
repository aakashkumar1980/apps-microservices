package com.example.tutorial.microservices.offer.read.service;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.offer.Offer;
import com.example.tutorial.microservices.offer.read.repository.OfferQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferQueryService {

  private static final Logger log = LoggerFactory.getLogger(OfferQueryService.class);

  @Autowired
  private OfferQueryRepository offerQueryRepository;

  /**
   * Retrieves all offers from the repository.
   * NOTE: Here we are not using the CouchbaseRepository's findAll method, because the id for different data models
   * starts like 'campaign::1', 'offer::1', etc. where the prefix is used to identify the type of document.
   * Therefore, it needs a custom query to filter by the prefix.
   *
   * @return a list of BaseDto containing Offer objects.
   */
  public List<BaseDto<Offer>> getAllOffers() {
    List<BaseDto<Offer>> allOffers = offerQueryRepository.getAllOffers();
    log.info("Total offers fetched: {}", allOffers.size());
    return allOffers;
  }

  /**
   * Retrieves an offer by its ID from the repository.
   *
   * @param id the ID of the offer to retrieve.
   * @return a BaseDto containing the Offer object if found, or null if not found.
   */
  public Optional<BaseDto<Offer>> getOfferById(String id) {
    Optional<BaseDto<Offer>> offer = offerQueryRepository.findById(id);
    if (offer.isPresent()) {
      log.info("Offer with ID: {} found", id);
    } else {
      log.warn("Offer with ID: {} not found", id);
    }
    return offer;
  }

  /**
   * Retrieves all offers associated with a specific campaign ID.
   * NOTE: This method uses a custom query to filter offers by campaign ID, as the campaignId is stored inside
   * the data field which is generic and also the id for different data models and starts like 'campaign::1',
   * 'offer::1', etc. Therefore, the filtering should be first on id and then on the campaignId field.
   *
   * @param campaignId the ID of the campaign to filter offers by.
   * @return a list of BaseDto containing Offer objects associated with the specified campaign ID.
   */
  public List<BaseDto<Offer>> getOffersByCampaignId(String campaignId) {
    List<BaseDto<Offer>> offers = offerQueryRepository.getOffersByCampaignId(campaignId);
    log.info("Total offers fetched for campaign ID {}: {}", campaignId, offers.size());
    return offers;
  }
}
