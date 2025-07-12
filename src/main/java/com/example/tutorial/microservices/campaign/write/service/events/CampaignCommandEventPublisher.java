package com.example.tutorial.microservices.campaign.write.service.events;

import com.example.tutorial.common.dto.BaseDto;
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
public class CampaignCommandEventPublisher {

  @Autowired
  private KafkaUtils kafkaUtils;

  /**
   * Publishes a campaign creation event to Kafka.
   * @param baseDto the BaseDto containing the campaign data
   */
  public void publishCreateCampaignEvent(BaseDto<Campaign> baseDto) {
    CampaignEvent campaignEvent = new CampaignEvent(
        baseDto.getId(),
        baseDto.getData().getStatus().name(),
        baseDto.getData().getStartDate(),
        baseDto.getData().getEndDate(),
        KafkaEventType.CAMPAIGN_CREATED
    );
    kafkaUtils.publishEvent(campaignEvent.getKafkaEventType().name(), campaignEvent.getId(), campaignEvent);
  }

  /**
   * Publishes a campaign update event to Kafka.
   * @param baseDto the BaseDto containing the updated campaign data
   */
  public void publishUpdateCampaignEvent(BaseDto<Campaign> baseDto) {
    CampaignEvent campaignEvent = new CampaignEvent(
        baseDto.getId(),
        baseDto.getData().getStatus().name(),
        baseDto.getData().getStartDate(),
        baseDto.getData().getEndDate(),
        KafkaEventType.CAMPAIGN_UPDATED
    );
    kafkaUtils.publishEvent(campaignEvent.getKafkaEventType().name(), campaignEvent.getId(), campaignEvent);
  }

  /**
   * Publishes a campaign delete event to Kafka.
   * @param id the ID of the campaign that was deleted
   */
  public void publishDeleteCampaignEvent(String id) {
    CampaignEvent campaignEvent = new CampaignEvent(
        id,
        KafkaEventType.CAMPAIGN_DELETED
    );
    kafkaUtils.publishEvent(campaignEvent.getKafkaEventType().name(), campaignEvent.getId(), campaignEvent);
  }
}
