package com.example.tutorial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Reward {

    private UUID rewardId;
    private UUID redemptionId;
    private UUID customerId;
    private RewardType rewardType;
    private BigDecimal rewardValue;
    private RewardStatus rewardStatus;
    private LocalDateTime processedAt;


    public UUID getRewardid() {
        return rewardId;
    }

    public void setRewardid(UUID rewardId) {
        this.rewardId = rewardId;
    }

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

    public RewardType getRewardtype() {
        return rewardType;
    }

    public void setRewardtype(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public BigDecimal getRewardvalue() {
        return rewardValue;
    }

    public void setRewardvalue(BigDecimal rewardValue) {
        this.rewardValue = rewardValue;
    }

    public RewardStatus getRewardstatus() {
        return rewardStatus;
    }

    public void setRewardstatus(RewardStatus rewardStatus) {
        this.rewardStatus = rewardStatus;
    }

    public LocalDateTime getProcessedat() {
        return processedAt;
    }

    public void setProcessedat(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    @Override
    public String toString() {
        return "Reward{" + 
               " + ", ".join(['rewardId=" + rewardId +', 'redemptionId=" + redemptionId +', 'customerId=" + customerId +', 'rewardType=" + rewardType +', 'rewardValue=" + rewardValue +', 'rewardStatus=" + rewardStatus +', 'processedAt=" + processedAt +']) + "}";
    }
}
