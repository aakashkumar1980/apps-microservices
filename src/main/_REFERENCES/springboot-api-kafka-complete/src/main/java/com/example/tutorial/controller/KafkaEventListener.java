package com.example.tutorial.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventListener {

    @KafkaListener(topics = "offer-created-topic", groupId = "springboot-group")
    public void consume(Object event) {
        System.out.println("Received Kafka Event: " + event);
    }
}