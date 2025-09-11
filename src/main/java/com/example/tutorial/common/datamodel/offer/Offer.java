package com.example.tutorial.common.datamodel.offer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Offer {

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("campaign_id")
  private String campaignId;

  @JsonProperty("merchant_id")
  private String merchantId;

  @JsonProperty("type")
  private OfferType type;

  @JsonProperty("status")
  private OfferStatus status;

  @JsonProperty("cancellation_reason")
  private String cancellationReason;

  @JsonProperty("discount_amount")
  private BigDecimal discountAmount;

  @JsonProperty("segment_criteria")
  private Segment segmentCriteria;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("valid_from")
  private LocalDateTime validFrom;

  @JsonProperty("valid_to")
  private LocalDateTime validTo;

  @JsonProperty("max_redemptions")
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