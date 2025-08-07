package com.example.tutorial.microservices.campaign.write.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.dto.campaign.CampaignStatus;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.DBUtils;
import com.example.tutorial.common.utils.validation.CampaignValidation;
import com.example.tutorial.microservices.campaign.write.repository.CampaignCommandRepository;
import com.example.tutorial.microservices.campaign.write.service.events.publisher.CampaignEventPublisher;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignCommandService {

  private static final Logger log = LoggerFactory.getLogger(CampaignCommandService.class);

  @Autowired
  private CampaignCommandRepository campaignCommandRepository;

  @Autowired
  private CouchbaseTemplate couchbaseTemplate;

  @Value("${campaign.counter.key:campaign_counter}")
  private String campaignCounterKey;

  @Autowired
  private CampaignEventPublisher campaignEventPublisher;

  @Autowired
  private CampaignValidation campaignValidation;

  @Autowired
  private DBUtils dbUtils;

  @Autowired
  private APIUtils apiUtils;

  @Value("${campaigns.api.url}")
  String campaignsApiUrl;

  /**
   * Create a new campaign and publish an event to the kafka event bus.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param campaign the campaign to create
   * @return the ID of the created campaign
   */
  public String createCampaign(Campaign campaign) {
    log.info("Creating campaign: {}", campaign);

    // build the BaseDto for the campaign with default values
    BaseDto<Campaign> baseCampaign = BaseDto.build(campaign);

    /** PERSIST DATA **/
    // generate a unique ID for the campaign
    String id = "campaign::" + dbUtils.getUniqueCounter(couchbaseTemplate, campaignCounterKey);
    baseCampaign.setId(id);
    // Save the campaign to the repository
    BaseDto<Campaign> savedCampaign = campaignCommandRepository.save(baseCampaign);

    /** PUBLISH EVENT **/
    // Publish the campaign created event to kafka event bus
    campaignEventPublisher.publishCreateCampaignEvent(savedCampaign);
    return savedCampaign.getId();
  }

  /**
   * Update an existing campaign and publish an event to the kafka event bus.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param campaign the BaseDto containing the campaign data to update
   */
  public void updateCampaign(BaseDto<Campaign> campaign) {
    log.info("Updating campaign: {}", campaign);

    /** DATA VALIDATION **/
    // override offer ids by keeping the original as it shouldn't be changed once assigned
    campaignValidation.keepOriginalOfferIds(campaign);

    /** PERSIST DATA **/
    // Update the updated campaign to the repository
    campaign.setUpdatedAt(LocalDateTime.now());
    BaseDto<Campaign> updatedCampaign = campaignCommandRepository.save(campaign);

    /** PUBLISH EVENT **/
    // Publish the campaign updated event to kafka event bus
    campaignEventPublisher.publishUpdateCampaignEvent(updatedCampaign);
  }

  /**
   * Cancel a campaign by its ID and publish an event to the kafka event bus.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param id the ID of the campaign to delete
   */
  public void cancelCampaign(String id) {
    log.info("Cancelling campaign with ID: {}", id);

    /** PERSIST DATA **/
    // Get the campaign by ID
    Optional<BaseDto<Campaign>> originalCampaignOptional = apiUtils.fetchAndCacheBaseDtoById(
        campaignsApiUrl, id, new TypeReference<BaseDto<Campaign>>() {});
    if (originalCampaignOptional.isPresent()) {
      BaseDto<Campaign> originalCampaign = originalCampaignOptional.get();
      // Set the status to CANCELLED
      originalCampaign.getData().setStatus(CampaignStatus.CANCELLED);
      // Persist the updated campaign
      campaignCommandRepository.save(originalCampaign);
    }

    /** PUBLISH EVENT **/
    // Publish the campaign created event to kafka event bus
    campaignEventPublisher.publishCancelCampaignEvent(id);
  }

  /**
   * Link an offer to a campaign by campaign ID and offer ID.
   * If the offer is already linked, it will not be added again.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param campaignId the ID of the campaign
   * @param offerId    the ID of the offer to link
   */
  public void linkOfferToCampaign(String campaignId, String offerId) {
    log.info("Linking offer {} to campaign {}", offerId, campaignId);

    /** PERSIST DATA **/
    // Fetch the campaign by ID
    Optional<BaseDto<Campaign>> originalCampaignOptional = apiUtils.fetchAndCacheBaseDtoById(
        campaignsApiUrl, campaignId, new TypeReference<BaseDto<Campaign>>() {});
    if (originalCampaignOptional.isPresent()) {
      BaseDto<Campaign> originalCampaign = originalCampaignOptional.get();
      // Get the existing offer IDs from the campaign
      List<String> existingOfferIds = originalCampaign.getData().getOfferIds();
      if(!existingOfferIds.contains(offerId)) {
        // If the offer is not already linked, add it to the campaign
        log.debug("Adding offer {} to campaign {}", offerId, campaignId);
        existingOfferIds.add(offerId);
        campaignCommandRepository.save(originalCampaign);

      } else {
        log.warn("Offer {} is already linked to campaign {}", offerId, campaignId);
      }
    }

  }

  /**
   * Unlink an offer from a campaign by campaign ID and offer ID.
   * If the offer is not linked, it will not be removed.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param campaignId the ID of the campaign
   * @param offerId    the ID of the offer to unlink
   */
  public void unlinkOfferFromCampaign(String campaignId, String offerId) {
    log.info("Unlinking offer {} from campaign {}", offerId, campaignId);

    /** PERSIST DATA **/
    // Fetch the campaign by ID
    Optional<BaseDto<Campaign>> originalCampaignOptional = apiUtils.fetchAndCacheBaseDtoById(
        campaignsApiUrl, campaignId, new TypeReference<BaseDto<Campaign>>() {});
    if (originalCampaignOptional.isPresent()) {
      BaseDto<Campaign> originalCampaign = originalCampaignOptional.get();
      // Get the existing offer IDs from the campaign
      List<String> existingOfferIds = originalCampaign.getData().getOfferIds();
      if(existingOfferIds.contains(offerId)) {
        // If the offer is linked, remove it from the campaign
        log.debug("Removing offer {} from campaign {}", offerId, campaignId);
        existingOfferIds.remove(offerId);
        campaignCommandRepository.save(originalCampaign);

      } else {
        log.warn("Offer {} is not linked to campaign {}", offerId, campaignId);
      }
    }
  }
}
