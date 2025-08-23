package com.example.tutorial.microservices.campaign.write.service;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import com.example.tutorial.common.exceptions.ApplicationFunctionalException;
import com.example.tutorial.common.exceptions.api.APIRequestValidationException;
import com.example.tutorial.common.exceptions.api.APIRequestValidationMessage;
import com.example.tutorial.common.exceptions.api.APIRequestVersionConflictException;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.ApplicationUtils;
import com.example.tutorial.common.utils.DBUtils;
import com.example.tutorial.common.utils.validation.CampaignValidation;
import com.example.tutorial.microservices.campaign.write.repository.CampaignCommandRepository;
import com.example.tutorial.microservices.campaign.write.service.events.publisher.CampaignEventPublisher;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CampaignCommandService {

  private static final Logger log = LoggerFactory.getLogger(CampaignCommandService.class);

  @Autowired
  private CampaignCommandRepository campaignCommandRepository;

  @Autowired
  private CouchbaseTemplate couchbaseTemplate;

  @Autowired
  private CampaignEventPublisher campaignEventPublisher;

  @Autowired
  private CampaignValidation campaignValidation;

  @Autowired
  private DBUtils dbUtils;

  @Autowired
  private APIUtils apiUtils;

  @Autowired
  private ApplicationUtils applicationUtils;

  @Value("${campaigns.api.url}")
  String campaignsApiUrl;

  @Value("${campaign.counter.key:campaign_counter}")
  private String campaignCounterKey;

  /**
   * Create a new campaign and publish an event to the kafka event bus.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param campaign the campaign to create
   * @return Optional containing the created campaign if successful, otherwise empty.
   */
  public Optional<BaseDto<Campaign>> createCampaign(Campaign campaign) {
    log.info("Creating campaign: {}", campaign);

    // build the BaseDto for the campaign with default values
    BaseDto<Campaign> baseCampaign = BaseDto.build(campaign);

    /** PERSIST DATA **/
    // generate a unique ID for the campaign
    String id = "campaign::" + dbUtils.getUniqueCounter(couchbaseTemplate, campaignCounterKey);
    baseCampaign.setId(id);
    // save the campaign to the repository
    BaseDto<Campaign> savedCampaign = campaignCommandRepository.save(baseCampaign);

    /** PUBLISH EVENT **/
    // publish the campaign created event to kafka event bus
    campaignEventPublisher.publishCreateCampaignEvent(savedCampaign);
    return Optional.of(savedCampaign);
  }

  /**
   * Update an existing campaign and publish an event to the kafka event bus.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param id the ID of the campaign
   * @param campaign the campaign with updated fields
   * @return Optional containing the updated campaign if successful, otherwise empty.
   * @throws APIRequestValidationException if the campaign with the given ID is not found,
   * or if there is a version conflict.
   */
  @SuppressWarnings("unchecked")
  public Optional<BaseDto<Campaign>> updateCampaign(String id, BaseDto<Campaign> campaign) {
    log.info("Updating campaign with ID {}: {}", id, campaign);

    /** DATA VALIDATION **/
    // override offer ids by keeping the original as it shouldn't be changed once assigned
    campaignValidation.keepOriginalOfferIds(id, campaign);

    /** PERSIST DATA **/
    try {
      return (Optional<BaseDto<Campaign>>) apiUtils.fetchDtoById(campaignsApiUrl, id, new TypeReference<BaseDto<Campaign>>() {})
          .map(existingCampaignObj -> {
            BaseDto<Campaign> existingCampaign = (BaseDto<Campaign>) existingCampaignObj;

            /** STEP 1: Check version for optimistic locking **/
            Integer existingVersion = applicationUtils.validateAndGetExistingVersion(id, campaign, existingCampaign);
            /** STEP 2: Apply updates of the existing data-model **/
            applicationUtils.copyProperties(existingCampaign.getData(), campaign.getData());
            existingCampaign.setUpdatedAt(LocalDateTime.now());
            // increment version for optimistic locking
            existingCampaign.setVersion(existingVersion + 1);

            try {
              BaseDto<Campaign> updatedCampaign = campaignCommandRepository.save(existingCampaign);

              /** PUBLISH EVENT **/
              // publish the campaign updated event to kafka event bus
              campaignEventPublisher.publishUpdateCampaignEvent(updatedCampaign);
              return updatedCampaign;
            } catch (OptimisticLockingFailureException e) {
              throw new APIRequestVersionConflictException(
                  new APIRequestValidationMessage(
                      "Api request validation failed",
                      Map.of("error", String.format("Campaign with ID %s has been modified by another process. " +
                          "Please retrieve the latest version and try again.", id))
                  )
              );
            }
          })
          .map(Optional::of)
          .orElseThrow(() -> new APIRequestValidationException(
              new APIRequestValidationMessage("Api request validation failed",
                  Map.of("error", String.format("Campaign with ID %s not found for update.", id)))
          ));
    } catch (Throwable e) {
      throw ((ApplicationFunctionalException) e);
    }
  }



  /**
   * Cancel a campaign by its ID and publish an event to the kafka event bus.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param id the ID of the campaign to delete
   * @throws APIRequestValidationException if the campaign with the given ID is not found.
   */
  @SuppressWarnings("unchecked")
  public void cancelCampaign(String id) {
    log.info("Cancelling campaign with ID {}", id);

    apiUtils.fetchDtoById(campaignsApiUrl, id, new TypeReference<BaseDto<Campaign>>() {})
        .ifPresentOrElse(existingCampaignObj -> {
            BaseDto<Campaign> existingCampaign = (BaseDto<Campaign>) existingCampaignObj;

            /** PERSIST DATA **/
            existingCampaign.getData().setStatus(CampaignStatus.CANCELLED);
            campaignCommandRepository.save(existingCampaign);
            /** PUBLISH EVENT **/
            campaignEventPublisher.publishCancelCampaignEvent(id);

          }, () -> {
            throw new APIRequestValidationException(
                new APIRequestValidationMessage(
                    "Api request validation failed",
                    Map.of("error", String.format("Campaign with ID %s not found for cancellation.", id)))
            );
          }
        );
  }

  /**
   * Link an offer to a campaign by campaign ID and offer ID.
   * If the offer is already linked, it will not be added again.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param campaignId the ID of the campaign
   * @param offerId    the ID of the offer to link
   */
  @SuppressWarnings("unchecked")
  public void linkOfferToCampaign(String campaignId, String offerId) {
    log.info("Linking offer {} to campaign {}", offerId, campaignId);

    /** PERSIST DATA **/
    // fetch the original campaign by ID
    apiUtils.fetchDtoById(campaignsApiUrl, campaignId, new TypeReference<BaseDto<Campaign>>() {})
        .ifPresentOrElse(existingCampaignObj -> {
            BaseDto<Campaign> existingCampaign = (BaseDto<Campaign>) existingCampaignObj;

            List<String> existingOfferIds = existingCampaign.getData().getOfferIds();
            if (existingOfferIds.stream().noneMatch(offerId::equals)) {
              log.debug("Adding offer {} to campaign {}", offerId, campaignId);
              existingOfferIds.add(offerId);
              campaignCommandRepository.save(existingCampaign);

            } else {
              log.warn("Offer {} is already linked to campaign {}", offerId, campaignId);
            }

          }, () -> {
            log.warn("Campaign with ID {} not found for linking offer {}", campaignId, offerId);
          }
        );

  }

  /**
   * Unlink an offer from a campaign by campaign ID and offer ID.
   * If the offer is not linked, it will not be removed.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param campaignId the ID of the campaign
   * @param offerId    the ID of the offer to unlink
   */
  @SuppressWarnings("unchecked")
  public void unlinkOfferFromCampaign(String campaignId, String offerId) {
    log.info("Unlinking offer {} from campaign {}", offerId, campaignId);

    /** PERSIST DATA **/
    // fetch the original campaign by ID
    apiUtils.fetchDtoById(campaignsApiUrl, campaignId, new TypeReference<BaseDto<Campaign>>() {})
        .ifPresentOrElse(existingCampaignObj -> {
            BaseDto<Campaign> existingCampaign = (BaseDto<Campaign>) existingCampaignObj;

            List<String> existingOfferIds = existingCampaign.getData().getOfferIds();
            if (existingOfferIds.stream().anyMatch(offerId::equals)) {
              log.debug("Removing offer {} from campaign {}", offerId, campaignId);
              existingOfferIds.removeIf(offerId::equals);
              campaignCommandRepository.save(existingCampaign);

            } else {
              log.warn("Offer {} is not linked to campaign {}", offerId, campaignId);
            }

          }, () -> {
            log.warn("Campaign with ID {} not found for unlinking offer {}", campaignId, offerId);
          }
        );
  }
}
