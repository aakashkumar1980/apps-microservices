package com.example.tutorial.microservices_merchant_read.service;

import com.example.tutorial.microservices_merchant_read.repository.MerchantQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantQueryService {

  @Autowired
  private MerchantQueryRepository merchantQueryRepository;

}
