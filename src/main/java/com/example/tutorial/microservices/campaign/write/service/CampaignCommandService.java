package com.example.tutorial.microservices.campaign.write.service;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.exceptions.RequestValidationException;
import com.example.tutorial.common.exceptions.RequestValidationMessage;
import com.example.tutorial.common.utils.MockDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class CampaignCommandService {

  private static final Logger log = LoggerFactory.getLogger(CampaignCommandService.class);
  @Autowired
  private MockDataUtil mockDataUtil;

  /**
   * Create a new campaign.
   * @param campaign the campaign to create
   * @return Optional containing the created campaign if successful.
   */
  public Optional<Campaign> createCampaign(Campaign campaign) {
    List<Campaign> campaigns = mockDataUtil.campaignSupplier.get();
    campaigns.add(campaign);

    // generate a random ID for mock data
    if (campaign.getId() == null) {
      campaign.setId((long) (Math.random() * 1000));
      mockDataUtil.addCampaign(campaign);
    }
    return Optional.of(campaign);
  }

  /**
   * Update an existing campaign.
   *
   * @param id the ID of the campaign
   * @param campaign the campaign with updated fields
   * @return Optional containing the updated campaign if successful, otherwise empty.
   * @throws RequestValidationException if the campaign with the given ID is not found.
   */
  public Optional<Campaign> updateCampaign(Long id, Campaign campaign) {
    List<Campaign> campaigns = mockDataUtil.campaignSupplier.get();

    // find the campaign with the given ID
    Optional<Campaign> existingCampaign = getCampaignById(id, campaigns);
    // if the campaign exists, update its fields
    if (existingCampaign.isPresent()) {
      Campaign exCampaign = existingCampaign.get();
      exCampaign.setName(campaign.getName());
      exCampaign.setDescription(campaign.getDescription());
      exCampaign.setStatus(campaign.getStatus());
      exCampaign.setStartDate(campaign.getStartDate());
      exCampaign.setEndDate(campaign.getEndDate());
      exCampaign.setBudget(campaign.getBudget());
      return existingCampaign;

    } else {
      RequestValidationMessage validationMessage = new RequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Campaign with ID %s not found for update.,", id))
      );
      throw new RequestValidationException(validationMessage);
    }
  }

  private Optional<Campaign> getCampaignById(
      Long id, List<Campaign> campaigns) {
    for (Campaign c : campaigns) {
        if (Objects.equals(c.getId(), id)) {
            return Optional.of(c);
        }
    }
    return Optional.empty();
  }

  /**
   * Delete a campaign by its ID.
   *
   * @param id the ID of the campaign to delete
   * @throws RequestValidationException if the campaign with the given ID is not found.
   */
  public void deleteCampaign(Long id) {
    List<Campaign> campaigns = mockDataUtil.campaignSupplier.get();

    // find and remove the campaign with the given ID
    for (int i = 0; i < campaigns.size(); i++) {
      if (campaigns.get(i).getId().equals(id)) {
        campaigns.remove(i);
        return;
      }
    }

    // if the campaign with the given ID is not found, throw an exception
    RequestValidationMessage validationMessage = new RequestValidationMessage(
        "Api request validation failed",
        Map.of("error", String.format("Campaign with ID %s not found or deletion.", id))
    );
    throw new RequestValidationException(validationMessage);
  }
}
