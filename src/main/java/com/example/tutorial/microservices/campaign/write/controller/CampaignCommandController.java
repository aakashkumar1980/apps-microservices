package com.example.tutorial.microservices.campaign.write.controller;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.microservices.campaign.write.service.CampaignCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignCommandController {

  private static final Logger log = LoggerFactory.getLogger(CampaignCommandController.class);

  @Autowired
  private CampaignCommandService campaignCommandService;

  /**
   * Create a new campaign. This endpoint is used to create a new campaign.
   *
   * @param campaign the campaign data to be created
   * @return ResponseEntity with the created campaign and HTTP status 201 (Created)
   */
  @PostMapping
  public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
    log.info("Received request to create campaign: {}", campaign);

    Optional<Campaign> createdCampaignOptional = campaignCommandService.createCampaign(campaign);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header(HttpHeaders.LOCATION, String.format("/api/campaigns/%s", createdCampaignOptional.get().getId()))
        .body(createdCampaignOptional.get());
  }

  /**
   * Update an existing campaign. This endpoint is used to update an existing campaign.
   * TODO: Add validation to ensure the campaign exists before updating.
   *
   * @param id       the ID of the campaign to be updated
   * @param campaign the updated campaign data
   * @return ResponseEntity with the updated campaign and HTTP status 200 (OK)
   */
  @PutMapping("/{id}")
  public ResponseEntity<Campaign> updateCampaign(
      @PathVariable String id,
      @RequestBody Campaign campaign) {
    log.info("Received request to update campaign with ID: {}, Data: {}", id, campaign);

    Optional<Campaign> updatedCampaignOptional = campaignCommandService.updateCampaign(id, campaign);
    return ResponseEntity.ok(updatedCampaignOptional.get());
  }

  /**
   * Delete a campaign by ID. This endpoint is used to delete a campaign.
   * TODO: Add validation to ensure the campaign exists before deleting.
   *
   * @param id the ID of the campaign to be deleted
   * @return ResponseEntity with HTTP status 204 (No Content) if successful
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCampaign(@PathVariable String id) {
    log.info("Received request to delete campaign with ID: {}", id);

    campaignCommandService.deleteCampaign(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
