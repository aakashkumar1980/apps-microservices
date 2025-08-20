package com.example.tutorial.microservices.campaign.write.service;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.exceptions.api.APIRequestValidationException;
import com.example.tutorial.common.exceptions.api.APIRequestValidationMessage;
import com.example.tutorial.common.utils.DBUtils;
import com.example.tutorial.microservices.campaign.write.repository.CampaignCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
   * Update an existing campaign by its ID.
   *
   * @param id the ID of the campaign to update
   * @param campaign the updated campaign data
   * @return Optional containing the updated campaign if successful, otherwise empty.
   * @throws APIRequestValidationException if the campaign with the given ID is not found.
   */
  public Optional<BaseDto<Campaign>> updateCampaign(String id, BaseDto<Campaign> campaign) {
    // fetch the existing campaign by ID
    Optional<BaseDto<Campaign>> existingCampaign = campaignCommandRepository.findById(id);
    if (existingCampaign.isPresent()) {
      // update the fields of the existing campaign with the new values
      BaseDto<Campaign> exCampaign = existingCampaign.get();
      exCampaign.getData().setName(campaign.getData().getName());
      exCampaign.getData().setDescription(campaign.getData().getDescription());
      exCampaign.getData().setStatus(campaign.getData().getStatus());
      exCampaign.getData().setStartDate(campaign.getData().getStartDate());
      exCampaign.getData().setEndDate(campaign.getData().getEndDate());
      exCampaign.getData().setBudget(campaign.getData().getBudget());
      return Optional.of(campaignCommandRepository.save(exCampaign));

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
    Optional<BaseDto<Campaign>> existingCampaign = campaignCommandRepository.findById(id);
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
