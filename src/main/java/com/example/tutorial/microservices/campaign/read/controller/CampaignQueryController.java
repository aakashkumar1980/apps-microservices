package com.example.tutorial.microservices.campaign.read.controller;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.microservices.campaign.read.service.CampaignQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignQueryController {

  private static final Logger log = LoggerFactory.getLogger(CampaignQueryController.class);

  @Autowired
  private CampaignQueryService campaignQueryService;

  /**
   * Retrieves all campaigns.
   *
   * @return a ResponseEntity containing a list of BaseDto<Campaign> objects.
   */
  @GetMapping
  public ResponseEntity<List<BaseDto<Campaign>>> getAllCampaigns() {
    log.info("Fetching all campaigns");
    return ResponseEntity.ok(campaignQueryService.getAllCampaigns());
  }

  /**
   * Retrieves a campaign by its ID.
   *
   * @param id the ID of the campaign to retrieve
   * @return a ResponseEntity containing the BaseDto<Campaign> object if found, or a 404 Not Found status if not found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<BaseDto<Campaign>> getCampaignById(@PathVariable String id) {
    log.info("Fetching campaign with ID: {}", id);
    Optional<BaseDto<Campaign>> campaign = campaignQueryService.getCampaignById(id);
    if (campaign.isPresent()) {
      return ResponseEntity.ok(campaign.get());
    } else {
      log.warn("Campaign with ID: {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }

}
