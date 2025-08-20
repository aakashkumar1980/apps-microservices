package com.example.tutorial.microservices.campaign.write.controller;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.microservices.campaign.write.service.CampaignCommandService;
import jakarta.validation.Valid;
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
     * Handles the creation of a new campaign.
     *
     * @param campaign the campaign to be created
     * @return a response entity with the ID of the created campaign
     */
    @PostMapping
    public ResponseEntity<String> createCampaign(@Valid @RequestBody Campaign campaign) {
        log.info("Received request to create campaign: {}", campaign);

        String id = campaignCommandService.createCampaign(campaign);
        return ResponseEntity.ok(String.format("Campaign created successfully with ID: %s", id));
    }

    /**
     * Handles the update of an existing campaign.
     *
     * @param campaign the base DTO containing the campaign to be updated
     * @return a response entity with a success message
     */
    @PutMapping
    public ResponseEntity<String> updateCampaign(@Valid @RequestBody BaseDto<Campaign> campaign) {
        log.info("Received request to update campaign: {}", campaign);

        campaignCommandService.updateCampaign(campaign);
        return ResponseEntity.ok(String.format("Campaign updated successfully with ID: %s", campaign.getId()));
    }

    /**
     * Handles the cancellation of a campaign by its ID.
     *
     * @param id the ID of the campaign to be cancelled
     * @return a response entity with a success message
     */
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<String> cancelCampaign(@PathVariable String id) {
        log.info("Received request to cancel campaign with ID: {}", id);

        campaignCommandService.cancelCampaign(id);
        return ResponseEntity.ok("Campaign cancelled successfully");
    }
}
