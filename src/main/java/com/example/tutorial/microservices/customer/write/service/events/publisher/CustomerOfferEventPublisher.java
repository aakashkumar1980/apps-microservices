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
public class CustomerOfferEventPublisher {

  private static final Logger log = LoggerFactory.getLogger(CustomerOfferEventPublisher.class);

  @Autowired
  private KafkaUtils kafkaUtils;

  /**
   * <p>
   * Publishes an OfferAssignedEvent to Kafka. This is used by the "Recommendation Engine"
   * for offer personalization which includes ranking, filtering, or tailoring offers based on
   * customer data such as purchase history, browsing behavior, preferences, or demographics. for example,
   * </p>
   * <p>
   * A customer frequently buys electronics and rarely shops for clothing. The recommendation engine,
   * upon receiving a list of eligible offers, prioritizes or highlights electronics-related offers for
   * this customer, while deprioritizing or omitting clothing offers. This increases the chance the
   * customer will engage with the offer.
   * </p>
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

  /**
   * Publishes an OfferUnassignedEvent to Kafka. This is used when a customer is no longer eligible
   * for an offer, or the offer has been removed. This is used by the "Recommendation Engine" to update
   * its recommendations.
   *
   * @param offerId The ID of the offer being unassigned.
   * @param unassignedCustomers The list of customers who are no longer eligible for the offer.
   */
  public void publishOfferUnassignedEvent(String offerId, List<BaseDto<Customer>> unassignedCustomers) {
    OfferAssignedEvent offerAssignedEvent = new OfferAssignedEvent(
        offerId,
        unassignedCustomers.stream().map(BaseDto::getId).toList(),
        java.time.LocalDateTime.now(),
        KafkaEventType.CUSTOMER_OFFER_UNASSIGNED
    );

    log.info("Publishing OfferUnassignedEvent: {}", offerAssignedEvent);
    kafkaUtils.publishEvent(offerAssignedEvent.getKafkaEventType().name(), offerAssignedEvent.getOfferId(), offerAssignedEvent);
  }
}
