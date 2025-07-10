package com.example.tutorial.microservices.campaign.write.controller;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.microservices.campaign.write.service.CampaignCommandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignCommandController {

    @Autowired
    private CampaignCommandService campaignCommandService;

    @PostMapping
    public ResponseEntity<String> createCampaign(@Valid @RequestBody Campaign campaign) {
        String id = campaignCommandService.createCampaign(campaign);
        return ResponseEntity.ok(String.format("Campaign created successfully with ID: %s", id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCampaign(@PathVariable String id, @Valid @RequestBody Campaign campaign) {
        campaignCommandService.updateCampaign(id, campaign);
        return ResponseEntity.ok("Campaign updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable String id) {
        campaignCommandService.deleteCampaign(id);
        return ResponseEntity.ok("Campaign deleted successfully");
    }
}
