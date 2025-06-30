package com.example.tutorial.common.dto.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Document
public class Campaign {
  @Id
  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  @NotBlank(message = "Campaign name is required")
  @Size(min = 3, max = 100, message = "Campaign name must be between 3 and 100 characters")
  private String name;

  @JsonProperty("description")
  @NotBlank(message = "Description is required")
  private String description;

  @JsonProperty("status")
  private CampaignStatus status;

  @JsonProperty("start_date")
  @FutureOrPresent(message = "Start date must be in the future or present")
  @NotNull(message = "Start date is required")
  private LocalDateTime startDate;

  @JsonProperty("end_date")
  @NotNull(message = "End date is required")
  @Future(message = "End date must be in the future")
  private LocalDateTime endDate;
  /** TODO: Add validation for endDate to be after startDate */

  @JsonProperty("budget")
  @NotNull(message = "Budget is required")
  @DecimalMin(value = "100.00", message = "Budget must be greater than $100.00")
  private Double budget;

  // Getters and Setters
  public Campaign() {
    // id will be set by the service using a Couchbase counter
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public Double getBudget() {
    return budget;
  }

  public void setBudget(Double budget) {
    this.budget = budget;
  }

  @Override
  public String toString() {
    return "Campaign{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", status=" + status +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", budget=" + budget +
        '}';
  }
}