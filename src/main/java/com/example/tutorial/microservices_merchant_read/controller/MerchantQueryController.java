package com.example.tutorial.microservices_merchant_read.controller;

import com.example.tutorial.microservices_merchant_read.service.MerchantQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchants")
public class MerchantQueryController {

  @Autowired
  private MerchantQueryService merchantQueryService;

}
