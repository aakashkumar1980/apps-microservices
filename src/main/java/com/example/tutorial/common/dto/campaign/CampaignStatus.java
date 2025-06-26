package com.example.tutorial.common.dto.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CampaignStatus {
  @JsonProperty("ACTIVE")
  ACTIVE,
  @JsonProperty("INACTIVE")
  INACTIVE,
  @JsonProperty("PLANNED")
  PLANNED,
  @JsonProperty("DRAFT")
  DRAFT,
  @JsonProperty("COMPLETED")
  COMPLETED
}
