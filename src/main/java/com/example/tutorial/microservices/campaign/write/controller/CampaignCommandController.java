package com.example.tutorial.microservices.campaign.write.controller;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.microservices.campaign.write.service.CampaignCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignCommandController {

    private static final Logger log = LoggerFactory.getLogger(CampaignCommandController.class);

    @Autowired
    private CampaignCommandService campaignCommandService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

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
     * @param baseDto the base DTO containing the campaign to be updated
     * @return a response entity with a success message
     */
    @PutMapping
    public ResponseEntity<String> updateCampaign(@Valid @RequestBody BaseDto<Campaign> baseDto) throws JsonProcessingException {
        log.info("Received request to update campaign: {}", baseDto);

        /** DATA VALIDATION: override offer ids by keeping the original as it shouldn't be changed once assigned **/
        String originalCampaignString = restTemplate.getForObject(
                String.format("http://localhost:8080/api/campaigns/%s", baseDto.getId()), String.class);
        BaseDto<Campaign> originalCampaign = objectMapper.readValue(
            originalCampaignString, new TypeReference<BaseDto<Campaign>>() {});
        log.warn("Overriding provided offer IDs: {} with the original offer IDs: {}",
            baseDto.getData().getOfferIds(), originalCampaign.getData().getOfferIds());
        baseDto.getData().setOfferIds(originalCampaign.getData().getOfferIds());

        campaignCommandService.updateCampaign(baseDto);
        return ResponseEntity.ok(String.format("Campaign updated successfully with ID: %s", baseDto.getId()));
    }

    /**
     * Handles the deletion of a campaign by its ID.
     *
     * @param id the ID of the campaign to be deleted
     * @return a response entity with a success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable String id) {
        log.info("Received request to delete campaign with ID: {}", id);
        campaignCommandService.deleteCampaign(id);
        return ResponseEntity.ok("Campaign deleted successfully");
    }
}
