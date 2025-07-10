package com.example.tutorial.microservices_offer_read.service;

import com.example.tutorial.microservices_offer_read.repository.OfferQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferQueryService {

  @Autowired
  private OfferQueryRepository offerQueryRepository;

}
