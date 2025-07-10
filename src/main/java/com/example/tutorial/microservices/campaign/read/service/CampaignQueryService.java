package com.example.tutorial.microservices.campaign.read.service;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.microservices.campaign.read.repository.CampaignQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
