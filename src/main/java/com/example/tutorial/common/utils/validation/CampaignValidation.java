package com.example.tutorial.common.utils.validation;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.KafkaEventType;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import com.example.tutorial.common.datamodel.campaign.events.CampaignEvent;
import com.example.tutorial.common.exceptions.api.APIRequestValidationException;
import com.example.tutorial.common.exceptions.api.APIRequestValidationMessage;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.CacheUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
  private CacheUtils cacheUtils;

  @Value("${campaigns.api.url}")
  String campaignsApiUrl;

  /**
   * Overrides the offer IDs in the BaseDto with the original campaign's offer IDs.
   * This is used to ensure that the offer IDs are consistent with the original campaign as
   * during a campaign update, the offer IDs should not change.
   *
   * @param id The ID of the campaign to update.
   * @param campaign The BaseDto containing the campaign data.
   * @throws APIRequestValidationException
   */
  public void keepOriginalOfferIds(String id, BaseDto<Campaign> campaign) {
    log.info("Overriding offer IDs for campaign: {}", id);

    Optional<BaseDto<Campaign>> originalCampaignOptional = apiUtils.fetchDtoById(
        campaignsApiUrl, id, new TypeReference<BaseDto<Campaign>>() {});
    if (originalCampaignOptional.isPresent()) {
      log.debug("Overridden offer IDs for campaign: {} with the original campaign: {}",
          campaign.getData().getOfferIds(), originalCampaignOptional.get().getData().getOfferIds());
      campaign.getData().setOfferIds(originalCampaignOptional.get().getData().getOfferIds());

    } else {
      APIRequestValidationMessage validationMessage = new APIRequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Campaign with ID %s not found for update.,", id))
      );
      throw new APIRequestValidationException(validationMessage);
    }

  }

  /**
   * Validates the existence and status of a campaign by its ID.
   * <pre>
   * It first checks if the campaign ID is present in the Redis cache or gets it from the REST API.
   * If found, it validates the campaign status and end date.
   * If not found, it throws an exception indicating that the campaign does not exist.
   * </pre>
   *
   * @param campaignId The ID of the campaign to validate.
   * @param campaignsApiUrl The URL of the campaigns API to fetch the campaign if not found in cache.
   * @throws APIRequestValidationException if the campaign is not found or not active or the end date has passed.
   */
  public void validateCampaign(String campaignId, String campaignsApiUrl) {
    log.info("Validating existence of campaign with ID: {}", campaignId);

    // get the campaign event from cache or from the campaigns API
    Optional<CampaignEvent> campaignEventOptional = cacheUtils.getCache(
        campaignId, new TypeReference<CampaignEvent>() {},
        campaignsApiUrl, new TypeReference<BaseDto<Campaign>>() {},
        new CampaignEvent(campaignId, KafkaEventType.CAMPAIGN_UPDATED)
    );
    if(campaignEventOptional.isPresent()) {
      // if found in cache, validate the campaign status and end date.
      validateCampaign(
          campaignEventOptional.get().getStatus().name(),
          campaignEventOptional.get().getEndDate(), campaignId
      );

    } else {
      //  if not found then fail the validation with an exception.
      APIRequestValidationMessage validationMessage = new APIRequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Campaign with ID %s not found", campaignId))
      );
      throw new APIRequestValidationException(validationMessage);
    }
  }

  // -- PRIVATE METHODS -- //
  /**
   * Extracted method to validate the campaign status and end date.
   * This method checks if the campaign is active and if the end date has not passed.
   *
   * @param status The status of the campaign.
   * @param endDate The end date of the campaign.
   * @param campaignId The ID of the campaign.
   * @throws APIRequestValidationException if the campaign is not active or the end date has passed.
   */
  private void validateCampaign(String status, LocalDateTime endDate, String campaignId) {
    log.debug("Validating campaign status: {}, end date: {}, for campaign ID: {}", status, endDate, campaignId);

    // validate if the campaign is still active, if not throw an exception
    if (!StringUtils.equals(status, CampaignStatus.ACTIVE.name())) {
      APIRequestValidationMessage validationMessage = new APIRequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Cannot create offer for campaign ID %s as it is not active", campaignId))
      );
      throw new APIRequestValidationException(validationMessage);
    }
    // validate if the campaign end date is not reached, if so throw an exception
    if (endDate.isBefore(java.time.LocalDateTime.now())) {
      APIRequestValidationMessage validationMessage = new APIRequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Cannot create offer for campaign ID %s as the campaign end date has passed", campaignId))
      );
      throw new APIRequestValidationException(validationMessage);
    }
  }

}
