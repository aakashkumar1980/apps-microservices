package com.example.tutorial.microservices.campaign.write.controller;

import com.example.tutorial.common.dto.BaseDto;
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

    @PostMapping
    public ResponseEntity<String> updateCampaign(@Valid @RequestBody BaseDto<Campaign> baseDto) {
        campaignCommandService.updateCampaign(baseDto);
        return ResponseEntity.ok(String.format("Campaign updated successfully with ID: %s", baseDto.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable String id) {
        campaignCommandService.deleteCampaign(id);
        return ResponseEntity.ok("Campaign deleted successfully");
    }
}
