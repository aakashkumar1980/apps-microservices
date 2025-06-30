package com.example.tutorial.microservices_campaign_read.service;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.microservices_campaign_read.repository.CampaignQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignQueryService {

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
   * Returns a campaign by its ID.
   * @param id Campaign ID
   * @return Optional containing the Campaign if found, otherwise empty
   */
  public Optional<Campaign> getCampaignById(String id) {
    return campaignQueryRepository.findById(id);
  }
}
