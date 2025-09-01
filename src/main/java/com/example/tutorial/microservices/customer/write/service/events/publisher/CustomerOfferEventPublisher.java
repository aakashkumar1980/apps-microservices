package com.example.tutorial.microservices.customer.write.service.events.publisher;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.KafkaEventType;
import com.example.tutorial.common.datamodel.customer.Customer;
import com.example.tutorial.common.datamodel.customer.events.OfferEnrollmentEvent;
import com.example.tutorial.common.datamodel.customer.events.OfferDisenrollmentEvent;
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
   * Publishes an OfferEnrollmentEvent to Kafka. This is used by the "Recommendation Engine"
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
  public void publishOfferEnrollmentEvent(String offerId, List<BaseDto<Customer>> eligibleCustomers) {
    OfferEnrollmentEvent offerEnrollmentEvent = new OfferEnrollmentEvent(
        offerId,
        eligibleCustomers.stream().map(BaseDto::getId).toList(),
        java.time.LocalDateTime.now(),
        KafkaEventType.CUSTOMER_OFFER_ENROLLED
    );

    log.info("Publishing OfferEnrollmentEvent: {}", offerEnrollmentEvent);
    kafkaUtils.publishEvent(offerEnrollmentEvent.getKafkaEventType().name(), offerEnrollmentEvent.getOfferId(), offerEnrollmentEvent);
  }

  /**
   * Publishes an OfferDisenrollmentEvent to Kafka. This is used when a customer is no longer eligible
   * for an offer, or the offer has been removed. This is used by the "Recommendation Engine" to update
   * its recommendations.
   *
   * @param offerId The ID of the offer being unassigned.
   * @param unassignedCustomers The list of customers who are no longer eligible for the offer.
   */
  public void publishOfferDisenrollmentEvent(String offerId, List<BaseDto<Customer>> unassignedCustomers) {
    OfferDisenrollmentEvent offerDisenrollmentEvent = new OfferDisenrollmentEvent(
        offerId,
        unassignedCustomers.stream().map(BaseDto::getId).toList(),
        java.time.LocalDateTime.now(),
        KafkaEventType.CUSTOMER_OFFER_DISENROLLED
    );

    log.info("Publishing OfferDisenrollmentEvent: {}", offerDisenrollmentEvent);
    kafkaUtils.publishEvent(offerDisenrollmentEvent.getKafkaEventType().name(), offerDisenrollmentEvent.getOfferId(), offerDisenrollmentEvent);
  }
}
