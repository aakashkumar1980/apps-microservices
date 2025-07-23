package com.example.tutorial.common.dto.merchant.events;

import com.example.tutorial.common.dto.Event;
import com.example.tutorial.common.dto.KafkaEventType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantEvent extends Event {

  @JsonProperty("merchant_id")
  private String merchantId;

  @JsonProperty("merchant_code")
  private String merchantCode;

  public MerchantEvent() {}
  public MerchantEvent(String merchantId, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.merchantId = merchantId;
  }
  public MerchantEvent(String merchantId, String merchantCode, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.merchantId = merchantId;
    this.merchantCode = merchantCode;
  }

  // Getters and Setters
  public String getMerchantId() {
    return merchantId;
  }
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  public String getMerchantCode() {
    return merchantCode;
  }
  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  @Override
  public String toString() {
    return "CampaignEvent{" +
        "merchantId='" + merchantId + '\'' +
        ", merchantCode='" + merchantCode + '\'' +
        '}';
  }
}

