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
    Optional<Campaign> existingCampaign = campaignCommandRepository.findById(id);
    if (existingCampaign.isPresent()) {
      // app visible counter (optional)
      campaign.setVersion((campaign.getVersion() == null ? 0 : campaign.getVersion()) + 1);

      try {
        return Optional.of(campaignCommandRepository.save(campaign));
      } catch (OptimisticLockingFailureException e) {
        APIRequestValidationMessage validationMessage = new APIRequestValidationMessage(
            "Api request validation failed",
            Map.of("error", String.format("Campaign with ID %s has been modified by another process. " +
                "Please retrieve the latest version and try again.", id))
        );
        throw new APIRequestValidationException(validationMessage);
      }
    } else {
      APIRequestValidationMessage validationMessage = new APIRequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Campaign with ID %s not found for update.,", id))
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
    Optional<Campaign> existingCampaign = campaignCommandRepository.findById(id);
    if(existingCampaign.isPresent()) {
      campaignCommandRepository.deleteById(id);

    } else {
      APIRequestValidationMessage validationMessage = new APIRequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Campaign with ID %s not found for delete.,", id))
      );
      throw new APIRequestValidationException(validationMessage);
    }
  }
}
