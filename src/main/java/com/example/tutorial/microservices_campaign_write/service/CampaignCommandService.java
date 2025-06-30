package com.example.tutorial.microservices_campaign_write.service;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.exceptions.ApplicationFunctionalException;
import com.example.tutorial.common.utils.MockDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignCommandService {

  @Autowired
  private MockDataUtil mockDataUtil;

  /**
   * Create a new campaign.
   * @param campaign the campaign to create
   * @return the ID of the created campaign
   */
  public Long createCampaign(Campaign campaign) {
      List<Campaign> campaigns = mockDataUtil.campaignSupplier.get();
      campaigns.add(campaign);
      return campaign.getId();
    }

  /**
   * Update an existing campaign.
   * @param id the ID of the campaign
   * @param campaign the campaign with updated fields
   * @throws ApplicationFunctionalException if the campaign with the given ID does not exist
   */
  public void updateCampaign(Long id, Campaign campaign) {
    List<Campaign> campaigns = mockDataUtil.campaignSupplier.get();
    // Use streams to find the campaign with the given ID
    Optional<Campaign> existingCampaign = campaigns.stream()
        .filter(c -> c.getId().equals(id))
        .findFirst();

    // If the campaign exists, update its fields
    if (existingCampaign.isPresent()) {
      Campaign exCampaign = existingCampaign.get();
      exCampaign.setName(campaign.getName());
      exCampaign.setDescription(campaign.getDescription());
      exCampaign.setStatus(campaign.getStatus());
      exCampaign.setStartDate(campaign.getStartDate());
      exCampaign.setEndDate(campaign.getEndDate());
      exCampaign.setBudget(campaign.getBudget());
    } else {
      throw new ApplicationFunctionalException(String.format("Campaign with ID %d not found", id));
    }
  }

  /**
   * Delete a campaign by its ID.
   * @param id the ID of the campaign to delete
   */
  public void deleteCampaign(Long id) {
    List<Campaign> campaigns = mockDataUtil.campaignSupplier.get();
    // Use removeIf with streams for concise removal
    campaigns.removeIf(c -> c.getId().equals(id));
  }
}
