package com.example.tutorial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Campaign {

    private UUID campaignId;
    private String campaignName;
    private CampaignStatus campaignStatus;
    private String description;
    private BigDecimal budget;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<UUID> offerIds;


    public UUID getCampaignid() {
        return campaignId;
    }

    public void setCampaignid(UUID campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignname() {
        return campaignName;
    }

    public void setCampaignname(String campaignName) {
        this.campaignName = campaignName;
    }

    public CampaignStatus getCampaignstatus() {
        return campaignStatus;
    }

    public void setCampaignstatus(CampaignStatus campaignStatus) {
        this.campaignStatus = campaignStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public LocalDateTime getStartdate() {
        return startDate;
    }

    public void setStartdate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEnddate() {
        return endDate;
    }

    public void setEnddate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getCreatedby() {
        return createdBy;
    }

    public void setCreatedby(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedat() {
        return createdAt;
    }

    public void setCreatedat(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<UUID> getOfferids() {
        return offerIds;
    }

    public void setOfferids(List<UUID> offerIds) {
        this.offerIds = offerIds;
    }

    @Override
    public String toString() {
        return "Campaign{" + 
               " + ", ".join(['campaignId=" + campaignId +', 'campaignName=" + campaignName +', 'campaignStatus=" + campaignStatus +', 'description=" + description +', 'budget=" + budget +', 'startDate=" + startDate +', 'endDate=" + endDate +', 'createdBy=" + createdBy +', 'createdAt=" + createdAt +', 'offerIds=" + offerIds +']) + "}";
    }
}
