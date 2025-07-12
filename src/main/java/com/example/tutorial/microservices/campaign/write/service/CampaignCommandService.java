package com.example.tutorial.microservices.campaign.write.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.utils.DBUtils;
import com.example.tutorial.microservices.campaign.write.repository.CampaignCommandRepository;
import com.example.tutorial.microservices.campaign.write.service.events.CampaignCommandEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CampaignCommandService {

  @Autowired
  private CampaignCommandRepository campaignCommandRepository;

  @Autowired
  private CouchbaseTemplate couchbaseTemplate;

  @Value("${campaign.counter.key:campaign_counter}")
  private String campaignCounterKey;

  @Autowired
  private CampaignCommandEventPublisher campaignCommandEventPublisher;

  /**
   * Create a new campaign and publish an event to the kafka event bus.
   * @param campaign the campaign to create
   * @return the ID of the created campaign
   */
  public String createCampaign(Campaign campaign) {
    // Use DBUtils to get a unique sequential ID
    long counter = DBUtils.getUniqueCounter(couchbaseTemplate, campaignCounterKey);
    String id = "campaign::" + counter;

    BaseDto<Campaign> baseDto = BaseDto.build(campaign);
    baseDto.setId(id);
    BaseDto<Campaign> savedDto = campaignCommandRepository.save(baseDto);

    // Publish the campaign created event to kafka event bus
    campaignCommandEventPublisher.publishCreateCampaignEvent(savedDto);
    return savedDto.getId();
  }

/**
   * Update an existing campaign and publish an event to the kafka event bus.
   * @param baseDto the BaseDto containing the campaign data to update
   */
  public void updateCampaign(BaseDto<Campaign> baseDto) {
    baseDto.setUpdatedAt(LocalDateTime.now());
    BaseDto<Campaign> updatedDto = campaignCommandRepository.save(baseDto);

    // Publish the campaign updated event to kafka event bus
    campaignCommandEventPublisher.publishUpdateCampaignEvent(updatedDto);
  }


  /**
   * Delete a campaign by its ID and publish an event to the kafka event bus.
   * @param id the ID of the campaign to delete
   */
  public void deleteCampaign(String id) {
    campaignCommandRepository.deleteById(id);

    // Publish the campaign created event to kafka event bus
    campaignCommandEventPublisher.publishDeleteCampaignEvent(id);
  }
}
