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
public class Redemption {


    @Id
    @JsonProperty("redemption_id")
    @Field("redemption_id")
    private String redemptionId;
    

    @NotBlank
    @JsonProperty("customer_id")
    @Field("customer_id")
    private String customerId;
    

    @NotBlank
    @JsonProperty("offer_id")
    @Field("offer_id")
    private String offerId;
    

    @NotBlank
    @JsonProperty("transaction_id")
    @Field("transaction_id")
    private String transactionId;
    

    
    @JsonProperty("amount_spent")
    @Field("amount_spent")
    private BigDecimal amountSpent;
    

    
    @JsonProperty("reward_applied")
    @Field("reward_applied")
    private BigDecimal rewardApplied;
    

    
    @JsonProperty("timestamp")
    @Field("timestamp")
    private LocalDateTime timestamp;
    

    public String getRedemptionId() { return redemptionId; }
    public void setRedemptionId(String redemptionId) { this.redemptionId = redemptionId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getOfferId() { return offerId; }
    public void setOfferId(String offerId) { this.offerId = offerId; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public BigDecimal getAmountSpent() { return amountSpent; }
    public void setAmountSpent(BigDecimal amountSpent) { this.amountSpent = amountSpent; }
    public BigDecimal getRewardApplied() { return rewardApplied; }
    public void setRewardApplied(BigDecimal rewardApplied) { this.rewardApplied = rewardApplied; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "Redemption{"  + ", ".join(["redemptionId=" + redemptionId, "customerId=" + customerId, "offerId=" + offerId, "transactionId=" + transactionId, "amountSpent=" + amountSpent, "rewardApplied=" + rewardApplied, "timestamp=" + timestamp]) + "}";
    }
}