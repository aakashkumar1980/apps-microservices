package com.example.tutorial.common.datamodel.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.springframework.data.couchbase.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Offer {

  @NotBlank
  @Size(min = 3, max = 100)
  @JsonProperty("name")
  @Field("name")
  private String name;

  @Size(max = 500)
  @JsonProperty("description")
  @Field("description")
  private String description;

  @NotBlank
  @JsonProperty("campaign_id")
  @Field("campaign_id")
  private String campaignId;

  @NotBlank
  @JsonProperty("merchant_id")
  @Field("merchant_id")
  private String merchantId;

  @NotNull
  @JsonProperty("type")
  @Field("type")
  private OfferType type;

  @NotNull
  @JsonProperty("status")
  @Field("status")
  private OfferStatus status;

  @JsonProperty("cancellation_reason")
  @Field("cancellation_reason")
  private String cancellationReason;

  @DecimalMin("0.0")
  @JsonProperty("discount_amount")
  @Field("discount_amount")
  private BigDecimal discountAmount;

  @NotNull
  @JsonProperty("segment_criteria")
  @Field("segment_criteria")
  private Segment segmentCriteria;

  @NotBlank
  @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be ISO 4217 format (e.g., USD, INR)")
  @JsonProperty("currency")
  @Field("currency")
  private String currency;

  @NotNull
  @FutureOrPresent
  @JsonProperty("valid_from")
  @Field("valid_from")
  private LocalDateTime validFrom;

  @NotNull
  @Future
  @JsonProperty("valid_to")
  @Field("valid_to")
  private LocalDateTime validTo;

  @Min(0)
  @JsonProperty("max_redemptions")
  @Field("max_redemptions")
  private Integer maxRedemptions;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public String getCampaignId() { return campaignId; }
  public void setCampaignId(String campaignId) { this.campaignId = campaignId; }

  public String getMerchantId() { return merchantId; }
  public void setMerchantId(String merchantId) { this.merchantId = merchantId; }

  public OfferType getType() { return type; }
  public void setType(OfferType type) { this.type = type; }

  public OfferStatus getStatus() { return status; }
  public void setStatus(OfferStatus status) { this.status = status; }

  public String getCancellationReason() { return cancellationReason; }
  public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }

  public BigDecimal getDiscountAmount() { return discountAmount; }
  public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }

  public Segment getSegmentCriteria() { return segmentCriteria; }
  public void setSegmentCriteria(Segment segmentCriteria) { this.segmentCriteria = segmentCriteria; }

  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }

  public LocalDateTime getValidFrom() { return validFrom; }
  public void setValidFrom(LocalDateTime validFrom) { this.validFrom = validFrom; }

  public LocalDateTime getValidTo() { return validTo; }
  public void setValidTo(LocalDateTime validTo) { this.validTo = validTo; }

  public Integer getMaxRedemptions() { return maxRedemptions; }
  public void setMaxRedemptions(Integer maxRedemptions) { this.maxRedemptions = maxRedemptions; }

  @Override
  public String toString() {
    return "Offer{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        "  campaignId='" + campaignId + '\'' +
        ", merchantId='" + merchantId + '\'' +
        ", type=" + type +
        ", status=" + status +
        ", cancellationReason='" + cancellationReason + '\'' +
        ", discountAmount=" + discountAmount +
        ", segmentCriteria=" + segmentCriteria +
        ", currency='" + currency + '\'' +
        ", validFrom=" + validFrom +
        ", validTo=" + validTo +
        ", maxRedemptions=" + maxRedemptions +
        '}';
  }
}