package com.example.tutorial.microservices.offer.read.controller;

import com.example.tutorial.microservices.offer.read.service.OfferQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/offers")
public class OfferQueryController {

  @Autowired
  private OfferQueryService offerQueryService;

}
