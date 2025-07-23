package com.example.tutorial.common.dto.campaign.events;

import com.example.tutorial.common.dto.Event;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.dto.campaign.CampaignStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignEvent extends Event {

  @JsonProperty("campaign_id")
  private String campaignId;

  @JsonProperty("status")
  private CampaignStatus status;

  @JsonProperty("start_date")
  private LocalDateTime startDate;

  @JsonProperty("end_date")
  private LocalDateTime endDate;

  public CampaignEvent() {}
  public CampaignEvent(String campaignId, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.campaignId = campaignId;
  }
  public CampaignEvent(String campaignId, CampaignStatus status, LocalDateTime startDate, LocalDateTime endDate, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.campaignId = campaignId;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  // Getters and Setters
  public String getCampaignId() {
    return campaignId;
  }
  public void setCampaignId(String campaignId) {
    this.campaignId = campaignId;
  }

  public CampaignStatus getStatus() {
    return status;
  }
  public void setStatus(CampaignStatus status) {
    this.status = status;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }
  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }
  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    return "CampaignEvent{" +
        "campaignId='" + campaignId + '\'' +
        ", status='" + status + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        '}';
  }
}

