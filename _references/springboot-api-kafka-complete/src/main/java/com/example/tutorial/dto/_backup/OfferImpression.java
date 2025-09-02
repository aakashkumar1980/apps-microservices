package com.example.tutorial.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class OfferImpression {

    private UUID impressionId;
    private UUID customerId;
    private UUID offerId;
    private LocalDateTime timestamp;
    private String adPlatform;


    public UUID getImpressionid() {
        return impressionId;
    }

    public void setImpressionid(UUID impressionId) {
        this.impressionId = impressionId;
    }

    public UUID getCustomerid() {
        return customerId;
    }

    public void setCustomerid(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getOfferid() {
        return offerId;
    }

    public void setOfferid(UUID offerId) {
        this.offerId = offerId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAdplatform() {
        return adPlatform;
    }

    public void setAdplatform(String adPlatform) {
        this.adPlatform = adPlatform;
    }

    @Override
    public String toString() {
        return "OfferImpression{" + 
               " + ", ".join(['impressionId=" + impressionId +', 'customerId=" + customerId +', 'offerId=" + offerId +', 'timestamp=" + timestamp +', 'adPlatform=" + adPlatform +']) + "}";
    }
}
