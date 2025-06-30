package com.example.tutorial.microservices_campaign_read.controller;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.microservices_campaign_read.service.CampaignQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignQueryController {

  @Autowired
  private CampaignQueryService campaignQueryService;

  /**
   * Retrieves all campaigns.
   *
   * @return a list of all campaigns
   */
  @GetMapping
  public ResponseEntity<List<Campaign>> getAllCampaigns() {
    return ResponseEntity.ok(campaignQueryService.getAllCampaigns());
  }

  /**
   * Retrieves a campaign by its ID.
   *
   * @param id the ID of the campaign
   * @return the campaign with the specified ID, or 404 if not found
   */
  @GetMapping("/{id}")
  public ResponseEntity<Campaign> getCampaignById(@PathVariable String id) {
    return ResponseEntity.of(campaignQueryService.getCampaignById(id));
  }
}
