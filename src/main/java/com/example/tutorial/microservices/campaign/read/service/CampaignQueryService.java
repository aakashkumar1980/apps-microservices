package com.example.tutorial.microservices.campaign.read.service;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.campaign.Campaign;
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
    return campaignQueryRepository.findByIdStartingWith("campaign::%");
  }

  /**
   * Retrieves a campaign by its unique ID.
   *
   * @param id the unique identifier of the campaign
   * @return an {@link Optional} containing the found {@link Campaign}, or {@link Optional#empty()} if not found
   */
  public Optional<BaseDto<Campaign>> getCampaignById(String id) {
    return campaignQueryRepository.findById(id);
  }

  /**
   * Retrieves campaigns by their status.
   *
   * @param value the status of the campaigns to retrieve
   * @return a list of campaigns with the specified status
   */
  public List<BaseDto<Campaign>> getCampaignsByStatus(String value) {
    return campaignQueryRepository.findCampaignByStatus(value);
  }
}
