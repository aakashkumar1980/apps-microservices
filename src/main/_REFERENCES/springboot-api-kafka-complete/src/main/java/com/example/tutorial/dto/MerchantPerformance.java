package com.example.tutorial.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class MerchantPerformance {

    private UUID merchantId;
    private Integer totalTransactions;
    private Integer totalRedemptions;
    private BigDecimal avgTransactionValue;


    public UUID getMerchantid() {
        return merchantId;
    }

    public void setMerchantid(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getTotaltransactions() {
        return totalTransactions;
    }

    public void setTotaltransactions(Integer totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public Integer getTotalredemptions() {
        return totalRedemptions;
    }

    public void setTotalredemptions(Integer totalRedemptions) {
        this.totalRedemptions = totalRedemptions;
    }

    public BigDecimal getAvgtransactionvalue() {
        return avgTransactionValue;
    }

    public void setAvgtransactionvalue(BigDecimal avgTransactionValue) {
        this.avgTransactionValue = avgTransactionValue;
    }

    @Override
    public String toString() {
        return "MerchantPerformance{" + 
               " + ", ".join(['merchantId=" + merchantId +', 'totalTransactions=" + totalTransactions +', 'totalRedemptions=" + totalRedemptions +', 'avgTransactionValue=" + avgTransactionValue +']) + "}";
    }
}
