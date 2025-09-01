package com.example.tutorial.microservices.merchant.write.service;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.merchant.Merchant;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.microservices.merchant.write.repository.MerchantCommandRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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
  @SuppressWarnings("unchecked")
  public void linkOfferToMerchant(String merchantId, String offerId) {
    log.info("Linking offer {} to merchant {}", offerId, merchantId);

    /** PERSIST DATA **/
    apiUtils.fetchDtoById(merchantsApiUrl, merchantId, new TypeReference<BaseDto<Merchant>>() {})
      .ifPresentOrElse(merchantObj -> {
        BaseDto<Merchant> merchant = (BaseDto<Merchant>) merchantObj;

        /** STEP 1: Check if the offer is already linked. If not, add it to the list **/
        List<String> activeOfferIds = merchant.getData().getActiveOffers();
        if (activeOfferIds.stream().noneMatch(offerId::equals)) {
          /** STEP 2: Link the offer to the merchant **/
          activeOfferIds.add(offerId);
          log.info("Adding offer {} to merchant {}", offerId, merchantId);
          /** STEP 3: Save the updated merchant **/
          merchantCommandRepository.save(merchant);

        } else {
          log.warn("Offer {} is already linked to merchant {}", offerId, merchantId);
        }
      }, () -> log.warn("Merchant with ID {} not found for linking offer {}", merchantId, offerId));
  }

  /**
   * Remove the offer from the merchant's active offers list.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param merchantId
   * @param offerId
   */
  @SuppressWarnings("unchecked")
  public void removeActiveOfferFromMerchant(String merchantId, String offerId) {
    log.info("Unlinking offer {} from merchant {}", offerId, merchantId);

    /** PERSIST DATA **/
    apiUtils.fetchDtoById(merchantsApiUrl, merchantId, new TypeReference<BaseDto<Merchant>>() {})
      .ifPresentOrElse(merchantObj -> {
        BaseDto<Merchant> merchant = (BaseDto<Merchant>) merchantObj;

        /** STEP 1: Check if the offer is linked. If yes, remove it from the list **/
        List<String> activeOfferIds = merchant.getData().getActiveOffers();
        if (activeOfferIds.stream().anyMatch(offerId::equals)) {
          /** STEP 2: Unlink the offer from the merchant **/
          activeOfferIds.removeIf(offerId::equals);
          log.info("Removing offer {} from merchant {}", offerId, merchantId);
          /** STEP 3: Save the updated merchant **/
          merchantCommandRepository.save(merchant);
        } else {
          log.warn("Offer {} is not linked to merchant {}", offerId, merchantId);
        }
      }, () -> log.warn("Merchant with ID {} not found for unlinking offer {}", merchantId, offerId));
  }
}
