package com.example.tutorial.common.utils.validation;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.merchant.Merchant;
import com.example.tutorial.common.dto.merchant.events.MerchantEvent;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.exceptions.RequestValidationException;
import com.example.tutorial.common.exceptions.RequestValidationMessage;
import com.example.tutorial.common.utils.APIUtils;
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
  private APIUtils apiUtils;

  @Autowired
  private CacheUtils cacheUtils;

  @Value("${merchants.api.url}")
  private String merchantsApiUrl;

  /**
   * Validates the existence of a merchant by its ID.
   * This method first checks if the merchant event is cached in Redis.
   * If not found, it fetches the merchant details from the merchants API and caches the event.
   *
   * @param merchantId The ID of the merchant to validate.
   * @throws RequestValidationException if the merchant does not exist or is not found in the API.
   */
  public void validateMerchant(String merchantId) {
    log.info("Validating existence of merchant with ID: {}", merchantId);

    Optional<MerchantEvent> merchantEventOptional = cacheUtils.getCache(merchantId, new TypeReference<MerchantEvent>() {});
    if (merchantEventOptional.isEmpty()) {
      Optional<BaseDto<Merchant>> merchantOptional = apiUtils.fetchAndCacheBaseDtoById(
          merchantsApiUrl, merchantId, new TypeReference<BaseDto<Merchant>>() {},
          new MerchantEvent(merchantId, KafkaEventType.MERCHANT_UPDATED));

      merchantOptional.ifPresentOrElse(
          m -> {}, // Do nothing if present
          () -> {
            RequestValidationMessage validationMessage = new RequestValidationMessage(
                "Api request validation failed",
                Map.of("error", String.format("Merchant with ID %s does not exist", merchantId))
            );
            throw new RequestValidationException(validationMessage);
          }
      );

    }
  }

}
