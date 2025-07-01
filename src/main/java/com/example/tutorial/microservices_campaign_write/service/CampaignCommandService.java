package com.example.tutorial.microservices_campaign_write.service;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.exceptions.ApplicationFunctionalException;
import com.example.tutorial.microservices_campaign_write.repository.CampaignCommandRepository;
import com.example.tutorial.common.utils.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CampaignCommandService {

  @Autowired
  private CampaignCommandRepository campaignCommandRepository;

  @Autowired
  private CouchbaseTemplate couchbaseTemplate;

  @Value("${campaign.counter.key:campaign_counter}")
  private String campaignCounterKey;

  /**
   * Create a new campaign.
   * @param campaign the campaign to create
   * @return the ID of the created campaign
   */
  public String createCampaign(Campaign campaign) {
      // Use DBUtils to get a unique sequential ID
      long counter = DBUtils.getUniqueCounter(couchbaseTemplate, campaignCounterKey);
      String id = "campaign::" + counter;
      campaign.setId(id);
      Campaign saved = campaignCommandRepository.save(campaign);
      return saved.getId();
    }

  /**
   * Update an existing campaign.
   * @param id the ID of the campaign
   * @param campaign the campaign with updated fields
   * @throws ApplicationFunctionalException if the campaign with the given ID does not exist
   */
  public void updateCampaign(String id, Campaign campaign) {
    Optional<Campaign> existingCampaign = campaignCommandRepository.findById(id);

    if (existingCampaign.isPresent()) {
      Campaign exCampaign = existingCampaign.get();
      exCampaign.setName(campaign.getName());
      exCampaign.setDescription(campaign.getDescription());
      exCampaign.setStatus(campaign.getStatus());
      exCampaign.setStartDate(campaign.getStartDate());
      exCampaign.setEndDate(campaign.getEndDate());
      exCampaign.setBudget(campaign.getBudget());
      campaignCommandRepository.save(exCampaign);
    } else {
      throw new ApplicationFunctionalException(String.format("Campaign with ID %s not found", id));
    }
  }

  /**
   * Delete a campaign by its ID.
   * @param id the ID of the campaign to delete
   */
  public void deleteCampaign(String id) {
    campaignCommandRepository.deleteById(id);
  }
}
