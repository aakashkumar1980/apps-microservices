package com.example.tutorial.common.datamodel.merchant.events;

import com.example.tutorial.common.datamodel.Event;
import com.example.tutorial.common.datamodel.KafkaEventType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantEvent extends Event {

  @JsonProperty("id")
  private String id;

  @JsonProperty("code")
  private String code;

  public MerchantEvent() {}
  public MerchantEvent(String id, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.id = id;
  }
  public MerchantEvent(String id, String code, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.id = id;
    this.code = code;
  }

  // Getters and Setters
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return "CampaignEvent{" +
        "id='" + id + '\'' +
        ", code='" + code + '\'' +
        '}';
  }
}

