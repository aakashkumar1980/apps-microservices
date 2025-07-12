package com.example.tutorial.common.dto.offer;

import com.example.tutorial.common.dto.RewardType;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.couchbase.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Offer {

  @JsonProperty("campaign_id")
  @Field("campaign_id")
  @NotBlank(message = "Campaign ID is required")
  private String campaignId;

  @JsonProperty("merchant_id")
  @Field("merchant_id")
  @NotBlank(message = "Merchant ID is required")
  private String merchantId;

  @JsonProperty("reward_type")
  @Field("reward_type")
  @NotNull(message = "Reward type is required")
  private RewardType rewardType;

  @JsonProperty("reward_value")
  @Field("reward_value")
  @DecimalMin(value = "0.01", message = "Reward value must be greater than 0")
  private BigDecimal rewardValue;

  @JsonProperty("eligibility_criteria")
  @Field("eligibility_criteria")
  @NotBlank(message = "Eligibility criteria is required")
  private String eligibilityCriteria;

  @JsonProperty("offer_status")
  @Field("offer_status")
  @NotNull(message = "Offer status is required")
  private OfferStatus offerStatus;

  @JsonProperty("valid_from")
  @Field("valid_from")
  private LocalDateTime validFrom;

  @JsonProperty("valid_to")
  @Field("valid_to")
  private LocalDateTime validTo;

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
  public LocalDateTime getValidFrom() { return validFrom; }
  public void setValidFrom(LocalDateTime validFrom) { this.validFrom = validFrom; }
  public LocalDateTime getValidTo() { return validTo; }
  public void setValidTo(LocalDateTime validTo) { this.validTo = validTo; }

  @Override
  public String toString() {
    return "Offer{" +
        ", campaignId='" + campaignId + '\'' +
        ", merchantId='" + merchantId + '\'' +
        ", rewardType=" + rewardType +
        ", rewardValue=" + rewardValue +
        ", eligibilityCriteria='" + eligibilityCriteria + '\'' +
        ", offerStatus=" + offerStatus +
        ", validFrom=" + validFrom +
        ", validTo=" + validTo +
        '}';
  }
}