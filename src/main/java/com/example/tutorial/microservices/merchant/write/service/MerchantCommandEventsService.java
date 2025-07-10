package com.example.tutorial.microservices.merchant.write.service;

import com.example.tutorial.microservices.merchant.write.repository.MerchantCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

@Service
public class  MerchantCommandEventsService {

  @Autowired
  private MerchantCommandRepository merchantCommandRepository;

  @Autowired
  private CouchbaseTemplate couchbaseTemplate;

  @Value("${merchant.counter.key:merchant_counter}")
  private String merchantCounterKey;

}
