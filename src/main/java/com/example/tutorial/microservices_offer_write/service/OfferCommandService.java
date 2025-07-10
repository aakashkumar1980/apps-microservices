package com.example.tutorial.microservices_offer_write.service;

import com.example.tutorial.microservices_offer_write.repository.OfferCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

@Service
public class OfferCommandService {

  @Autowired
  private OfferCommandRepository offerCommandRepository;

  @Autowired
  private CouchbaseTemplate couchbaseTemplate;

  @Value("${offer.counter.key:offer_counter}")
  private String offerCounterKey;

 
}
