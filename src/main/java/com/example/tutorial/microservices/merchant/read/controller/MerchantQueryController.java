package com.example.tutorial.microservices.merchant.read.controller;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.merchant.Merchant;
import com.example.tutorial.microservices.merchant.read.service.MerchantQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/merchants")
public class MerchantQueryController {

  private static final Logger log = LoggerFactory.getLogger(MerchantQueryController.class);

  @Autowired
  private MerchantQueryService merchantQueryService;

  /**
   * Retrieves all merchants.
   *
   * @return a list of all merchants wrapped in BaseDto.
   */
  @GetMapping
  public ResponseEntity<List<BaseDto<Merchant>>> getAllMerchants() {
    List<BaseDto<Merchant>> allMerchants = merchantQueryService.getAllMerchants();
    log.info("Total merchants found: {}", allMerchants.size());
    return ResponseEntity.ok(allMerchants);
  }

  /**
   * Retrieves a merchant by its ID.
   *
   * @param id the ID of the merchant to retrieve.
   * @return the merchant wrapped in BaseDto if found, or a 404 Not Found response if not found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<BaseDto<Merchant>> getMerchantById(@PathVariable String id) {
    Optional<BaseDto<Merchant>> merchantOptional = merchantQueryService.getMerchantById(id);
    if (merchantOptional.isPresent()) {
      log.info("Merchant with ID: {} found", id);
      return ResponseEntity.ok(merchantOptional.get());
    } else {
      log.warn("Merchant with ID: {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }

  // -- Additional Endpoints for supporting operations  -- //
  @PostMapping("/by-ids")
  public ResponseEntity<List<BaseDto<Merchant>>> getMerchantsByIds(@RequestBody List<String> ids) {
    List<BaseDto<Merchant>> merchants = new ArrayList<BaseDto<Merchant>>();
    for (String id : ids) {
      merchants.add(getMerchantById(id).getBody());
    }

    log.info("Total merchants found by IDs: {}", merchants.size());
    return ResponseEntity.ok(merchants);
  }

}
