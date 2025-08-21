package com.example.tutorial.microservices.campaign.read.controller;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
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
   * The URI is /api/campaigns.
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
   * The URI is /api/campaigns/{id}.
   *
   * @param id the ID of the campaign
   * @return a ResponseEntity containing the campaign object if found, or 404 if not found
   */
  @GetMapping("/{id}")
  public ResponseEntity<Campaign> getCampaignById(@PathVariable String id) {
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
   * The URI is /api/campaigns/status?status={status}.
   *
   * @param status the status of the campaigns to retrieve
   * @return a ResponseEntity containing a list of campaigns with the specified status, or 404 if none found
   */
  @GetMapping("/status")
  public ResponseEntity<List<Campaign>> getCampaignsByStatus(@RequestParam CampaignStatus status) {
    List<Campaign> campaigns = campaignQueryService.getCampaignsByStatus(status);
    if (!campaigns.isEmpty()) {
      log.info("Found {} campaigns with status: {}", campaigns.size(), status);
      return ResponseEntity.ok(campaigns);

    } else {
      log.warn("No campaigns found with status: {}", status);
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves campaigns that contain a specific offer ID.
   * The URI is /api/campaigns/offer?offerId={offerId}.
   *
   * @param offerId the offer ID to search for in campaigns
   * @return a ResponseEntity containing a list of campaigns that contain the specified offer ID, or 404 if none found
   */
  @GetMapping("/offer")
  public ResponseEntity<List<Campaign>> getCampaignsByOfferId(@RequestParam String offerId) {
    List<Campaign> campaigns = campaignQueryService.getCampaignsByOfferId(offerId);
    if (!campaigns.isEmpty()) {
      log.info("Found {} campaigns containing offer ID: {}", campaigns.size(), offerId);
      return ResponseEntity.ok(campaigns);

    } else {
      log.warn("No campaigns found containing offer ID: {}", offerId);
      return ResponseEntity.notFound().build();
    }
  }

}
