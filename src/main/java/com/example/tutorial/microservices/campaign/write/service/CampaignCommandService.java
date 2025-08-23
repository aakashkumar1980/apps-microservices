package com.example.tutorial.microservices.campaign.write.service;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.exceptions.api.APIRequestValidationException;
import com.example.tutorial.common.exceptions.api.APIRequestValidationMessage;
import com.example.tutorial.common.exceptions.api.APIRequestVersionConflictException;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.DBUtils;
import com.example.tutorial.microservices.campaign.write.repository.CampaignCommandRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class CampaignCommandService {

  private static final Logger log = LoggerFactory.getLogger(CampaignCommandService.class);
  @Autowired
  private CouchbaseTemplate couchbaseTemplate;

  @Autowired
  private CampaignCommandRepository campaignCommandRepository;

  @Autowired
  private DBUtils dbUtils;

  @Autowired
  private APIUtils apiUtils;

  @Value("${campaigns.api.url}")
  String campaignsApiUrl;

  @Value("${campaign.counter.key:campaign_counter}")
  private String campaignCounterKey;

  /**
   * Create a new campaign.
   *
   * @param campaign the campaign to create
   * @return Optional containing the created campaign if successful, otherwise empty.
   */
  public Optional<BaseDto<Campaign>> createCampaign(Campaign campaign) {
    // build the BaseDto for the campaign with default values
    BaseDto<Campaign> baseCampaign = BaseDto.build(campaign);

    // generate a unique ID for the campaign
    String id = "campaign::" + dbUtils.getUniqueCounter(couchbaseTemplate, campaignCounterKey);
    baseCampaign.setId(id);
    // save the campaign to the repository
    BaseDto<Campaign> savedCampaign = campaignCommandRepository.save(baseCampaign);
    return Optional.of(savedCampaign);
  }

  /**
   * Update an existing campaign.
   *
   * @param id the ID of the campaign
   * @param campaign the campaign with updated fields
   * @return Optional containing the updated campaign if successful, otherwise empty.
   * @throws APIRequestValidationException if the campaign with the given ID is not found,
   * or if there is a version conflict.
   */
  @SuppressWarnings("unchecked")
  public Optional<BaseDto<Campaign>> updateCampaign(String id, BaseDto<Campaign> campaign) {
    // fetch the existing campaign by ID
    BaseDto<Campaign> existingCampaign = null;
    try {
      existingCampaign = (BaseDto<Campaign>) apiUtils
          .fetchDtoById(campaignsApiUrl, id, new TypeReference<BaseDto<Campaign>>() {})
          .orElseThrow(() -> new APIRequestValidationException(
              new APIRequestValidationMessage("Api request validation failed",
                  Map.of("error", String.format("Campaign with ID %s not found for update.", id))))
          );
    } catch (Throwable e) {
      throw ((APIRequestValidationException)e);
    }

    /** STEP 1: Check version for optimistic locking **/
    Integer currentVersion = campaign.getVersion(); // from client body
    Integer existingVersion  = existingCampaign.getVersion(); // from DB
    if (currentVersion == null || !currentVersion.equals(existingVersion)) {
      throw new APIRequestVersionConflictException(
          new APIRequestValidationMessage("Api request validation failed",
              Map.of("error", "Campaign %s has changed (expected version=%s). Please reload and retry."
                  .formatted(id, existingVersion))));
    }

    /** STEP 2: Apply updates of the existing data-model **/
    existingCampaign.setUpdatedAt(LocalDateTime.now());
    existingCampaign.getData().setName(campaign.getData().getName());
    existingCampaign.getData().setDescription(campaign.getData().getDescription());
    existingCampaign.getData().setStartDate(campaign.getData().getStartDate());
    existingCampaign.getData().setEndDate(campaign.getData().getEndDate());
    existingCampaign.getData().setBudget(campaign.getData().getBudget());
    existingCampaign.getData().setStatus(campaign.getData().getStatus());
    existingCampaign.getData().setOfferIds(campaign.getData().getOfferIds());
    // increment version for optimistic locking
    existingCampaign.setVersion(existingVersion+1);

    try {
      /** STEP 3: Save the updated data-model **/
      return Optional.of(campaignCommandRepository.save(existingCampaign));
    } catch (OptimisticLockingFailureException e) {
      throw new APIRequestVersionConflictException(
          new APIRequestValidationMessage(
              "Api request validation failed",
              Map.of("error", String.format("Campaign with ID %s has been modified by another process. " +
                  "Please retrieve the latest version and try again.", id))
          )
      );
    }
  }

  /**
   * Delete a campaign by its ID.
   *
   * @param id the ID of the campaign to delete
   * @throws APIRequestValidationException if the campaign with the given ID is not found.
   */
  public void deleteCampaign(String id) {
    campaignCommandRepository.findById(id)
        .ifPresentOrElse(
            c -> campaignCommandRepository.deleteById(id),
            () -> {
              throw new APIRequestValidationException(
                  new APIRequestValidationMessage(
                      "Api request validation failed",
                      Map.of("error", String.format("Campaign with ID %s not found for delete.", id))
                  )
              );
            }
        );

  }
}
