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
   * NOTE: Here we are not using the CouchbaseRepository's findAll method, because the id for different data models
   * starts like 'campaign::1', 'offer::1', etc. where the prefix is used to identify the type of document.
   * Therefore, it needs a custom query to filter by the prefix.
   *
   * @return List of BaseDto<Merchant>
   */
  public List<BaseDto<Merchant>> getAllMerchants() {
    List<BaseDto<Merchant>> allMerchants = merchantQueryRepository.getAllMerchants();
    log.info("Total merchants fetched: {}", allMerchants.size());
    return allMerchants;
  }

  /**
   * Returns a merchant by its ID.
   *
   * @param id the ID of the merchant
   * @return Optional containing the BaseDto<Merchant> if found, or empty if not found
   */
  public Optional<BaseDto<Merchant>> getMerchantById(String id) {
    Optional<BaseDto<Merchant>> merchant = merchantQueryRepository.findById(id);
    if (merchant.isPresent()) {
      log.info("Merchant with ID: {} found", id);
    } else {
      log.warn("Merchant with ID: {} not found", id);
    }
    return merchant;
  }
}
