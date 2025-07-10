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
public class Offer {


    @Id
    @JsonProperty("offer_id")
    @Field("offer_id")
    private String offerId;
    

    @NotBlank
    @JsonProperty("campaign_id")
    @Field("campaign_id")
    private String campaignId;
    

    @NotBlank
    @JsonProperty("merchant_id")
    @Field("merchant_id")
    private String merchantId;
    

    @NotNull
    @JsonProperty("reward_type")
    @Field("reward_type")
    private RewardType rewardType;
    

    @DecimalMin(value = "0.01")
    @JsonProperty("reward_value")
    @Field("reward_value")
    private BigDecimal rewardValue;
    

    @NotBlank
    @JsonProperty("eligibility_criteria")
    @Field("eligibility_criteria")
    private String eligibilityCriteria;
    

    @NotNull
    @JsonProperty("offer_status")
    @Field("offer_status")
    private OfferStatus offerStatus;
    

    @NotNull
    @JsonProperty("created_at")
    @Field("created_at")
    private LocalDateTime createdAt;
    

    
    @JsonProperty("valid_from")
    @Field("valid_from")
    private LocalDateTime validFrom;
    

    
    @JsonProperty("valid_to")
    @Field("valid_to")
    private LocalDateTime validTo;
    

    public String getOfferId() { return offerId; }
    public void setOfferId(String offerId) { this.offerId = offerId; }
    public String getCampaignId() { return campaignId; }
    public void setCampaignId(String campaignId) { this.campaignId = campaignId; }
    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    public RewardType getRewardType() { return rewardType; }
    public void setRewardType(RewardType rewardType) { this.rewardType = rewardType; }
    public BigDecimal getRewardValue() { return rewardValue; }
    public void setRewardValue(BigDecimal rewardValue) { this.rewardValue = rewardValue; }
    public String getEligibilityCriteria() { return eligibilityCriteria; }
    public void setEligibilityCriteria(String eligibilityCriteria) { this.eligibilityCriteria = eligibilityCriteria; }
    public OfferStatus getOfferStatus() { return offerStatus; }
    public void setOfferStatus(OfferStatus offerStatus) { this.offerStatus = offerStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getValidFrom() { return validFrom; }
    public void setValidFrom(LocalDateTime validFrom) { this.validFrom = validFrom; }
    public LocalDateTime getValidTo() { return validTo; }
    public void setValidTo(LocalDateTime validTo) { this.validTo = validTo; }

    @Override
    public String toString() {
        return "Offer{"  + ", ".join(["offerId=" + offerId, "campaignId=" + campaignId, "merchantId=" + merchantId, "rewardType=" + rewardType, "rewardValue=" + rewardValue, "eligibilityCriteria=" + eligibilityCriteria, "offerStatus=" + offerStatus, "createdAt=" + createdAt, "validFrom=" + validFrom, "validTo=" + validTo]) + "}";
    }
}