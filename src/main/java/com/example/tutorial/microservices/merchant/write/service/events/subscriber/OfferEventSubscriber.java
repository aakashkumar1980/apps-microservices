package com.example.tutorial.microservices.merchant.write.service.events.subscriber;

import com.example.tutorial.common.constants.CacheConstants;
import com.example.tutorial.common.dto.offer.events.OfferEvent;
import com.example.tutorial.common.utils.CacheUtils;
import com.example.tutorial.microservices.merchant.ApplicationConstants;
import com.example.tutorial.microservices.merchant.write.service.MerchantCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service("MerchantOfferEventSubscriber") // Ensure the service name is unique to avoid conflicts with other subscribers
public class OfferEventSubscriber {

  private static final Logger log = LoggerFactory.getLogger(OfferEventSubscriber.class);

  @Autowired
  private MerchantCommandService merchantCommandService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CacheUtils cacheUtils;

  /**
   * This method listens to the Kafka topic "OFFER_CREATED" for new offer creation events.
   * When an event is received, it caches the offer details and links the offer to the merchant.
   *
   * @param payload The JSON payload of the OfferCreated event.
   */
  @KafkaListener(topics = "OFFER_CREATED", groupId = ApplicationConstants.APPLICATION_NAME)
  public void subscribeCreateOfferEvent(String payload) {
    log.info("Received OfferCreated event: {}", payload);

    OfferEvent offerEvent = null;
    try {
      offerEvent = objectMapper.readValue(payload, OfferEvent.class);
      String offerId = offerEvent.getId();

      /** CACHE DATA **/
      // Cache the offer details in Redis
      cacheUtils.setCache(offerId, payload, CacheConstants.APPLICATION_CACHE_LIMIT_HOUR);

      /** BUSINESS LOGIC **/
      // Link the offers to the Campaign
      merchantCommandService.linkOfferToMerchant(offerEvent.getMerchantId(), offerId);

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * This method listens to the Kafka topic "OFFER_CANCELLED" for offer cancellation events.
   * When an event is received, it removes the offer from the merchant's offers.
   *
   * @param payload The JSON payload of the OfferCancelled event.
   */
  @KafkaListener(topics = "OFFER_CANCELLED", groupId = ApplicationConstants.APPLICATION_NAME)
  public void subscribeCancelOfferEvent(String payload) {
    log.info("Received OfferCancelled event: {}", payload);

    OfferEvent offerEvent = null;
    try {
      offerEvent = objectMapper.readValue(payload, OfferEvent.class);
      String offerId = offerEvent.getId();

      /** CACHE DATA **/
      // Clear the cached offer details in Redis
      cacheUtils.delete(offerId);

      /** BUSINESS LOGIC **/
      // Unlink the offer from the merchant
      merchantCommandService.unlinkOfferFromMerchant(offerEvent.getMerchantId(), offerId);

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
