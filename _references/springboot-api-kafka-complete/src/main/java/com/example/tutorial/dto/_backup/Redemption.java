package com.example.tutorial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Redemption {

    private UUID redemptionId;
    private UUID customerId;
    private UUID offerId;
    private UUID transactionId;
    private BigDecimal amountSpent;
    private BigDecimal rewardApplied;
    private LocalDateTime timestamp;


    public UUID getRedemptionid() {
        return redemptionId;
    }

    public void setRedemptionid(UUID redemptionId) {
        this.redemptionId = redemptionId;
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

    public UUID getTransactionid() {
        return transactionId;
    }

    public void setTransactionid(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmountspent() {
        return amountSpent;
    }

    public void setAmountspent(BigDecimal amountSpent) {
        this.amountSpent = amountSpent;
    }

    public BigDecimal getRewardapplied() {
        return rewardApplied;
    }

    public void setRewardapplied(BigDecimal rewardApplied) {
        this.rewardApplied = rewardApplied;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Redemption{" + 
               " + ", ".join(['redemptionId=" + redemptionId +', 'customerId=" + customerId +', 'offerId=" + offerId +', 'transactionId=" + transactionId +', 'amountSpent=" + amountSpent +', 'rewardApplied=" + rewardApplied +', 'timestamp=" + timestamp +']) + "}";
    }
}
