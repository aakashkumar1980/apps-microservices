package com.example.tutorial.microservices.merchant.write.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.dto.merchant.Merchant;
import com.example.tutorial.common.dto.merchant.events.MerchantEvent;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.microservices.merchant.write.repository.MerchantCommandRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MerchantCommandService {

  private static final Logger log = LoggerFactory.getLogger(MerchantCommandService.class);

  @Autowired
  private MerchantCommandRepository merchantCommandRepository;

  @Autowired
  private APIUtils apiUtils;

  @Value("${merchants.api.url}")
  String merchantsApiUrl;

  /**
   * Links an offer to a merchant by updating the merchant's active offers list.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param merchantId
   * @param offerId
   */
  public void linkOfferToMerchant(String merchantId, String offerId) {
    log.info("Linking offer {} to merchant {}", offerId, merchantId);

    // Fetch the merchant by ID
    Optional<BaseDto<Merchant>> originalMerchantOptional = apiUtils.fetchAndCacheBaseDtoById(
        merchantsApiUrl, merchantId, new TypeReference<BaseDto<Merchant>>() {},
        new MerchantEvent(merchantId, KafkaEventType.MERCHANT_UPDATED));
    if (originalMerchantOptional.isPresent()) {
      BaseDto<Merchant> originalMerchant = originalMerchantOptional.get();
      // get the active offers list from the merchant, and add the offerId if it is not already present
      List<String> activeOfferIds = originalMerchant.getData().getActiveOffers();
      if(!activeOfferIds.contains(offerId)) {
        activeOfferIds.add(offerId);
        log.info("Adding offer {} to merchant {}", offerId, merchantId);
        merchantCommandRepository.save(originalMerchant);

      } else {
        log.info("Offer {} is already linked to campaign {}", offerId, merchantId);
      }
    }
  }
}
