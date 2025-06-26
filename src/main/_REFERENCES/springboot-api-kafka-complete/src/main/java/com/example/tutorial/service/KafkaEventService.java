package com.example.tutorial.service;

public interface KafkaEventService {
    void publishOfferCreatedEvent(Object event);
}