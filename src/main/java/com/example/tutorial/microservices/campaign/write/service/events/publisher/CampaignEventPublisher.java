package com.example.tutorial.microservices.campaign.write.service.events.publisher;

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
public class CampaignEventPublisher {

  private static final Logger log = LoggerFactory.getLogger(CampaignEventPublisher.class);

  @Autowired
  private KafkaUtils kafkaUtils;

  /**
   * Publishes a campaign creation event to Kafka.
   * @param campaign the BaseDto containing the campaign data
   */
  public void publishCreateCampaignEvent(BaseDto<Campaign> campaign) {
    CampaignEvent campaignEvent = new CampaignEvent(
        campaign.getId(),
        campaign.getData().getStatus(),
        campaign.getData().getStartDate(),
        campaign.getData().getEndDate(),
        campaign.getData().getBudget(),
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
        campaign.getData().getBudget(),
        KafkaEventType.CAMPAIGN_UPDATED
    );

    log.info("Publishing campaign update event: {}", campaignEvent);
    kafkaUtils.publishEvent(campaignEvent.getKafkaEventType().name(), campaignEvent.getCampaignId(), campaignEvent);
  }

  /**
   * Publishes a campaign cancellation event to Kafka.
   * @param id the ID of the campaign that was cancelled
   */
  public void publishCancelCampaignEvent(String id) {
    CampaignEvent campaignEvent = new CampaignEvent(
        id,
        KafkaEventType.CAMPAIGN_CANCELLED
    );

    log.info("Publishing campaign cancel event: {}", campaignEvent);
    kafkaUtils.publishEvent(campaignEvent.getKafkaEventType().name(), campaignEvent.getCampaignId(), campaignEvent);
  }
}
