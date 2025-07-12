package com.example.tutorial.microservices.offer.write.service.events;

import com.example.tutorial.common.dto.campaign.events.CampaignEvent;
import com.example.tutorial.microservices.offer.write.service.OfferCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OfferCommandCampaignEventSubscriber {

  private static final Logger log = LoggerFactory.getLogger(OfferCommandCampaignEventSubscriber.class);

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private OfferCommandService offerCommandService;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Handles the CampaignCreated campaignEvent by caching the campaign details in Redis.
   * It can be used later to quickly access campaign information without
   * making an REST API call to the Campaign microservice.
   *
   * @param campaignEvent the CampaignEvent containing details of the created campaign
   */
  @KafkaListener(topics = "CAMPAIGN_CREATED", groupId = "offer-microservice")
  public void subscribeCreateCampaignEvent(CampaignEvent campaignEvent) throws JsonProcessingException {
    String campaignId = campaignEvent.getId();
    redisTemplate.opsForValue().set(campaignId, objectMapper.writeValueAsString(campaignEvent));
    log.info("Cached campaign {} in Redis", campaignEvent.getId());
  }

  /**
   * Handles the CampaignUpdated campaignEvent by updating the cached campaign details in Redis.
   * This ensures that the latest campaign information is available for offers.
   *
   * @param campaignEvent the CampaignEvent containing details of the updated campaign
   */
  @KafkaListener(topics = "CAMPAIGN_UPDATED", groupId = "offer-microservice")
  public void subscribeUpdateCampaignEvent(CampaignEvent campaignEvent) throws JsonProcessingException {
    String campaignId = campaignEvent.getId();
    redisTemplate.opsForValue().set(campaignId, objectMapper.writeValueAsString(campaignEvent));
    log.info("Updated Cached campaign {} in Redis", campaignEvent.getId());
  }

  /**
   * Handles the CampaignDeleted campaignEvent by removing the campaign from Redis cache
   * and deactivating associated offers.
   *
   * @param campaignEvent the CampaignEvent containing details of the deleted campaign
   */
  @KafkaListener(topics = "CAMPAIGN_DELETED", groupId = "offer-microservice")
  public void subscribeDeleteCampaignEvent(CampaignEvent campaignEvent) {
    String campaignId = campaignEvent.getId();
    // Remove the campaign from Redis cache
    redisTemplate.delete(campaignId);
    log.info("Deleted campaign {} from Redis", campaignEvent.getId());

    // Deactivate all offers associated with the campaign
    offerCommandService.deactivateOffers(campaignId);
    log.info("Deactivated offers for campaign {}", campaignEvent.getId());
  }
}
