package com.example.tutorial.microservices.campaign.read.service;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.utils.MockDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignQueryService {

  private static final Logger log = LoggerFactory.getLogger(CampaignQueryService.class);

  @Autowired
  private MockDataUtil mockDataUtil;

  /**
   * Returns all campaigns.
   * @return List of Campaigns
   */
  public List<Campaign> getAllCampaigns() {
    return mockDataUtil.campaignSupplier.get();
  }

  /**
   * Returns a campaign by its ID.
   * @param id Campaign ID
   * @return Optional containing the Campaign if found, otherwise empty
   */
  public Optional<Campaign> getCampaignById(Long id) {
    return mockDataUtil.campaignSupplier.get()
        .stream()
        .filter(c -> c.getId().equals(id))
        .findFirst();
  }

  /**
   * Retrieves campaigns by their status.
   *
   * @param value the status of the campaigns to retrieve
   * @return a list of campaigns with the specified status
   */
  public List<Campaign> getCampaignsByStatus(String value) {
    List<Campaign> filteredCampaigns = mockDataUtil.campaignSupplier.get()
        .stream()
        .filter(c -> c.getStatus().name().equalsIgnoreCase(value))
        .toList();

    if (filteredCampaigns.isEmpty()) {
      log.warn("No campaigns found with status: {}", value);
    } else {
      log.info("Found {} campaigns with status: {}", filteredCampaigns.size(), value);
    }
    return filteredCampaigns;
  }
}
