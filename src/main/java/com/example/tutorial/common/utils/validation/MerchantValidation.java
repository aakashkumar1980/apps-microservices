package com.example.tutorial.common.utils.validation;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.merchant.Merchant;
import com.example.tutorial.common.dto.merchant.events.MerchantEvent;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.exceptions.ApplicationFunctionalException;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.CacheUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MerchantValidation {

  private static final Logger log = LoggerFactory.getLogger(MerchantValidation.class);

  @Autowired
  private APIUtils apiUtils;

  @Autowired
  private CacheUtils cacheUtils;


  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Validates the existence of a merchant by its ID.
   * This method first checks if the merchant event is cached in Redis.
   * If not found, it fetches the merchant details from the merchants API and caches the event.
   *
   * @param merchantId The ID of the merchant to validate.
   * @param merchantsApiUrl The API URL to fetch merchant details.
   */
  public void validateMerchant(String merchantId, String merchantsApiUrl) {
    log.info("Validating existence of merchant with ID: {}", merchantId);

    log.info("Checking cache for merchant event with ID: {}", merchantId);
    Optional<String> merchantEventOptional = cacheUtils.getCache(merchantId);
    if (merchantEventOptional.isEmpty()) {
      log.info("Fetching data for ID {} from merchants API", merchantId);
      Optional<BaseDto<Merchant>> merchantOptional = apiUtils.fetchAndCacheBaseDtoById(
          merchantsApiUrl, merchantId, new TypeReference<BaseDto<Merchant>>() {},
          new MerchantEvent(merchantId, KafkaEventType.MERCHANT_UPDATED));

      merchantOptional.ifPresentOrElse(
          m -> {}, // Do nothing if present
          () -> {
            throw new ApplicationFunctionalException(
                String.format("Merchant with ID %s does not exist", merchantId));
          }
      );

    }
  }

}
