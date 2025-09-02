package com.example.tutorial.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class OfferEnrollment {

    private UUID enrollmentId;
    private UUID customerId;
    private UUID offerId;
    private LocalDateTime enrolledAt;


    public UUID getEnrollmentid() {
        return enrollmentId;
    }

    public void setEnrollmentid(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
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

    public LocalDateTime getEnrolledat() {
        return enrolledAt;
    }

    public void setEnrolledat(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    @Override
    public String toString() {
        return "OfferEnrollment{" + 
               " + ", ".join(['enrollmentId=" + enrollmentId +', 'customerId=" + customerId +', 'offerId=" + offerId +', 'enrolledAt=" + enrolledAt +']) + "}";
    }
}
