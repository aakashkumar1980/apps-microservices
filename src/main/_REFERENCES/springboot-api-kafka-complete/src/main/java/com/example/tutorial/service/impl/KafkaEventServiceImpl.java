package com.example.tutorial.service.impl;

import com.example.tutorial.service.KafkaEventService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventServiceImpl implements KafkaEventService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaEventServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishOfferCreatedEvent(Object event) {
        kafkaTemplate.send("offer-created-topic", event);
    }
}