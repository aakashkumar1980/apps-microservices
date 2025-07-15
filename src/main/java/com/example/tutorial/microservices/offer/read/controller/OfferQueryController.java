package com.example.tutorial.microservices.offer.read.controller;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.microservices.offer.read.service.OfferQueryService;
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
    log.info("Fetching all offers");
    return ResponseEntity.ok(offerQueryService.getAllOffers());
  }

  /**
   * Fetches an offer by its ID.
   *
   * @param id the ID of the offer to retrieve.
   * @return ResponseEntity containing the BaseDto<Offer> object if found, or a 404 Not Found status if not found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<BaseDto<Offer>> getOfferById(@PathVariable String id) {
    log.info("Fetching offer with ID: {}", id);
    Optional<BaseDto<Offer>> offer = offerQueryService.getOfferById(id);
    if (offer.isPresent()) {
      return ResponseEntity.ok(offer.get());
    } else {
      log.warn("Offer with ID: {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }

}
