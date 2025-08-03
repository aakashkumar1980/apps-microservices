package com.example.tutorial.common.utils.validation;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.dto.campaign.CampaignStatus;
import com.example.tutorial.common.dto.campaign.events.CampaignEvent;
import com.example.tutorial.common.exceptions.ApplicationException;
import com.example.tutorial.common.exceptions.RequestValidationException;
import com.example.tutorial.common.exceptions.RequestValidationMessage;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.CacheUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Component
public class CampaignValidation {

  private static final Logger log = LoggerFactory.getLogger(CampaignValidation.class);

  @Autowired
  private APIUtils apiUtils;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private CacheUtils cacheUtils;

  @Autowired
  private ObjectMapper objectMapper;


  /**
   * Overrides the offer IDs in the BaseDto with the original campaign's offer IDs.
   * This is used to ensure that the offer IDs are consistent with the original campaign.
   *
   * @param campaign The BaseDto containing the campaign data.
   * @param campaignsApiUrl The URL of the campaigns API to fetch the original campaign.
   */
  public void keepOriginalOfferIds(BaseDto<Campaign> campaign, String campaignsApiUrl) {
    Optional<BaseDto<Campaign>> originalCampaignOptional = apiUtils.fetchAndCacheBaseDtoById(
        campaignsApiUrl, campaign.getId(), new TypeReference<BaseDto<Campaign>>() {},
        new CampaignEvent(campaign.getId(), KafkaEventType.CAMPAIGN_UPDATED));

    originalCampaignOptional.ifPresent( originalCampaign -> {
      log.info("Overridden offer IDs for campaign: {} with the original campaign: {}",
          campaign.getData().getOfferIds(), originalCampaign.getData().getOfferIds());
      campaign.getData().setOfferIds(originalCampaign.getData().getOfferIds());
    });

  }

  /**
   * Validates the existence and status of a campaign by its ID.
   * It first checks if the campaign ID is present in the Redis cache.
   * If found, it validates the campaign status and end date.
   * If not found, it fetches the campaign from the campaigns API and then validates it.
   * If still not found, it throws an exception indicating that the campaign does not exist.
   *
   * @param campaignId The ID of the campaign to validate.
   * @param campaignsApiUrl The URL of the campaigns API to fetch the campaign if not found in cache.
   * @throws RequestValidationException if the campaign is not found or not active or the end date has passed.
   */
  public void validateCampaign(String campaignId, String campaignsApiUrl) {
    log.info("Validating existence of campaign with ID: {}", campaignId);

    /** check if the campaign ID is present in Redis cache. If present, use it to validate the campaign status **/
    log.info("Checking Redis cache for campaign ID: {}", campaignId);
    Optional<String> campaignEventOptional =cacheUtils.getCache(campaignId);
    if(campaignEventOptional.isPresent()) {
      CampaignEvent campaignEvent = null;
      try {
        campaignEvent = objectMapper.readValue(campaignEventOptional.get(), new TypeReference<CampaignEvent>() {});

        validateCampaign(campaignEvent.getStatus().name(), campaignEvent.getEndDate(), campaignId);
      } catch (JsonProcessingException e) {
        throw new ApplicationException("Error parsing object's value", e);
      }

    /** if the campaign ID is not present in Redis cache, fetch it from the campaigns API and then validate the campaign status **/
    } else {
      log.info("Campaign ID {} not found in cache, fetching from campaigns API: {}", campaignId, campaignsApiUrl);
      Optional<BaseDto<Campaign>> campaignOptional = apiUtils.fetchAndCacheBaseDtoById(
          campaignsApiUrl, campaignId, new TypeReference<BaseDto<Campaign>>() {},
          new CampaignEvent(campaignId, KafkaEventType.CAMPAIGN_UPDATED));

      if(campaignOptional.isPresent()) {
        BaseDto<Campaign> campaign = campaignOptional.get();
        validateCampaign(
            campaign.getData().getStatus().name(),
            campaign.getData().getEndDate(), campaignId);

      } else {

        RequestValidationMessage validationMessage = new RequestValidationMessage(
            "Api request validation failed",
            Map.of("error", String.format("Campaign with ID %s not found", campaignId))
        );
        throw new RequestValidationException(validationMessage);
      }
    }
  }

  // -- PRIVATE METHODS --
  /**
   * Extracted method to validate the campaign status and end date.
   * This method checks if the campaign is active and if the end date has not passed.
   *
   * @param status The status of the campaign.
   * @param endDate The end date of the campaign.
   * @param campaignId The ID of the campaign.
   * @throws RequestValidationException if the campaign is not active or the end date has passed.
   */
  private void validateCampaign(String status, LocalDateTime endDate, String campaignId) {
    // validate if the campaign is still active, if not throw an exception
    if (!StringUtils.equals(status, CampaignStatus.ACTIVE.name())) {
      RequestValidationMessage validationMessage = new RequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Cannot create offer for campaign ID %s as it is not active", campaignId))
      );
      throw new RequestValidationException(validationMessage);
    }
    // validate if the campaign end date is not reached, if so throw an exception
    if (endDate.isBefore(java.time.LocalDateTime.now())) {
      RequestValidationMessage validationMessage = new RequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Cannot create offer for campaign ID %s as the campaign end date has passed", campaignId))
      );
      throw new RequestValidationException(validationMessage);
    }
  }

}
