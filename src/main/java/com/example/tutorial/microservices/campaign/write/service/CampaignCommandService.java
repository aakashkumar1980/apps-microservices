package com.example.tutorial.microservices.campaign.write.service;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.exceptions.RequestValidationException;
import com.example.tutorial.common.exceptions.RequestValidationMessage;
import com.example.tutorial.common.utils.MockDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

      if (campaign.getId() == null) {
        campaign.setId((long) (Math.random() * 1000)); // Example of generating a random ID
      }
      return campaign.getId();
    }

  /**
   * Update an existing campaign.
   * @param id the ID of the campaign
   * @param campaign the campaign with updated fields
   * @throws RequestValidationException if the campaign with the given ID does not exist
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
      RequestValidationMessage validationMessage = new RequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Campaign with ID %s not found", id))
      );
      throw new RequestValidationException(validationMessage);
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
