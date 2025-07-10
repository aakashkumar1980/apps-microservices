package com.example.tutorial.microservices.merchant.read.service;

import com.example.tutorial.microservices.merchant.read.repository.MerchantQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantQueryService {

  @Autowired
  private MerchantQueryRepository merchantQueryRepository;

}
