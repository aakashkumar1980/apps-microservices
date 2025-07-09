package com.example.tutorial.common.dto.offer;

import com.example.tutorial.common.dto.RewardType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class Offer {

    @Id
    @JsonProperty("offer_id")
    private UUID offerId;

    @JsonProperty("campaign_id")
    private UUID campaignId;

    @JsonProperty("merchant_id")
    private UUID merchantId;

    @JsonProperty("reward_type")
    @NotNull(message = "Reward type is required")
    private RewardType rewardType;

    @JsonProperty("reward_value")
    @NotNull(message = "Reward value is required")
    @DecimalMin(value = "0.01", message = "Reward value must be greater than 0")
    private BigDecimal rewardValue;

    @JsonProperty("max_redemptions")
    private Integer maxRedemptions;

    @JsonProperty("eligibility_criteria")
    private String eligibilityCriteria;

    @JsonProperty("offer_status")
    private OfferStatus offerStatus;

    @JsonProperty("created_at")
    @NotNull(message = "Created at is required")
    private LocalDateTime createdAt;

    public Offer() {
        // id will be set by the service or generated
    }

    // Getters and Setters
    public UUID getOfferId() {
        return offerId;
    }

    public void setOfferId(UUID offerId) {
        this.offerId = offerId;
    }

    public UUID getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(UUID campaignId) {
        this.campaignId = campaignId;
    }

    public UUID getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public BigDecimal getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(BigDecimal rewardValue) {
        this.rewardValue = rewardValue;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getMaxRedemptions() {
        return maxRedemptions;
    }

    public void setMaxRedemptions(Integer maxRedemptions) {
        this.maxRedemptions = maxRedemptions;
    }

    @Override
    public String toString() {
        return "Offer{" +
               "offerId=" + offerId +
               ", campaignId=" + campaignId +
               ", merchantId=" + merchantId +
               ", rewardType=" + rewardType +
               ", rewardValue=" + rewardValue +
               ", eligibilityCriteria='" + eligibilityCriteria + '\'' +
               ", offerStatus=" + offerStatus +
               ", createdAt=" + createdAt +
               ", maxRedemptions=" + maxRedemptions +
               '}';
    }
}
