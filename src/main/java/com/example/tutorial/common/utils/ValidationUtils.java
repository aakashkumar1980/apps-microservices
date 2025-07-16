package com.example.tutorial.common.utils;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.dto.campaign.CampaignStatus;
import com.example.tutorial.common.dto.campaign.events.CampaignEvent;
import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.common.exceptions.ApplicationFunctionalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {

  private static final Logger log = LoggerFactory.getLogger(ValidationUtils.class);

  @Autowired
  private APIUtils apiUtils;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;


  /**
   * Overrides the offer IDs in the BaseDto with the original campaign's offer IDs.
   * This is used to ensure that the offer IDs are consistent with the original campaign.
   *
   * @param baseDto The BaseDto containing the campaign data.
   */
  public void keepOriginalOfferIds(BaseDto<Campaign> baseDto, String campaignsApiUrl) {
    BaseDto<Campaign> originalCampaign = apiUtils.fetchBaseDtoById(
        campaignsApiUrl, baseDto, new TypeReference<BaseDto<Campaign>>() {});
    baseDto.getData().setOfferIds(originalCampaign.getData().getOfferIds());

    log.info("Overriding offer IDs for campaign: {} with the original campaign: {}",
        baseDto.getData().getOfferIds(), originalCampaign.getData().getOfferIds());
  }

  /**
   * Validates the campaign associated with the offer.
   * This method checks if the campaign is active by first looking it up in the Redis cache.
   * If the campaign is not found in the cache, it fetches the campaign details from the campaigns API.
   * If the campaign is not active or does not exist, it throws an ApplicationFunctionalException.
   *
   * @param offer The offer to validate.
   * @param campaignsApiUrl The URL of the campaigns API.
   * @throws ApplicationFunctionalException if the campaign is not active or does not exist.
   */
  public void validateCampaign(Offer offer, String campaignsApiUrl) {
    log.info("Validating campaign for offer: {}", offer);

    String campaignId = offer.getCampaignId();
    /** check if the campaign ID is present in Redis cache. If present, use it to validate the campaign status **/
    String campaignEventString = redisTemplate.opsForValue().get(campaignId);
    if(StringUtils.isNotBlank(campaignEventString)) {
      log.info("Campaign ID {} found in Redis cache", campaignId);
      CampaignEvent campaignEvent = null;
      try {
        campaignEvent = objectMapper.readValue(campaignEventString, new TypeReference<CampaignEvent>() {});

        // validate if the campaign is still active, if not throw an exception
        if (!StringUtils.equals(campaignEvent.getStatus().name(), CampaignStatus.ACTIVE.name())) {
          throw new ApplicationFunctionalException(
              String.format("Cannot create offer for campaign ID %s as it is not active", campaignId));
        }
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }

    /** if the campaign ID is not present in Redis cache, fetch it from the campaigns API and then validate the campaign status **/
    } else {
      log.info("Campaign ID {} not found in Redis cache, fetching from campaigns API", campaignId);
      BaseDto<Campaign> baseDto = new BaseDto<>();
      baseDto.setId(campaignId);
      baseDto = apiUtils.fetchBaseDtoById(campaignsApiUrl, baseDto, new TypeReference<BaseDto<Campaign>>() {});

      // validate if the campaign is still active, if not throw an exception
      if (!StringUtils.equals(baseDto.getData().getStatus().name(), CampaignStatus.ACTIVE.name())) {
        throw new ApplicationFunctionalException(
            String.format("Cannot create offer for campaign ID %s as it is not active", campaignId));
      }

      // cache the campaign event in Redis for future use
      log.info("Caching campaign event for campaign ID {} in Redis", campaignId);
      CampaignEvent event = new CampaignEvent();
      event.setId(baseDto.getId());
      event.setStatus(baseDto.getData().getStatus());
      event.setStartDate(baseDto.getData().getStartDate());
      event.setEndDate(baseDto.getData().getEndDate());
      event.setKafkaEventType(KafkaEventType.CAMPAIGN_UPDATED);
      try {
        redisTemplate.opsForValue().set(campaignId, objectMapper.writeValueAsString(event));
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
