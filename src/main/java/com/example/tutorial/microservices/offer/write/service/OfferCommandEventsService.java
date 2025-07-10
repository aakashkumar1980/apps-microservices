package com.example.tutorial.microservices.offer.write.service;

import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.common.utils.KafkaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferCommandEventsService {

  @Autowired
  private KafkaUtils kafkaUtils;

  private static final String OFFER_EVENTS_TOPIC = "offer-events";

  public void publishOfferCreatedEvent(Offer saved) {
    OfferCreatedEvent event = new OfferCreatedEvent();
    kafkaUtils.publishEvent(OFFER_EVENTS_TOPIC, event);
  }
}
