package com.example.tutorial.microservices.offer.write.service.events.publisher;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.common.dto.offer.events.OfferEvent;
import com.example.tutorial.common.utils.KafkaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferEventPublisher {

  private static final Logger log = LoggerFactory.getLogger(OfferEventPublisher.class);

  @Autowired
  private KafkaUtils kafkaUtils;

  /**
   * Publishes a offer creation event to Kafka.
   * @param offer the BaseDto containing the offer data
   */
  public void publishCreateOfferEvent(BaseDto<Offer> offer) {
    OfferEvent offerEvent = new OfferEvent(
        offer.getId(),
        offer.getData().getCampaignId(),
        offer.getData().getMerchantId(),
        offer.getData().getDiscountAmount(),
        offer.getData().getSegmentCriteria(),
        KafkaEventType.OFFER_CREATED
    );

    log.info("Publishing offer creation event: {}", offerEvent);
    kafkaUtils.publishEvent(offerEvent.getKafkaEventType().name(), offerEvent.getId(), offerEvent);
  }

}
