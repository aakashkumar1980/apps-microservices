package com.example.tutorial.microservices.merchant.read.controller;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.merchant.Merchant;
import com.example.tutorial.microservices.merchant.read.service.MerchantQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/merchants")
public class MerchantQueryController {

  private static final Logger log = LoggerFactory.getLogger(MerchantQueryController.class);

  @Autowired
  private MerchantQueryService merchantQueryService;

  /**
   * Fetches all merchants available in the system.
   * @return ResponseEntity containing a list of BaseDto<Merchant> objects.
   */
  @GetMapping
  public ResponseEntity<List<BaseDto<Merchant>>> getAllMerchants() {
    log.info("Fetching all merchants");
    return ResponseEntity.ok(merchantQueryService.getAllMerchants());
  }

}
