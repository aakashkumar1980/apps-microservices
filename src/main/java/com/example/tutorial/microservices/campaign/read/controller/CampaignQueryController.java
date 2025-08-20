package com.example.tutorial.microservices.campaign.read.controller;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.microservices.campaign.read.service.CampaignQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    List<BaseDto<Campaign>> allCampaigns = campaignQueryService.getAllCampaigns();
    log.info("Total campaigns found: {}", allCampaigns.size());
    return ResponseEntity.ok(allCampaigns);
  }

  /**
   * Retrieves a campaign by its ID.
   *
   * @param id the ID of the campaign to retrieve
   * @return a ResponseEntity containing the BaseDto<Campaign> object if found,
   * or a 404 Not Found status if not found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<BaseDto<Campaign>> getCampaignById(@PathVariable String id) {
    Optional<BaseDto<Campaign>> campaignOptional = campaignQueryService.getCampaignById(id);
    if (campaignOptional.isPresent()) {
      log.info("Campaign with ID: {} found", id);
      return ResponseEntity.ok(campaignOptional.get());

    } else {
      log.warn("Campaign with ID: {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves campaigns by their status.
   *
   * @param value the status of the campaigns to retrieve
   * @return a ResponseEntity containing a list of campaigns with the specified status, or 404 if none found
   */
  @GetMapping("/status")
  public ResponseEntity<List<BaseDto<Campaign>>> getCampaignsByStatus(@RequestParam String value) {
    List<BaseDto<Campaign>> campaigns = campaignQueryService.getCampaignsByStatus(value);

    if (campaigns.isEmpty()) {
      log.warn("No campaigns found with status: {}", value);
      return ResponseEntity.notFound().build();

    } else {
      log.info("Found {} campaigns with status: {}", campaigns.size(), value);
      return ResponseEntity.ok(campaigns);
    }
  }

  /**
   * Retrieves campaigns by their offer ID.
   *
   * @param offerId the ID of the offer associated with the campaigns
   * @return a ResponseEntity containing a list of campaigns with the specified offer ID, or 404 if none found
   */
  @GetMapping("/offers/{offerId}")
  public ResponseEntity<List<BaseDto<Campaign>>> getCampaignsByOfferId(@PathVariable String offerId) {
    List<BaseDto<Campaign>> campaigns = campaignQueryService.getCampaignsByOfferId(offerId);

    if (campaigns.isEmpty()) {
      log.warn("No campaigns found with offer ID: {}", offerId);
      return ResponseEntity.notFound().build();

    } else {
      log.info("Found {} campaigns with offer ID: {}", campaigns.size(), offerId);
      return ResponseEntity.ok(campaigns);
    }
  }
}
