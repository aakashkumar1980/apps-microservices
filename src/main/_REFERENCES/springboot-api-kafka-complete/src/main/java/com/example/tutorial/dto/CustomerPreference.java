package com.example.dto;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document
public class CustomerPreference {


    @NotBlank
    @JsonProperty("customer_id")
    @Field("customer_id")
    private String customerId;
    

    
    @JsonProperty("categories")
    @Field("categories")
    private List<String> categories;
    

    @NotNull
    @JsonProperty("preferred_reward_type")
    @Field("preferred_reward_type")
    private RewardType preferredRewardType;
    

    
    @JsonProperty("engagement_score")
    @Field("engagement_score")
    private BigDecimal engagementScore;
    

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    public RewardType getPreferredRewardType() { return preferredRewardType; }
    public void setPreferredRewardType(RewardType preferredRewardType) { this.preferredRewardType = preferredRewardType; }
    public BigDecimal getEngagementScore() { return engagementScore; }
    public void setEngagementScore(BigDecimal engagementScore) { this.engagementScore = engagementScore; }

    @Override
    public String toString() {
        return "CustomerPreference{"  + ", ".join(["customerId=" + customerId, "categories=" + categories, "preferredRewardType=" + preferredRewardType, "engagementScore=" + engagementScore]) + "}";
    }
}