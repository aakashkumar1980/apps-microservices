package com.example.tutorial.microservices.customer.write.service.events.subscriber;

import com.example.tutorial.common.constants.CacheConstants;
import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.customer.Customer;
import com.example.tutorial.common.dto.offer.events.OfferEvent;
import com.example.tutorial.common.utils.CacheUtils;
import com.example.tutorial.microservices.customer.ApplicationConstants;
import com.example.tutorial.microservices.customer.write.service.CustomerCommandService;
import com.example.tutorial.microservices.customer.write.service.events.publisher.OfferAssignedEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CustomerOfferEventSubscriber") // Ensure the service name is unique to avoid conflicts with other subscribers
public class OfferEventSubscriber {

  private static final Logger log = LoggerFactory.getLogger(OfferEventSubscriber.class);

  @Autowired
  private CustomerCommandService customerCommandService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CacheUtils cacheUtils;

  @Autowired
  private OfferAssignedEventPublisher offerAssignedEventPublisher;

  /**
   * This method listens to the OFFER_CREATED topic and processes the OfferCreated event.
   * It caches the offer details and checks if customers are eligible for the offer.
   * If eligible, it assigns the offer to the customers and publishes an OfferAssigned event.
   *
   * @param payload The JSON payload of the OfferCreated event.
   */
  @KafkaListener(topics = "OFFER_CREATED", groupId = ApplicationConstants.APPLICATION_NAME)
  public void subscribeCreateOfferEvent(String payload) {
    log.info("Received OfferCreated event: {}", payload);

    OfferEvent offerEvent = null;
    try {
      offerEvent = objectMapper.readValue(payload, OfferEvent.class);
      String offerId = offerEvent.getId();

      // Cache the offer details in Redis
      cacheUtils.setCache(offerId, payload, CacheConstants.APPLICATION_CACHE_LIMIT_HOUR);

      // Check if the customer is eligible for the offer. If eligible, assign the offer to the customer.
      List<BaseDto<Customer>> eligibleCustomers = customerCommandService.assignOfferToCustomer(offerId);
      log.info("Assigned offer {} to {} customers successfully", offerId, eligibleCustomers.size());

      // Publish the offer assignment event
      if(CollectionUtils.isNotEmpty(eligibleCustomers)) {
        offerAssignedEventPublisher.publishOfferAssignedEvent(offerId, eligibleCustomers);
      }

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
