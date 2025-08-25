package com.example.tutorial.microservices.offer.read.controller;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.offer.Offer;
import com.example.tutorial.microservices.offer.read.service.OfferQueryService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offers")
public class OfferQueryController {

  private static final Logger log = LoggerFactory.getLogger(OfferQueryController.class);

  @Autowired
  private OfferQueryService offerQueryService;

  /**
   * Fetches all offers available in the system.
   * @return ResponseEntity containing a list of BaseDto<Offer> objects.
   */
  @GetMapping
  public ResponseEntity<List<BaseDto<Offer>>> getAllOffers() {
    List<BaseDto<Offer>> allOffers = offerQueryService.getAllOffers();
    log.info("Total offers found: {}", allOffers.size());
    return ResponseEntity.ok(allOffers);
  }

  /**
   * Fetches an offer by its ID.
   *
   * @param id the ID of the offer to retrieve.
   * @return ResponseEntity containing the BaseDto<Offer> object if found, or a 404 Not Found status if not found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<BaseDto<Offer>> getOfferById(@PathVariable String id) {
    Optional<BaseDto<Offer>> offerOptional = offerQueryService.getOfferById(id);
    if (offerOptional.isPresent()) {
      log.info("Offer with ID: {} found", id);
      return ResponseEntity.ok(offerOptional.get());
    } else {
      log.warn("Offer with ID: {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Fetches all offers associated with a specific campaign ID.
   *
   * @param campaignId the ID of the campaign to filter offers by.
   * @return ResponseEntity containing a list of BaseDto<Offer> objects associated with the specified campaign ID.
   */
  @GetMapping("/campaign/{campaignId}")
  public ResponseEntity<List<BaseDto<Offer>>> getOffersByCampaignId(@PathVariable String campaignId) {
    List<BaseDto<Offer>> offersByCampaign = offerQueryService.getOffersByCampaignId(campaignId);
    if (CollectionUtils.isNotEmpty(offersByCampaign)) {
      log.info("Found {} offers for campaign ID: {}", offersByCampaign.size(), campaignId);
      return ResponseEntity.ok(offersByCampaign);

    } else {
      log.warn("No offers found for campaign ID: {}", campaignId);
      return ResponseEntity.noContent().build();
    }
  }
}
