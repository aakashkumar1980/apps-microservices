package com.example.tutorial.common.dto.campaign.events;

import com.example.tutorial.common.dto.Event;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.dto.campaign.CampaignStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignEvent extends Event {
  private String id;
  private CampaignStatus status;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public CampaignEvent() {}
  public CampaignEvent(String id, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.id = id;
  }
  public CampaignEvent(String id, CampaignStatus status, LocalDateTime startDate, LocalDateTime endDate, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.id = id;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  // Getters and Setters
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
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
        "id='" + id + '\'' +
        ", status='" + status + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        '}';
  }
}

