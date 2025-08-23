package com.example.tutorial.microservices.campaign.write.service;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.exceptions.api.APIRequestValidationException;
import com.example.tutorial.common.exceptions.api.APIRequestValidationMessage;
import com.example.tutorial.common.utils.DBUtils;
import com.example.tutorial.microservices.campaign.write.repository.CampaignCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

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

  @Value("${campaign.counter.key:campaign_counter}")
  private String campaignCounterKey;

  /**
   * Create a new campaign.
   * @param campaign the campaign to create
   * @return the ID of the created campaign
   */
  public Optional<Campaign> createCampaign(Campaign campaign) {
      // Use DBUtils to get a unique sequential ID
      long counter = dbUtils.getUniqueCounter(couchbaseTemplate, campaignCounterKey);
      String id = "campaign::" + counter;
      campaign.setId(id);
      campaign.setVersion(1); // initialize version to 1
      return Optional.of(campaignCommandRepository.save(campaign));
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
  public Optional<Campaign> updateCampaign(String id, Campaign campaign) {
    Campaign existingCampaign = campaignCommandRepository
        .findById(id)
        .orElseThrow(() -> new APIRequestValidationException(
            new APIRequestValidationMessage("Api request validation failed",
                Map.of("error", "Campaign with ID %s not found for update.".formatted(id))))
        );

    /** STEP 1: Check version for optimistic locking **/
    Integer currentVersion = campaign.getVersion(); // from client body
    Integer existingVersion  = existingCampaign.getVersion(); // from DB
    if (currentVersion == null || !currentVersion.equals(existingVersion)) {
      throw new APIRequestValidationException(
          new APIRequestValidationMessage("Api request validation failed",
              Map.of("error", "Campaign %s has changed (expected version=%s). Please reload and retry."
                  .formatted(id, existingVersion))));
    }

    /** STEP 2: Apply updates of the existing data-model **/
    existingCampaign.setName(campaign.getName());
    existingCampaign.setDescription(campaign.getDescription());
    existingCampaign.setStartDate(campaign.getStartDate());
    existingCampaign.setEndDate(campaign.getEndDate());
    existingCampaign.setBudget(campaign.getBudget());
    existingCampaign.setStatus(campaign.getStatus());
    existingCampaign.setOfferIds(campaign.getOfferIds());
    // increment version for optimistic locking
    existingCampaign.setVersion(existingVersion+1);

    try {
      /** STEP 3: Save the updated data-model **/
      return Optional.of(campaignCommandRepository.save(existingCampaign));
    } catch (OptimisticLockingFailureException e) {
      APIRequestValidationMessage validationMessage = new APIRequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Campaign with ID %s has been modified by another process. " +
              "Please retrieve the latest version and try again.", id))
      );
      throw new APIRequestValidationException(validationMessage);
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
              APIRequestValidationMessage validationMessage = new APIRequestValidationMessage(
                  "Api request validation failed",
                  Map.of("error", String.format("Campaign with ID %s not found for delete.", id))
              );
              throw new APIRequestValidationException(validationMessage);
            }
        );

  }
}
