package com.example.tutorial.microservices.campaign.write.service;

import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.utils.DBUtils;
import com.example.tutorial.microservices.campaign.write.repository.CampaignCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

@Service
public class CampaignCommandService {

  @Autowired
  private CampaignCommandRepository campaignCommandRepository;

  @Autowired
  private CouchbaseTemplate couchbaseTemplate;

  @Value("${campaign.counter.key:campaign_counter}")
  private String campaignCounterKey;

  @Autowired
  private CampaignCommandEventService campaignCommandEventService;

  /**
   * Create a new campaign and publish an event to the kafka event bus.
   * @param campaign the campaign to create
   * @return the ID of the created campaign
   */
  public String createCampaign(Campaign campaign) {
      // Use DBUtils to get a unique sequential ID
      long counter = DBUtils.getUniqueCounter(couchbaseTemplate, campaignCounterKey);
      String id = "campaign::" + counter;
      campaign.setId(id);
      Campaign saved = campaignCommandRepository.save(campaign);

      // Publish the campaign created event to kafka event bus
      campaignCommandEventService.publishCreateCampaignEvent(saved);
      return saved.getId();
    }


  /**
   * Delete a campaign by its ID and publish an event to the kafka event bus.
   * @param id the ID of the campaign to delete
   */
  public void deleteCampaign(String id) {
    campaignCommandRepository.deleteById(id);

    // Publish the campaign created event to kafka event bus
    campaignCommandEventService.publishDeleteCampaignEvent(id);
  }
}
