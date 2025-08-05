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
   * NOTE: Here we are not using the CouchbaseRepository's findAll method, because the id for different data models
   * starts like 'campaign::1', 'offer::1', etc. where the prefix is used to identify the type of document.
   * Therefore, it needs a custom query to filter by the prefix.
   *
   * @return List of BaseDto<Campaign>
   */
  public List<BaseDto<Campaign>> getAllCampaigns() {
    List<BaseDto<Campaign>> allCampaigns = campaignQueryRepository.getAllCampaigns();
    log.info("Total campaigns fetched: {}", allCampaigns.size());

    return campaignQueryRepository.getAllCampaigns();
  }

  /**
   * Returns a campaign by its ID.
   *
   * @param id the ID of the campaign
   * @return Optional containing the BaseDto<Campaign> if found, or empty if not found
   */
  public Optional<BaseDto<Campaign>> getCampaignById(String id) {
    Optional<BaseDto<Campaign>> campaign = campaignQueryRepository.findById(id);
    if (campaign.isPresent()) {
      log.info("Campaign with ID: {} found", id);
    } else {
      log.warn("Campaign with ID: {} not found", id);
    }

    return campaign;
  }
}
