package com.example.tutorial.common.datamodel.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Campaign {

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("start_date")
  private LocalDateTime startDate;

  @JsonProperty("end_date")
  private LocalDateTime endDate;

  @JsonProperty("budget")
  private BigDecimal budget;

  @JsonProperty("status")
  private CampaignStatus status;

  @JsonProperty("cancellation_reason")
  private String cancellationReason;

  @JsonProperty("linked_offers")
  private List<String> linkedOffers;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public LocalDateTime getStartDate() { return startDate; }
  public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

  public LocalDateTime getEndDate() { return endDate; }
  public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

  public BigDecimal getBudget() { return budget; }
  public void setBudget(BigDecimal budget) { this.budget = budget; }

  public CampaignStatus getStatus() { return status; }
  public void setStatus(CampaignStatus status) { this.status = status; }
  public Boolean isActive() { return this.status == CampaignStatus.ACTIVE; }

  public String getCancellationReason() { return cancellationReason; }
  public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }

  public List<String> getLinkedOffers() { return linkedOffers; }
  public void setLinkedOffers(List<String> linkedOffers) { this.linkedOffers = linkedOffers; }

  @Override
  public String toString() {
    return "Campaign{" +
        "name='" + name +
        ", description='" + description +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", budget=" + budget +
        ", status=" + status +
        ", cancellationReason='" + cancellationReason +
        ", linkedOffers=" + linkedOffers +
        '}';
  }
}