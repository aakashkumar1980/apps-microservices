package com.example.tutorial.microservices.offer.write.controller;

import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.microservices.offer.write.service.OfferCommandService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/offers")
public class OfferCommandController {

  private static final Logger log = LoggerFactory.getLogger(OfferCommandController.class);

  @Autowired
  private OfferCommandService offerCommandService;

  /**
   * Handles the creation of a new offer.
   *
   * @param offer the offer to be created
   * @return a response entity with the ID of the created offer
   */
  @PostMapping
  public ResponseEntity<String> createOffer(@Valid @RequestBody Offer offer) {
    log.info("Received request to create offer: {}", offer);
    String id = offerCommandService.createOffer(offer);
    return ResponseEntity.ok(String.format("Offer created successfully with ID: %s", id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> cancelOffer(@PathVariable String id) {
    log.info("Received request to cancel offer with ID: {}", id);
    //offerCommandService.cancelOffer(id);
    return ResponseEntity.ok("Offer cancelled successfully");
  }

}
