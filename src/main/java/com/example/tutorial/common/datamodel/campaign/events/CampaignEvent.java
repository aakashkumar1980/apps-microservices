package com.example.tutorial.common.datamodel.campaign.events;

import com.example.tutorial.common.datamodel.Event;
import com.example.tutorial.common.datamodel.KafkaEventType;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignEvent extends Event {

  @JsonProperty("id")
  private String id;

  @JsonProperty("status")
  private CampaignStatus status;

  @JsonProperty("start_date")
  private LocalDateTime startDate;

  @JsonProperty("end_date")
  private LocalDateTime endDate;

  @JsonProperty("budget")
  private BigDecimal budget;

  public CampaignEvent() {}
  public CampaignEvent(String id, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.id = id;
  }
  public CampaignEvent(String id, CampaignStatus status, LocalDateTime startDate, LocalDateTime endDate, BigDecimal budget, KafkaEventType kafkaEventType) {
    super(kafkaEventType);
    this.id = id;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
    this.budget = budget;
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

  public BigDecimal getBudget() {
    return budget;
  }
  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }

  @Override
  public String toString() {
    return "CampaignEvent{" +
        "id='" + id + '\'' +
        ", status='" + status + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", budget=" + budget +
        '}';
  }
}

