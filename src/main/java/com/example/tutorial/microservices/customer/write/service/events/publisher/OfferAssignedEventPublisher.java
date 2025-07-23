package com.example.tutorial.microservices.customer.write.service.events.publisher;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.dto.customer.Customer;
import com.example.tutorial.common.dto.customer.events.OfferAssignedEvent;
import com.example.tutorial.common.utils.KafkaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for publishing campaign command events to Kafka.
 */
@Service
public class OfferAssignedEventPublisher {

  private static final Logger log = LoggerFactory.getLogger(OfferAssignedEventPublisher.class);

  @Autowired
  private KafkaUtils kafkaUtils;

  /**
   * Publishes an OfferAssignedEvent to Kafka. This is used by the "Recommendation Engine"
   * for offer personalization which includes ranking, filtering, or tailoring offers based on
   * customer data such as purchase history, browsing behavior, preferences, or demographics.
   * for example,
   * A customer frequently buys electronics and rarely shops for clothing. The recommendation engine,
   * upon receiving a list of eligible offers, prioritizes or highlights electronics-related offers for
   * this customer, while deprioritizing or omitting clothing offers. This increases the chance the
   * customer will engage with the offer.
   *
   * @param offerId The ID of the offer being assigned.
   * @param eligibleCustomers The list of eligible customers for the offer.
   */
  public void publishOfferAssignedEvent(String offerId, List<BaseDto<Customer>> eligibleCustomers) {
    OfferAssignedEvent offerAssignedEvent = new OfferAssignedEvent(
        offerId,
        eligibleCustomers.stream().map(BaseDto::getId).toList(),
        java.time.LocalDateTime.now(),
        KafkaEventType.CUSTOMER_OFFER_ASSIGNED
    );

    log.info("Publishing OfferAssignedEvent: {}", offerAssignedEvent);
    kafkaUtils.publishEvent(offerAssignedEvent.getKafkaEventType().name(), offerAssignedEvent.getOfferId(), offerAssignedEvent);
  }

}
