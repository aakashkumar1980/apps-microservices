package com.example.tutorial.microservices.campaign.read.controller;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.microservices.campaign.read.service.CampaignQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignQueryController {

  private static final Logger log = LoggerFactory.getLogger(CampaignQueryController.class);

  @Autowired
  private CampaignQueryService campaignQueryService;

  /**
   * Retrieves all campaigns.
   *
   * @return a ResponseEntity containing a list of all campaign objects.
   */
  @GetMapping
  public ResponseEntity<List<Campaign>> getAllCampaigns() {
    List<Campaign> allCampaigns = campaignQueryService.getAllCampaigns();
    log.info("Total campaigns found: {}", allCampaigns.size());
    return ResponseEntity.ok(allCampaigns);
  }

  /**
   * Retrieves a campaign by its ID.
   *
   * @param id the ID of the campaign
   * @return the campaign with the specified ID, or 404 if not found
   */
  @GetMapping("/{id}")
  public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
    Optional<Campaign> campaignOptional = campaignQueryService.getCampaignById(id);
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
   * @return a list of campaigns with the specified status, or 404 if none found
   */
  @GetMapping("/status")
  public ResponseEntity<List<Campaign>> getCampaignsByStatus(@RequestParam String value) {
    List<Campaign> campaigns = campaignQueryService.getCampaignsByStatus(value);
    if (campaigns.isEmpty()) {
      log.warn("No campaigns found with status: {}", value);
      return ResponseEntity.notFound().build();

    } else {
      log.info("Found {} campaigns with status: {}", campaigns.size(), value);
      return ResponseEntity.ok(campaigns);
    }
  }
}
