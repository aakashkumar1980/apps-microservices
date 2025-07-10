package com.example.tutorial.microservices.merchant.read.controller;

import com.example.tutorial.microservices.merchant.read.service.MerchantQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/merchants")
public class MerchantQueryController {

  @Autowired
  private MerchantQueryService merchantQueryService;

}
