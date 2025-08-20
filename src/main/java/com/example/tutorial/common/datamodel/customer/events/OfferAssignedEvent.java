package com.example.tutorial.common.datamodel.customer.events;

import com.example.tutorial.common.datamodel.Event;
import com.example.tutorial.common.datamodel.KafkaEventType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferAssignedEvent extends Event {

  @JsonProperty("offer_id")
  private String offerId;

  @JsonProperty("customer_id")
  private List<String> customerIds;

  @JsonProperty("assigned_at")
  private LocalDateTime assignedAt;

  public OfferAssignedEvent() {}
  public OfferAssignedEvent(String offerId, List<String> customerIds, LocalDateTime assignedAt, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.offerId = offerId;
    this.customerIds = customerIds;
    this.assignedAt = assignedAt;
  }

  // Getters and Setters
  public String getOfferId() {
    return offerId;
  }
  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public List<String> getCustomerIds() {
    return customerIds;
  }
  public void setCustomerIds(List<String> customerIds) {
    this.customerIds = customerIds;
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
        "customerIds=" + customerIds +
        ", offerId='" + offerId + '\'' +
        ", assignedAt=" + assignedAt +
        ", kafkaEventType=" + getKafkaEventType() +
        '}';
  }
}
