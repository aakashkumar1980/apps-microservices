package com.example.tutorial.common.utils.validation;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.merchant.Merchant;
import com.example.tutorial.common.datamodel.merchant.events.MerchantEvent;
import com.example.tutorial.common.datamodel.KafkaEventType;
import com.example.tutorial.common.utils.CacheUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class MerchantValidation {

  private static final Logger log = LoggerFactory.getLogger(MerchantValidation.class);

  @Autowired
  private CacheUtils cacheUtils;

  @Value("${merchants.api.url}")
  private String merchantsApiUrl;

  /**
   * Validates the existence of a merchant by its ID.
   * This method first checks if the merchant event is cached in Redis or gets it from the RETS API.
   * If not found, it throws a RequestValidationException with an appropriate message.
   *
   * @param merchantId The ID of the merchant to validate.
   * @throws RequestValidationException if the merchant does not exist or is not found in the API.
   */
  public void validateMerchant(String merchantId) {
    log.info("Validating existence of merchant with ID: {}", merchantId);

    // get the merchant event from cache or from the merchants API
    Optional<MerchantEvent> merchantEventOptional = cacheUtils.getCache(
        merchantId, new TypeReference<MerchantEvent>() {},
        merchantsApiUrl, new TypeReference<BaseDto<Merchant>>() {},
        new MerchantEvent(merchantId, KafkaEventType.MERCHANT_UPDATED)
    );

    if (merchantEventOptional.isEmpty()) {
      // if the merchant event is not found in cache, throw an exception
      RequestValidationMessage validationMessage = new RequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Merchant with ID %s does not exist", merchantId))
      );
      throw new RequestValidationException(validationMessage);
    }
  }

}
