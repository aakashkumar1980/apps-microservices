package com.example.tutorial.common.datamodel.campaign;

import com.example.tutorial.common.utils.validation.datamodel.ValidCampaign;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
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

  /**
   * CAS (Compare-And-Swap) field for optimistic locking.
   * This field is automatically managed by Couchbase and should not be set manually.
   * In couchbase, the CAS value is a unique identifier that changes every time the document is updated.
   * It is used to ensure that updates to a document are based on the most recent version, preventing
   * lost updates in concurrent environments.
   */
  @Version
  private long cas;

  @JsonProperty("version")
  @Field("version")
  private Integer version;

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

  @JsonProperty("offer_ids")
  @Field("offer_ids")
  private List<String> offerIds;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public long getCas() {
    return cas;
  }
  public void setCas(long cas) {
    this.cas = cas;
  }

  public Integer getVersion() {
    return version;
  }
  public void setVersion(Integer version) {
    this.version = version;
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
        ", cas=" + cas +
        ", version=" + version +
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