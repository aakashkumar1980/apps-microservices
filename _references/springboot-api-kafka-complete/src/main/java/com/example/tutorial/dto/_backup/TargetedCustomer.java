package com.example.tutorial.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class TargetedCustomer {

    private UUID customerId;
    private UUID offerId;
    private BigDecimal targetingScore;
    private Boolean eligibilityStatus;


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

    public BigDecimal getTargetingscore() {
        return targetingScore;
    }

    public void setTargetingscore(BigDecimal targetingScore) {
        this.targetingScore = targetingScore;
    }

    public Boolean getEligibilitystatus() {
        return eligibilityStatus;
    }

    public void setEligibilitystatus(Boolean eligibilityStatus) {
        this.eligibilityStatus = eligibilityStatus;
    }

    @Override
    public String toString() {
        return "TargetedCustomer{" + 
               " + ", ".join(['customerId=" + customerId +', 'offerId=" + offerId +', 'targetingScore=" + targetingScore +', 'eligibilityStatus=" + eligibilityStatus +']) + "}";
    }
}
