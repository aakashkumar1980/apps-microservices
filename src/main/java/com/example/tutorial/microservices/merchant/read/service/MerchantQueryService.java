package com.example.tutorial.microservices.merchant.read.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.merchant.Merchant;
import com.example.tutorial.microservices.merchant.read.repository.MerchantQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MerchantQueryService {

  private static final Logger log = LoggerFactory.getLogger(MerchantQueryService.class);

  @Autowired
  private MerchantQueryRepository merchantQueryRepository;

  /**
   * Returns all merchants.
   */
  public List<BaseDto<Merchant>> getAllMerchants() {
    log.info("Fetching all merchants from the repository");
    return merchantQueryRepository.getAllMerchants();
  }

  /**
   * Returns a merchant by its ID.
   */
  public Optional<BaseDto<Merchant>> getMerchantById(String id) {
    log.info("Fetching merchant with ID: {}", id);
    return merchantQueryRepository.getMerchantById(id);
  }
}

