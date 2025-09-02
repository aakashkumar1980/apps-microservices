package com.example.tutorial.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class OfferCreatedEvent {
    private UUID offerId;
    private UUID merchantId;
    private String description;
    private LocalDateTime timestamp;

    public OfferCreatedEvent() {}

    public OfferCreatedEvent(UUID offerId, UUID merchantId, String description, LocalDateTime timestamp) {
        this.offerId = offerId;
        this.merchantId = merchantId;
        this.description = description;
        this.timestamp = timestamp;
    }

    public UUID getOfferId() {
        return offerId;
    }

    public void setOfferId(UUID offerId) {
        this.offerId = offerId;
    }

    public UUID getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "OfferCreatedEvent{" +
                "offerId=" + offerId +
                ", merchantId=" + merchantId +
                ", description='" + description + ''' +
                ", timestamp=" + timestamp +
                '}';
    }
}