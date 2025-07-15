package com.example.tutorial.microservices.offer.write.service.events.subscriber;

import com.example.tutorial.common.dto.campaign.events.CampaignEvent;
import com.example.tutorial.microservices.offer.write.service.OfferCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CampaignEventSubscriber {

  private static final Logger log = LoggerFactory.getLogger(CampaignEventSubscriber.class);

  @Autowired
  private OfferCommandService offerCommandService;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Handles the CampaignCreated campaignEvent by caching the campaign details in Redis.
   * It can be used later to quickly access campaign information without
   * making an REST API call to the Campaign microservice.
   *
   * @param payload the JSON payload of the CampaignCreated event
   */
  @KafkaListener(topics = "CAMPAIGN_CREATED", groupId = "offer-microservice")
  public void subscribeCreateCampaignEvent(String payload) throws JsonProcessingException {
    log.info("Received CampaignCreated event: {}", payload);
  }

  /**
   * Handles the CampaignUpdated campaignEvent by updating the cached campaign details in Redis.
   * This ensures that the latest campaign information is available for offers.
   *
   * @param payload the JSON payload of the CampaignUpdated event
   */
  @KafkaListener(topics = "CAMPAIGN_UPDATED", groupId = "offer-microservice")
  public void subscribeUpdateCampaignEvent(String payload) throws JsonProcessingException {
    log.info("Received CampaignUpdated event: {}", payload);
  }

  /**
   * Handles the CampaignDeleted campaignEvent by removing the campaign from Redis cache
   * and deactivating associated offers.
   *
   * @param payload the JSON payload of the CampaignDeleted event
   */
  @KafkaListener(topics = "CAMPAIGN_DELETED", groupId = "offer-microservice")
  public void subscribeDeleteCampaignEvent(String payload) throws JsonProcessingException {
    log.info("Received CampaignDeleted event: {}", payload);

    CampaignEvent campaignEvent = objectMapper.readValue(payload, CampaignEvent.class);
    String campaignId = campaignEvent.getId();

    // Deactivate all offers associated with the campaign
    offerCommandService.deactivateOffers(campaignId);
    log.info("Deactivated offers for campaign {}", campaignId);
  }
}
