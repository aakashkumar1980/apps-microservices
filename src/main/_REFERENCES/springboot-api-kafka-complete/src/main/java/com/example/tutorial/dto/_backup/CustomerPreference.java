package com.example.tutorial.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CustomerPreference {

    private UUID customerId;
    private List<String> categories;
    private PreferredRewardType preferredRewardType;
    private BigDecimal engagementScore;


    public UUID getCustomerid() {
        return customerId;
    }

    public void setCustomerid(UUID customerId) {
        this.customerId = customerId;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public PreferredRewardType getPreferredrewardtype() {
        return preferredRewardType;
    }

    public void setPreferredrewardtype(PreferredRewardType preferredRewardType) {
        this.preferredRewardType = preferredRewardType;
    }

    public BigDecimal getEngagementscore() {
        return engagementScore;
    }

    public void setEngagementscore(BigDecimal engagementScore) {
        this.engagementScore = engagementScore;
    }

    @Override
    public String toString() {
        return "CustomerPreference{" + 
               " + ", ".join(['customerId=" + customerId +', 'categories=" + categories +', 'preferredRewardType=" + preferredRewardType +', 'engagementScore=" + engagementScore +']) + "}";
    }
}
