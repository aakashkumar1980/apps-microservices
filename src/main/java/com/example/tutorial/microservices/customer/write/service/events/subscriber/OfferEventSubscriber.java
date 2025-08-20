package com.example.tutorial.microservices.customer.write.service.events.subscriber;

import com.example.tutorial.common.constants.CacheConstants;
import com.example.tutorial.common.datamodel.offer.events.OfferEvent;
import com.example.tutorial.common.exceptions.ApplicationTechnicalException;
import com.example.tutorial.common.utils.CacheUtils;
import com.example.tutorial.microservices.customer.ApplicationConstants;
import com.example.tutorial.microservices.customer.write.service.CustomerCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service("CustomerOfferEventSubscriber") // Ensure the service name is unique to avoid conflicts with other subscribers
public class OfferEventSubscriber {

  private static final Logger log = LoggerFactory.getLogger(OfferEventSubscriber.class);

  @Autowired
  private CustomerCommandService customerCommandService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CacheUtils cacheUtils;

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

      /** CACHE DATA **/
      // cache the offer details in Redis
      cacheUtils.setCache(offerId, offerEvent, CacheConstants.APPLICATION_CACHE_LIMIT_HOUR);

      /** BUSNESS LOGIC **/
      // check if the customer is eligible for the offer. If eligible, assign the offer to the customer.
      customerCommandService.assignOfferToCustomer(offerId);

    } catch (JsonProcessingException e) {
      throw new ApplicationTechnicalException("Error parsing object's value", e);
    }
  }

  /**
   * This method listens to the OFFER_CANCELLED topic and processes the OfferCancelled event.
   * It clears the cached offer details and unassigns the offer from the customers.
   *
   * @param payload The JSON payload of the OfferCancelled event.
   */
  @KafkaListener(topics = "OFFER_CANCELLED", groupId = ApplicationConstants.APPLICATION_NAME)
  public void subscribeCancelOfferEvent(String payload) {
    log.info("Received OfferCancelled event: {}", payload);

    OfferEvent offerEvent = null;
    try {
      offerEvent = objectMapper.readValue(payload, OfferEvent.class);
      String offerId = offerEvent.getId();

      /** CLEAR CACHE DATA **/
      // remove the cached offer details from Redis
      cacheUtils.delete(offerId);

      /** BUSINESS LOGIC **/
      // unassign the offer from the customers
      customerCommandService.unassignOfferFromCustomer(offerId);

    } catch (JsonProcessingException e) {
      throw new ApplicationTechnicalException("Error parsing object's value", e);
    }
  }
}
