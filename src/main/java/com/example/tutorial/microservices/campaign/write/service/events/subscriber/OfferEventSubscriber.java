package com.example.tutorial.microservices.campaign.write.service.events.subscriber;

import com.example.tutorial.common.constants.CacheConstants;
import com.example.tutorial.common.dto.offer.events.OfferEvent;
import com.example.tutorial.common.utils.CacheUtils;
import com.example.tutorial.microservices.campaign.write.service.CampaignCommandService;
import com.example.tutorial.microservices.customer.ApplicationConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service("CampaignOfferEventSubscriber") // Ensure the service name is unique to avoid conflicts with other subscribers
public class OfferEventSubscriber {

  private static final Logger log = LoggerFactory.getLogger(OfferEventSubscriber.class);

  @Autowired
  private CampaignCommandService campaignCommandService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CacheUtils cacheUtils;

  /**
   * This method listens to the Kafka topic "OFFER_CREATED" for OfferCreated events.
   * When an event is received, it caches the offer details and links the offer to the Campaign.
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

      // Cache the offer details in Redis
      cacheUtils.setCache(offerId, payload, CacheConstants.APPLICATION_CACHE_LIMIT_HOUR);

      // Link the offers to the Campaign
      String campaignId = offerEvent.getCampaignId();
      if (StringUtils.isNotBlank(campaignId)) {
        campaignCommandService.linkOfferToCampaign(campaignId, offerId);
      }

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
