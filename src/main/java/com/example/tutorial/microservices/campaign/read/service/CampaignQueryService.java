package com.example.tutorial.microservices.campaign.read.service;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import com.example.tutorial.microservices.campaign.read.repository.CampaignQueryRepository;
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
  private CampaignQueryRepository campaignQueryRepository;

  /**
   * Returns all campaigns.
   * @return List of Campaigns
   */
  public List<Campaign> getAllCampaigns() {
    return campaignQueryRepository.findAll();
  }

  /**
   * Retrieves a campaign by its unique ID.
   *
   * @param id the unique identifier of the campaign
   * @return an {@link Optional} containing the found {@link Campaign}, or {@link Optional#empty()} if not found
   */
  public Optional<Campaign> getCampaignById(String id) {
    return campaignQueryRepository.findById(id);
  }

  /**
   * Retrieves campaigns by their status.
   *
   * @param status the status of the campaigns to retrieve
   * @return a list of campaigns with the specified status
   */
  public List<Campaign> getCampaignsByStatus(CampaignStatus status) {
    return campaignQueryRepository.getCampaignsByStatus(status);
  }

  /**
   * Retrieves campaigns that contain a specific offer ID.
   *
   * @param offerId the offer ID to search for in campaigns
   * @return a list of campaigns that contain the specified offer ID
   */
  public List<Campaign> getCampaignsByOfferId(String offerId) {
    return campaignQueryRepository.getCampaignsByOfferId(offerId);
  }
}
