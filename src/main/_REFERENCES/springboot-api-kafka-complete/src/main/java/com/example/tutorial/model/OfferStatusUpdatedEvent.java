package com.example.tutorial.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class OfferStatusUpdatedEvent {
    private UUID offerId;
    private String oldStatus;
    private String newStatus;
    private LocalDateTime updatedAt;

    public OfferStatusUpdatedEvent() {}

    public OfferStatusUpdatedEvent(UUID offerId, String oldStatus, String newStatus, LocalDateTime updatedAt) {
        this.offerId = offerId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.updatedAt = updatedAt;
    }

    public UUID getOfferId() {
        return offerId;
    }

    public void setOfferId(UUID offerId) {
        this.offerId = offerId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "OfferStatusUpdatedEvent{" +
                "offerId=" + offerId +
                ", oldStatus='" + oldStatus + ''' +
                ", newStatus='" + newStatus + ''' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}