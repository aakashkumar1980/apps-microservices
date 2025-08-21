package com.example.tutorial.microservices.campaign.read.service;

import com.example.tutorial.common.datamodel.BaseDto;
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
   *
   * @return List of BaseDto<Campaign>
   */
  public List<BaseDto<Campaign>> getAllCampaigns() {
    List<BaseDto<Campaign>> campaigns = campaignQueryRepository.getAllCampaigns();
    log.info("Retrieved {} campaigns", campaigns.size());
    return campaigns;
  }

  /**
   * Retrieves a campaign by its unique ID.
   *
   * @param id the unique identifier of the campaign
   * @return an {@link Optional} containing the found {@link Campaign}, or {@link Optional#empty()} if not found
   */
  public Optional<BaseDto<Campaign>> getCampaignById(String id) {
    Optional<BaseDto<Campaign>> campaign = campaignQueryRepository.findById(id);
    campaign.ifPresentOrElse(
        c -> log.info("Campaign found with ID: {}", id),
        () -> log.warn("No campaign found with ID: {}", id)
    );
    return campaign;
  }

  /**
   * Retrieves campaigns by their status.
   *
   * @param status the status of the campaigns to retrieve
   * @return a list of campaigns with the specified status
   */
  public List<BaseDto<Campaign>> getCampaignsByStatus(CampaignStatus status) {
    List<BaseDto<Campaign>> campaigns = campaignQueryRepository.getCampaignsByStatus(status);
    log.info("Retrieved {} campaigns with status: {}", campaigns.size(), status);
    return campaigns;
  }

  /**
   * Retrieves campaigns that contain a specific offer ID.
   *
   * @param offerId the offer ID to search for in campaigns
   * @return a list of campaigns that contain the specified offer ID
   */
  public List<BaseDto<Campaign>> getCampaignsByOfferId(String offerId) {
    List<BaseDto<Campaign>> campaigns = campaignQueryRepository.getCampaignsByOfferId(offerId);
    log.info("Retrieved {} campaigns with offer ID: {}", campaigns.size(), offerId);
    return campaigns;
  }
}
