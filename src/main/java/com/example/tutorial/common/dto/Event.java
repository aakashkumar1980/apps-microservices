package com.example.tutorial.common.dto;

/**
 * Represents a base event in the system.
 * This class serves as a base for all events that can be published to Kafka.
 */
public abstract class Event {

  private KafkaEventType kafkaEventType;

  public Event() {}
  public Event(KafkaEventType kafkaEventType) {
    this.kafkaEventType = kafkaEventType;
  }

  public KafkaEventType getKafkaEventType() {
    return kafkaEventType;
  }
  public void setKafkaEventType(KafkaEventType kafkaEventType) {
    this.kafkaEventType = kafkaEventType;
  }
}
