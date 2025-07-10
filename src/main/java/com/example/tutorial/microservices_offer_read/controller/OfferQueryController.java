package com.example.tutorial.microservices_offer_read.controller;

import com.example.tutorial.microservices_offer_read.service.OfferQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/offers")
public class OfferQueryController {

  @Autowired
  private OfferQueryService offerQueryService;

}
