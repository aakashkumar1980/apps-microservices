package com.example.tutorial.microservices.offer.read.controller;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.microservices.offer.read.service.OfferQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class OfferQueryController {

  @Autowired
  private OfferQueryService offerQueryService;

  /**
   * Fetches all offers available in the system.
   * @return ResponseEntity containing a list of BaseDto<Offer> objects.
   */
  @GetMapping
  public ResponseEntity<List<BaseDto<Offer>>> getAllOffers() {
    return ResponseEntity.ok(offerQueryService.getAllOffers());
  }

}
