package com.example.tutorial.microservices.campaign.write.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.DBUtils;
import com.example.tutorial.microservices.campaign.write.repository.CampaignCommandRepository;
import com.example.tutorial.microservices.campaign.write.service.events.publisher.CampaignEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CampaignCommandService {

  private static final Logger log = LoggerFactory.getLogger(CampaignCommandService.class);

  @Autowired
  private CampaignCommandRepository campaignCommandRepository;

  @Autowired
  private CouchbaseTemplate couchbaseTemplate;

  @Value("${campaign.counter.key:campaign_counter}")
  private String campaignCounterKey;

  @Autowired
  private CampaignEventPublisher campaignEventPublisher;

  @Autowired
  private APIUtils apiUtils;

  @Value("${campaigns.api.url}")
  String campaignsApiUrl;

  /**
   * Create a new campaign and publish an event to the kafka event bus.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param campaign the campaign to create
   * @return the ID of the created campaign
   */
  public String createCampaign(Campaign campaign) {
    log.info("Creating campaign: {}", campaign);

    // generate a unique ID for the campaign
    String id = "campaign::" + DBUtils.getUniqueCounter(couchbaseTemplate, campaignCounterKey);
    // build the BaseDto for the campaign with default values
    BaseDto<Campaign> baseDto = BaseDto.build(campaign);
    baseDto.setId(id);
    // Save the campaign to the repository
    BaseDto<Campaign> savedDto = campaignCommandRepository.save(baseDto);

    // Publish the campaign created event to kafka event bus
    campaignEventPublisher.publishCreateCampaignEvent(savedDto);
    return savedDto.getId();
  }

  /**
   * Update an existing campaign and publish an event to the kafka event bus.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param baseDto the BaseDto containing the campaign data to update
   */
  public void updateCampaign(BaseDto<Campaign> baseDto) throws JsonProcessingException {
    log.info("Updating campaign: {}", baseDto);

    /** DATA VALIDATION: override offer ids by keeping the original as it shouldn't be changed once assigned **/
    BaseDto<Campaign> originalCampaign = apiUtils.fetchBaseDtoById(
        campaignsApiUrl, baseDto, new TypeReference<BaseDto<Campaign>>() {});
    baseDto.getData().setOfferIds(originalCampaign.getData().getOfferIds());

    // Update the updated campaign to the repository
    baseDto.setUpdatedAt(LocalDateTime.now());
    BaseDto<Campaign> updatedDto = campaignCommandRepository.save(baseDto);

    // Publish the campaign updated event to kafka event bus
    campaignEventPublisher.publishUpdateCampaignEvent(updatedDto);
  }

  /**
   * Delete a campaign by its ID and publish an event to the kafka event bus.
   * TODO: Implement @Retry as this is an internal service call
   *
   * @param id the ID of the campaign to delete
   */
  public void deleteCampaign(String id) {
    log.info("Deleting campaign with ID: {}", id);

    // Delete the campaign from the repository
    campaignCommandRepository.deleteById(id);

    // Publish the campaign created event to kafka event bus
    campaignEventPublisher.publishDeleteCampaignEvent(id);
  }
}
