package com.example.tutorial.microservices.customer.write.service.events.publisher;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.dto.campaign.events.CampaignEvent;
import com.example.tutorial.common.utils.KafkaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for publishing campaign command events to Kafka.
 */
@Service
public class OfferAssignedEventPublisher {

  private static final Logger log = LoggerFactory.getLogger(OfferAssignedEventPublisher.class);

  @Autowired
  private KafkaUtils kafkaUtils;

  /**
   * Publishes a campaign creation event to Kafka.
   * @param campaign the BaseDto containing the campaign data
   */
  public void publishOfferAssignedEvent(BaseDto<Campaign> campaign) {
    CampaignEvent campaignEvent = new CampaignEvent(
        campaign.getId(),
        campaign.getData().getStatus(),
        campaign.getData().getStartDate(),
        campaign.getData().getEndDate(),
        KafkaEventType.CAMPAIGN_CREATED
    );

    log.info("Publishing campaign creation event: {}", campaignEvent);
    kafkaUtils.publishEvent(campaignEvent.getKafkaEventType().name(), campaignEvent.getCampaignId(), campaignEvent);
  }

  /**
   * Publishes a campaign update event to Kafka.
   * @param campaign the BaseDto containing the updated campaign data
   */
  public void publishUpdateCampaignEvent(BaseDto<Campaign> campaign) {
    CampaignEvent campaignEvent = new CampaignEvent(
        campaign.getId(),
        campaign.getData().getStatus(),
        campaign.getData().getStartDate(),
        campaign.getData().getEndDate(),
        KafkaEventType.CAMPAIGN_UPDATED
    );

    log.info("Publishing campaign update event: {}", campaignEvent);
    kafkaUtils.publishEvent(campaignEvent.getKafkaEventType().name(), campaignEvent.getCampaignId(), campaignEvent);
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

    log.info("Publishing campaign delete event: {}", campaignEvent);
    kafkaUtils.publishEvent(campaignEvent.getKafkaEventType().name(), campaignEvent.getCampaignId(), campaignEvent);
  }
}
