package com.example.tutorial.common.dto.customer.events;

import com.example.tutorial.common.dto.Event;
import com.example.tutorial.common.dto.KafkaEventType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferUnassignedEvent extends Event {

  @JsonProperty("offer_id")
  private String offerId;

  @JsonProperty("customer_id")
  private List<String> customerIds;

  @JsonProperty("assigned_at")
  private LocalDateTime unassignedAt;

  public OfferUnassignedEvent() {}
  public OfferUnassignedEvent(String offerId, List<String> customerIds, LocalDateTime unassignedAt, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.offerId = offerId;
    this.customerIds = customerIds;
    this.unassignedAt = unassignedAt;
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

  public LocalDateTime getUnassignedAt() {
    return unassignedAt;
  }
  public void setUnassignedAt(LocalDateTime unassignedAt) {
    this.unassignedAt = unassignedAt;
  }

  @Override
  public String toString() {
    return "OfferAssignedEvent{" +
        "customerIds=" + customerIds +
        ", offerId='" + offerId + '\'' +
        ", unassignedAt=" + unassignedAt +
        ", kafkaEventType=" + getKafkaEventType() +
        '}';
  }
}
