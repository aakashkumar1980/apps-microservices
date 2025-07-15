package com.example.tutorial.microservices.offer.read.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
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
   *
   * @return a list of BaseDto containing Offer objects.
   */
  public List<BaseDto<Offer>> getAllOffers() {
    log.info("Fetching all offers from the repository");
    return offerQueryRepository.getAllOffers();
  }

  /**
   * Retrieves an offer by its ID from the repository.
   *
   * @param id the ID of the offer to retrieve.
   * @return a BaseDto containing the Offer object if found, or null if not found.
   */
  public Optional<BaseDto<Offer>> getOfferById(String id) {
    log.info("Fetching offer with ID: {}", id);
    return offerQueryRepository.getOfferById(id);
  }

  /**
   * Retrieves all offers associated with a specific campaign ID.
   *
   * @param campaignId the ID of the campaign to filter offers by.
   * @return a list of BaseDto containing Offer objects associated with the specified campaign ID.
   */
  public List<BaseDto<Offer>> getOffersByCampaignId(String campaignId) {
    log.info("Fetching offers for campaign ID: {}", campaignId);
    return offerQueryRepository.getOffersByCampaignId(campaignId);
  }
}
