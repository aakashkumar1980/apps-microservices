package com.example.dto;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document
public class Reward {


    @Id
    @JsonProperty("reward_id")
    @Field("reward_id")
    private String rewardId;
    

    
    @JsonProperty("redemption_id")
    @Field("redemption_id")
    private String redemptionId;
    

    
    @JsonProperty("customer_id")
    @Field("customer_id")
    private String customerId;
    

    @NotNull
    @JsonProperty("reward_type")
    @Field("reward_type")
    private RewardType rewardType;
    

    
    @JsonProperty("reward_value")
    @Field("reward_value")
    private BigDecimal rewardValue;
    

    @NotNull
    @JsonProperty("reward_status")
    @Field("reward_status")
    private RewardStatus rewardStatus;
    

    
    @JsonProperty("processed_at")
    @Field("processed_at")
    private LocalDateTime processedAt;
    

    public String getRewardId() { return rewardId; }
    public void setRewardId(String rewardId) { this.rewardId = rewardId; }
    public String getRedemptionId() { return redemptionId; }
    public void setRedemptionId(String redemptionId) { this.redemptionId = redemptionId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public RewardType getRewardType() { return rewardType; }
    public void setRewardType(RewardType rewardType) { this.rewardType = rewardType; }
    public BigDecimal getRewardValue() { return rewardValue; }
    public void setRewardValue(BigDecimal rewardValue) { this.rewardValue = rewardValue; }
    public RewardStatus getRewardStatus() { return rewardStatus; }
    public void setRewardStatus(RewardStatus rewardStatus) { this.rewardStatus = rewardStatus; }
    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }

    @Override
    public String toString() {
        return "Reward{"  + ", ".join(["rewardId=" + rewardId, "redemptionId=" + redemptionId, "customerId=" + customerId, "rewardType=" + rewardType, "rewardValue=" + rewardValue, "rewardStatus=" + rewardStatus, "processedAt=" + processedAt]) + "}";
    }
}