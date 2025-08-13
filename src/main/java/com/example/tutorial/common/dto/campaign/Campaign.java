package com.example.tutorial.common.dto.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Campaign {
  @JsonProperty("id")
  private Long id;

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
  
  @JsonProperty("offer_ids")
  private List<String> offerIds;
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
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
  
  public CampaignStatus getStatus() { return status; }
  public void setStatus(CampaignStatus status) { this.status = status; }

  public List<String> getOfferIds() { return offerIds; }
  public void setOfferIds(List<String> offerIds) { this.offerIds = offerIds; }

  @Override
  public String toString() {
    return "Campaign{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", budget=" + budget +
        ", status=" + status +
        ", offerIds=" + offerIds +		
        '}';
  }
}