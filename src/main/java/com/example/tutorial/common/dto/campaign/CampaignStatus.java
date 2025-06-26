package com.example.tutorial.common.dto.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CampaignStatus {
  @JsonProperty("ACTIVE")
  ACTIVE,
  @JsonProperty("INACTIVE")
  INACTIVE,
  @JsonProperty("PAUSED")
  PAUSED,
  @JsonProperty("COMPLETED")
  COMPLETED,
  @JsonProperty("CANCELLED")
  CANCELLED
}
