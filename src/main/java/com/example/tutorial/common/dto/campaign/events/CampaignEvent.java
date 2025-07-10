package com.example.tutorial.common.dto.campaign.events;

import com.example.tutorial.common.dto.KafkaEventType;

import java.time.LocalDateTime;

public class CampaignEvent {
  private String id;
  private String status;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private KafkaEventType kafkaEventType;

  public CampaignEvent() {}
  public CampaignEvent(String id, KafkaEventType kafkaEventType) {
    this.id = id;
    this.kafkaEventType = kafkaEventType;
  }
  public CampaignEvent(String id, String status, LocalDateTime startDate, LocalDateTime endDate, KafkaEventType kafkaEventType) {
    this.id = id;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
    this.kafkaEventType = kafkaEventType;
  }

  // Getters and Setters
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
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

  public KafkaEventType getKafkaEventType() {
    return kafkaEventType;
  }
  public void setKafkaEventType(KafkaEventType kafkaEventType) {
    this.kafkaEventType = kafkaEventType;
  }

  @Override
  public String toString() {
    return "CampaignEvent{" +
        "id='" + id + '\'' +
        ", status='" + status + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", kafkaEventType=" + kafkaEventType +
        '}';
  }
}

