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
public class TargetedCustomer {


    @NotBlank
    @JsonProperty("customer_id")
    @Field("customer_id")
    private String customerId;
    

    @NotBlank
    @JsonProperty("offer_id")
    @Field("offer_id")
    private String offerId;
    

    
    @JsonProperty("targeting_score")
    @Field("targeting_score")
    private BigDecimal targetingScore;
    

    
    @JsonProperty("eligibility_status")
    @Field("eligibility_status")
    private Boolean eligibilityStatus;
    

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getOfferId() { return offerId; }
    public void setOfferId(String offerId) { this.offerId = offerId; }
    public BigDecimal getTargetingScore() { return targetingScore; }
    public void setTargetingScore(BigDecimal targetingScore) { this.targetingScore = targetingScore; }
    public Boolean getEligibilityStatus() { return eligibilityStatus; }
    public void setEligibilityStatus(Boolean eligibilityStatus) { this.eligibilityStatus = eligibilityStatus; }

    @Override
    public String toString() {
        return "TargetedCustomer{"  + ", ".join(["customerId=" + customerId, "offerId=" + offerId, "targetingScore=" + targetingScore, "eligibilityStatus=" + eligibilityStatus]) + "}";
    }
}