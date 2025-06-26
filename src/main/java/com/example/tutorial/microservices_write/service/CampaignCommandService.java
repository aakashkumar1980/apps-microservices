package com.example.tutorial.microservices_write.service;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.dto.campaign.utils.MockDataUtil;
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
   */
  public void updateCampaign(Long id, Campaign campaign) {
    List<Campaign> campaigns = mockDataUtil.campaignSupplier.get();
    // Find the campaign with the given ID
    Optional<Campaign> existingCampaign = Optional.empty();
    for (Campaign c : campaigns) {
        if (c.getId().equals(id)) {
            existingCampaign = Optional.of(c);
            break;
        }
    }

    // If the campaign exists, update its fields
    if (existingCampaign.isPresent()) {
      Campaign exCampaign = existingCampaign.get();
      exCampaign.setName(campaign.getName());
      exCampaign.setDescription(campaign.getDescription());
      exCampaign.setStatus(campaign.getStatus());
      exCampaign.setStartDate(campaign.getStartDate());
      exCampaign.setEndDate(campaign.getEndDate());
      exCampaign.setBudget(campaign.getBudget());
    }
  }

  /**
   * Delete a campaign by its ID.
   * @param id the ID of the campaign to delete
   */
  public void deleteCampaign(Long id) {
    List<Campaign> campaigns = mockDataUtil.campaignSupplier.get();
    // Find and remove the campaign with the given ID
    for (int i = 0; i < campaigns.size(); i++) {
      if (campaigns.get(i).getId().equals(id)) {
        campaigns.remove(i);
        break;
      }
    }
  }
}
