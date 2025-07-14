package com.example.tutorial.microservices.campaign.read.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.campaign.Campaign;
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
  public List<BaseDto<Campaign>> getAllCampaigns() {
    log.info("Fetching all campaigns from the repository");
    return campaignQueryRepository.getAllCampaigns();
  }

  /**
   * Returns a campaign by its ID.
   * @param id the ID of the campaign
   * @return Optional containing the BaseDto<Campaign> if found, or empty if not found
   */
  public Optional<BaseDto<Campaign>> getCampaignById(String id) {
    log.info("Fetching campaign with ID: {}", id);
    return campaignQueryRepository.getCampaignById(id);
  }
}
