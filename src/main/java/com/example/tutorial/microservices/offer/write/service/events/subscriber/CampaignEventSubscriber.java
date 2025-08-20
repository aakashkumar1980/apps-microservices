package com.example.tutorial.microservices.offer.write.service.events.subscriber;

import com.example.tutorial.common.constants.CacheConstants;
import com.example.tutorial.common.datamodel.campaign.events.CampaignEvent;
import com.example.tutorial.common.utils.CacheUtils;
import com.example.tutorial.microservices.offer.ApplicationConstants;
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

  @Autowired
  private CacheUtils  cacheUtils;

  /**
   * Handles the CampaignCreated campaignEvent by caching the campaign details in Redis.
   * It can be used later to quickly access campaign information without
   * making an REST API call to the Campaign microservice.
   *
   * @param payload the JSON payload of the CampaignCreated event
   */
  @KafkaListener(topics = "CAMPAIGN_CREATED", groupId = "offer-microservice")
  public void subscribeCreateCampaignEvent(String payload) {
    log.info("Received CampaignCreated event: {}", payload);

    CampaignEvent campaignEvent = null;
    try {
      campaignEvent = objectMapper.readValue(payload, CampaignEvent.class);
      String campaignId = campaignEvent.getCampaignId();

      /** CACHE DATA **/
      // cache the campaign details in Redis
      cacheUtils.setCache(campaignId, campaignEvent, CacheConstants.APPLICATION_CACHE_LIMIT_HOUR);
    } catch (JsonProcessingException e) {
      throw new ApplicationException("Error parsing object's value", e);
    }
  }

  /**
   * Handles the CampaignUpdated campaignEvent by updating the cached campaign details in Redis.
   * This ensures that the latest campaign information is available for offers.
   *
   * @param payload the JSON payload of the CampaignUpdated event
   */
  @KafkaListener(topics = "CAMPAIGN_UPDATED", groupId = "offer-microservice")
  public void subscribeUpdateCampaignEvent(String payload) {
    log.info("Received CampaignUpdated event: {}", payload);

    CampaignEvent campaignEvent = null;
    try {
      campaignEvent = objectMapper.readValue(payload, CampaignEvent.class);
      String campaignId = campaignEvent.getCampaignId();

      /** CACHE DATA **/
      // update the campaign details in Redis cache
      cacheUtils.setCache(campaignId, campaignEvent, CacheConstants.APPLICATION_CACHE_LIMIT_HOUR);
    } catch (JsonProcessingException e) {
      throw new ApplicationException("Error parsing object's value", e);
    }
  }

  /**
   * Handles the Campaign Cancelled campaignEvent by removing the campaign from Redis cache
   * and deactivating associated offers.
   *
   * @param payload the JSON payload of the CampaignDeleted event
   */
  @KafkaListener(topics = "CAMPAIGN_CANCELLED", groupId = ApplicationConstants.APPLICATION_NAME)
  public void subscribeCancelCampaignEvent(String payload) {
    log.info("Received CampaignCancelled event: {}", payload);

    CampaignEvent campaignEvent = null;
    try {
      campaignEvent = objectMapper.readValue(payload, CampaignEvent.class);
      String campaignId = campaignEvent.getCampaignId();

      /** CLEAR CACHE DATA **/
      // remove the campaign from Redis cache
      cacheUtils.delete(campaignId);

      /** BUSINESS LOGIC **/
      // cancel all offers associated with the campaign
      offerCommandService.cancelOffers(campaignId);

    } catch (JsonProcessingException e) {
      throw new ApplicationException("Error parsing object's value", e);
    }
  }
}
