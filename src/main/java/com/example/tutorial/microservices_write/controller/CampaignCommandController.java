package com.example.tutorial.microservices_write.controller;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.microservices_write.service.CampaignCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignCommandController {

    @Autowired
    private CampaignCommandService campaignCommandService;

    @PostMapping
    public ResponseEntity<String> createCampaign(@RequestBody Campaign campaign) {
        Long id = campaignCommandService.createCampaign(campaign);
        return ResponseEntity.ok(String.format("Campaign created successfully with ID: %d", id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCampaign(@PathVariable Long id, @RequestBody Campaign campaign) {
        campaignCommandService.updateCampaign(id, campaign);
        return ResponseEntity.ok("Campaign updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable Long id) {
        campaignCommandService.deleteCampaign(id);
        return ResponseEntity.ok("Campaign deleted successfully");
    }
}
