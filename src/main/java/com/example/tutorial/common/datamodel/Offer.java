package com.example.tutorial.common.datamodel;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.List;

public class Offer {
  private String offerId;
  private String merchantId;
  private String merchantName;
  private String category;
  private String title;
  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant endDate;

  private DiscountType discountType;
  private double discountValue;
  private String currency;
  private double minSpend;
  private int maxRedemptions;
  private List<String> tags;
  private List<String> eligibleSegments;
  private OfferStatus status;
  private int impressions;
  private int clicks;
  private int redemptions;
  private int dailyTpsHint;

  // Getters & setters
  public String getOfferId() { return offerId; }
  public void setOfferId(String offerId) { this.offerId = offerId; }
  public String getMerchantId() { return merchantId; }
  public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
  public String getMerchantName() { return merchantName; }
  public void setMerchantName(String merchantName) { this.merchantName = merchantName; }
  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public DiscountType getDiscountType() { return discountType; }
  public void setDiscountType(DiscountType discountType) { this.discountType = discountType; }
  public double getDiscountValue() { return discountValue; }
  public void setDiscountValue(double discountValue) { this.discountValue = discountValue; }
  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }
  public double getMinSpend() { return minSpend; }
  public void setMinSpend(double minSpend) { this.minSpend = minSpend; }
  public int getMaxRedemptions() { return maxRedemptions; }
  public void setMaxRedemptions(int maxRedemptions) { this.maxRedemptions = maxRedemptions; }
  public List<String> getTags() { return tags; }
  public void setTags(List<String> tags) { this.tags = tags; }
  public List<String> getEligibleSegments() { return eligibleSegments; }
  public void setEligibleSegments(List<String> eligibleSegments) { this.eligibleSegments = eligibleSegments; }
  public OfferStatus getStatus() { return status; }
  public void setStatus(OfferStatus status) { this.status = status; }
  public Instant getStartDate() { return startDate; }
  public void setStartDate(Instant startDate) { this.startDate = startDate; }
  public Instant getEndDate() { return endDate; }
  public void setEndDate(Instant endDate) { this.endDate = endDate; }
  public long getImpressions() { return impressions; }
  public void setImpressions(int impressions) { this.impressions = impressions; }
  public int getClicks() { return clicks; }
  public void setClicks(int clicks) { this.clicks = clicks; }
  public int getRedemptions() { return redemptions; }
  public void setRedemptions(int redemptions) { this.redemptions = redemptions; }
  public int getDailyTpsHint() { return dailyTpsHint; }
  public void setDailyTpsHint(int dailyTpsHint) { this.dailyTpsHint = dailyTpsHint; }
}
