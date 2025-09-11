package com.example.tutorial.common.datamodel.redemption;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Redemption {

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("offer_id")
    private String offerId;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("redemption_time")
    private LocalDateTime redemptionTime;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("status")
    private RedemptionStatus status;

    @JsonProperty("type")
    private RedemptionType type;

    @JsonProperty("reward_value")
    private BigDecimal rewardValue;

    // Getters and setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getOfferId() { return offerId; }
    public void setOfferId(String offerId) { this.offerId = offerId; }

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }

    public LocalDateTime getRedemptionTime() { return redemptionTime; }
    public void setRedemptionTime(LocalDateTime redemptionTime) { this.redemptionTime = redemptionTime; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public RedemptionStatus getStatus() { return status; }
    public void setStatus(RedemptionStatus status) { this.status = status; }

    public RedemptionType getType() { return type; }
    public void setType(RedemptionType type) { this.type = type; }

    public BigDecimal getRewardValue() { return rewardValue; }
    public void setRewardValue(BigDecimal rewardValue) { this.rewardValue = rewardValue; }

    @Override
    public String toString() {
        return "Redemption{" +
                "customerId='" + customerId + '\'' +
                ", offerId='" + offerId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", redemptionTime=" + redemptionTime +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", rewardValue=" + rewardValue +
                '}';
    }
}
