package com.example.tutorial.common.dto.customer.events;

import com.example.tutorial.common.dto.Event;
import com.example.tutorial.common.dto.KafkaEventType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferAssignedEvent extends Event {

  @JsonProperty("customer_id")
  private String customerId;

  @JsonProperty("offer_id")
  private String offerId;

  @JsonProperty("assigned_at")
  private LocalDateTime assignedAt;

  public OfferAssignedEvent() {}
  public OfferAssignedEvent(String customerId, String offerId, LocalDateTime assignedAt, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.customerId = customerId;
    this.offerId = offerId;
    this.assignedAt = assignedAt;

  }

  // Getters and Setters
  public String getCustomerId() {
    return customerId;
  }
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getOfferId() {
    return offerId;
  }
  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public LocalDateTime getAssignedAt() {
    return assignedAt;
  }
  public void setAssignedAt(LocalDateTime assignedAt) {
    this.assignedAt = assignedAt;
  }

  @Override
  public String toString() {
    return "OfferAssignedEvent{" +
        "customerId='" + customerId + '\'' +
        ", offerId='" + offerId + '\'' +
        ", assignedAt=" + assignedAt +
        '}';
  }
}
