package com.example.tutorial.microservices.offer.write.service.events;

import com.example.tutorial.common.dto.campaign.events.CampaignEvent;
import com.example.tutorial.microservices.offer.write.service.OfferCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OfferCommandEventSubscriber {

  private static final Logger log = LoggerFactory.getLogger(OfferCommandEventSubscriber.class);

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private OfferCommandService offerCommandService;

  /**
   * Handles the CampaignCreated event by caching the campaign details in Redis.
   * It can be used later to quickly access campaign information without
   * making an REST API call to the Campaign microservice.
   *
   * @param event the CampaignEvent containing details of the created campaign
   */
  @KafkaListener(topics = "CAMPAIGN_CREATED", groupId = "offer-microservice")
  public void subscribeCreateCampaignEvent(CampaignEvent event) {
    String campaignId = event.getId();
    redisTemplate.opsForValue().set(campaignId, event.toString());
    log.info("Cached campaign {} in Redis", event.getId());
  }

  /**
   * Handles the CampaignDeleted event by removing the campaign from Redis cache
   * and deactivating associated offers.
   *
   * @param event the CampaignEvent containing details of the deleted campaign
   */
  @KafkaListener(topics = "CAMPAIGN_DELETED", groupId = "offer-microservice")
  public void subscribeCampaignDeletedEvent(CampaignEvent event) {
    String campaignId = event.getId();
    // Remove the campaign from Redis cache
    redisTemplate.delete(campaignId);
    log.info("Deleted campaign {} from Redis", event.getId());

    // Deactivate all offers associated with the campaign
    offerCommandService.deactivateOffers(campaignId);
    log.info("Deactivated offers for campaign {}", event.getId());
  }
}
