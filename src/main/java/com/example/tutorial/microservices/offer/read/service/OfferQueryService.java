package com.example.tutorial.microservices.offer.read.service;

import com.example.tutorial.microservices.offer.read.repository.OfferQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferQueryService {

  @Autowired
  private OfferQueryRepository offerQueryRepository;

}
