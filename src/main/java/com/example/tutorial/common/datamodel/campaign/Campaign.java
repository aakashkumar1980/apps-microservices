package com.example.tutorial.common.datamodel.campaign;

import com.example.tutorial.common.utils.validation.datamodel.ValidCampaign;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ValidCampaign
@Document
public class Campaign {
  @JsonProperty("id")
  @Field("id")
  @Id
  private String id;

  @NotBlank
  @Size(min = 3, max = 100)
  @JsonProperty("name")
  @Field("name")
  private String name;

  @Size(max = 500)
  @JsonProperty("description")
  @Field("description")
  private String description;

  @NotNull
  @FutureOrPresent
  @JsonProperty("start_date")
  @Field("start_date")
  private LocalDateTime startDate;

  @NotNull
  @Future
  @JsonProperty("end_date")
  @Field("end_date")
  private LocalDateTime endDate;

  @DecimalMin("0.0")
  @JsonProperty("budget")
  @Field("budget")
  private BigDecimal budget;

  @NotNull
  @JsonProperty("status")
  @Field("status")
  private CampaignStatus status;

  @JsonProperty("cancellation_reason")
  @Field("cancellation_reason")
  private String cancellationReason;

  @JsonProperty("linked_offers")
  @Field("linked_offers")
  private List<String> linkedOffers;
  
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

  public String getCancellationReason() { return cancellationReason; }
  public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }

  public List<String> getOfferIds() { return linkedOffers; }
  public void setOfferIds(List<String> linkedOffers) { this.linkedOffers = linkedOffers; }

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
        ", cancellationReason='" + cancellationReason + '\'' +
        ", linkedOffers=" + linkedOffers +
        '}';
  }
}