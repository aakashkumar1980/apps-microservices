package com.example.tutorial.common.dto;

import com.example.tutorial.common.constants.KafkaEventType;

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
