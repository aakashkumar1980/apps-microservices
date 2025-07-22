package com.example.tutorial.common.dto.offer.events;

import com.example.tutorial.common.dto.Event;
import com.example.tutorial.common.constants.KafkaEventType;
import com.example.tutorial.common.dto.offer.OfferType;
import com.example.tutorial.common.dto.offer.Segment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferEvent extends Event {

  @JsonProperty("id")
  private String id;

  @JsonProperty("merchant_id")
  private String merchantId;

  @JsonProperty("type")
  private OfferType type;

  @JsonProperty("discount_amount")
  private BigDecimal discountAmount;

  @JsonProperty("segment_criteria")
  private Segment segmentCriteria;

  public OfferEvent() {}
  public OfferEvent(String id, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.id = id;
  }
  public OfferEvent(String id, String merchantId, OfferType type, BigDecimal discountAmount, Segment segmentCriteria, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.id = id;
    this.merchantId = merchantId;
    this.type = type;
    this.discountAmount = discountAmount;
    this.segmentCriteria = segmentCriteria;
  }

  // Getters and Setters
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getMerchantId() {
    return merchantId;
  }
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public OfferType getType() {
    return type;
  }
  public void setType(OfferType type) {
    this.type = type;
  }

  public BigDecimal getDiscountAmount() {
    return discountAmount;
  }
  public void setDiscountAmount(BigDecimal discountAmount) {
    this.discountAmount = discountAmount;
  }

  public Segment getSegmentCriteria() {
    return segmentCriteria;
  }
  public void setSegmentCriteria(Segment segmentCriteria) {
    this.segmentCriteria = segmentCriteria;
  }

  @Override
  public String toString() {
    return "OfferEvent{" +
        "id='" + id + '\'' +
        ", merchantId='" + merchantId + '\'' +
        ", type='" + type + '\'' +
        ", discountAmount=" + discountAmount +
        ", segmentCriteria='" + segmentCriteria + '\'' +
        ", kafkaEventType=" + getKafkaEventType() +
        '}';
  }
}
