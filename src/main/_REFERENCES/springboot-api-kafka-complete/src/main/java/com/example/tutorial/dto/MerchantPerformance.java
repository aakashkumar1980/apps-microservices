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
public class MerchantPerformance {


    @NotBlank
    @JsonProperty("merchant_id")
    @Field("merchant_id")
    private String merchantId;
    

    
    @JsonProperty("total_transactions")
    @Field("total_transactions")
    private Integer totalTransactions;
    

    
    @JsonProperty("total_redemptions")
    @Field("total_redemptions")
    private Integer totalRedemptions;
    

    
    @JsonProperty("avg_transaction_value")
    @Field("avg_transaction_value")
    private BigDecimal avgTransactionValue;
    

    
    @JsonProperty("performance_score")
    @Field("performance_score")
    private BigDecimal performanceScore;
    

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    public Integer getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(Integer totalTransactions) { this.totalTransactions = totalTransactions; }
    public Integer getTotalRedemptions() { return totalRedemptions; }
    public void setTotalRedemptions(Integer totalRedemptions) { this.totalRedemptions = totalRedemptions; }
    public BigDecimal getAvgTransactionValue() { return avgTransactionValue; }
    public void setAvgTransactionValue(BigDecimal avgTransactionValue) { this.avgTransactionValue = avgTransactionValue; }
    public BigDecimal getPerformanceScore() { return performanceScore; }
    public void setPerformanceScore(BigDecimal performanceScore) { this.performanceScore = performanceScore; }

    @Override
    public String toString() {
        return "MerchantPerformance{"  + ", ".join(["merchantId=" + merchantId, "totalTransactions=" + totalTransactions, "totalRedemptions=" + totalRedemptions, "avgTransactionValue=" + avgTransactionValue, "performanceScore=" + performanceScore]) + "}";
    }
}