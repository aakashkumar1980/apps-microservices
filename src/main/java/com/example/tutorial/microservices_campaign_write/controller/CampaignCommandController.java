package com.example.tutorial.microservices_campaign_write.controller;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.microservices_campaign_write.service.CampaignCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignCommandController {

    private static final Logger log = LoggerFactory.getLogger(CampaignCommandController.class);

    @Autowired
    private CampaignCommandService campaignCommandService;

    /**
     * Create a new campaign. This endpoint is used to create a new campaign.
     * @param campaign the campaign data to be created
     * @return ResponseEntity with a success message and the ID of the created campaign
     */
    @PostMapping
    public ResponseEntity<String> createCampaign(@RequestBody Campaign campaign) {
        log.info("Received request to create campaign: {}", campaign);
        Long id = campaignCommandService.createCampaign(campaign);
        return ResponseEntity.ok(String.format("Campaign created successfully with ID: %d", id));
    }

    /**
     * Update an existing campaign. This endpoint is used to update an existing campaign.
     *
     * @param id the ID of the campaign to be updated
     * @param campaign the updated campaign data
     * @return ResponseEntity with a success message
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCampaign(@PathVariable Long id, @RequestBody Campaign campaign) {
        log.info("Received request to update campaign with ID: {}, Data: {}", id, campaign);
        campaignCommandService.updateCampaign(id, campaign);
        return ResponseEntity.ok("Campaign updated successfully");
    }

    /**
     * Delete a campaign by ID. This endpoint is used to delete a campaign.
     *
     * @param id the ID of the campaign to be deleted
     * @return ResponseEntity with a success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable Long id) {
        log.info("Received request to delete campaign with ID: {}", id);
        campaignCommandService.deleteCampaign(id);
        return ResponseEntity.ok("Campaign deleted successfully");
    }
}
