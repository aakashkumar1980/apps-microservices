package com.example.tutorial.microservices.offer.write.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CancelOfferRequest {

  @JsonProperty("cancellation_reason")
  private String cancellationReason;
  @JsonProperty("cancelled_by")
  private String cancelledBy;

  public String getCancellationReason() {
      return cancellationReason;
  }
  public void setCancellationReason(String cancellationReason) {
      this.cancellationReason = cancellationReason;
  }

  public String getCancelledBy() {
      return cancelledBy;
  }
  public void setCancelledBy(String cancelledBy) {
    this.cancelledBy = cancelledBy;
  }

  @Override
  public String toString() {
    return "CancelOfferRequest{" +
            "cancellationReason='" + cancellationReason + '\'' +
            ", cancelledBy='" + cancelledBy + '\'' +
            '}';
  }
}
