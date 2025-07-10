package com.example.tutorial.microservices.campaign.write.service;

import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.dto.campaign.events.CampaignEvent;
import com.example.tutorial.common.utils.KafkaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for publishing campaign command events to Kafka.
 */
@Service
public class CampaignCommandEventService {

  @Autowired
  private KafkaUtils kafkaUtils;

  /**
   * Publishes a campaign creation event to Kafka.
   * @param campaign the campaign that was created
   */
  public void publishCreateCampaignEvent(Campaign campaign) {
    CampaignEvent event = new CampaignEvent(
        campaign.getId(),
        campaign.getStatus().name(),
        campaign.getStartDate(),
        campaign.getEndDate(),
        KafkaEventType.CAMPAIGN_CREATED
    );
    kafkaUtils.publishEvent(event.getKafkaEventType().name(), event.getId(), event);
  }

  /**
   * TODO: visit the actual implementation later
   * Publishes a campaign update event to Kafka.
   * @param campaign the campaign that was updated
   */
  public void publishUpdateCampaignEvent(Campaign campaign) {
    CampaignEvent event = new CampaignEvent(
        campaign.getId(),
        campaign.getStatus().name(),
        campaign.getStartDate(),
        campaign.getEndDate(),
        KafkaEventType.CAMPAIGN_UPDATED
    );
    kafkaUtils.publishEvent(event.getKafkaEventType().name(), event.getId(), event);
  }

  /**
   * Publishes a campaign delete event to Kafka.
   * @param id the ID of the campaign that was deleted
   */
  public void publishDeleteCampaignEvent(String id) {
    CampaignEvent event = new CampaignEvent(
        id,
        KafkaEventType.CAMPAIGN_DELETED
    );
    kafkaUtils.publishEvent(event.getKafkaEventType().name(), event.getId(), event);
  }
}
