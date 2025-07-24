package com.example.tutorial.common.dto.campaign;

public enum CampaignStatus {
    DRAFT,      // Campaign is being created and not yet finalized
    SCHEDULED,  // Campaign is scheduled to start in future
    ACTIVE,     // Campaign is currently running
    PAUSED,     // Campaign is temporarily halted
    COMPLETED,  // Campaign has finished successfully
    CANCELLED,  // Campaign has been cancelled before completion
    EXPIRED     // Campaign has reached its end date and is no longer valid
}