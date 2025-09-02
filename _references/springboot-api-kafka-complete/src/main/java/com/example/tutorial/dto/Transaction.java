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
public class Transaction {


    @Id
    @JsonProperty("transaction_id")
    @Field("transaction_id")
    private String transactionId;
    

    @NotBlank
    @JsonProperty("customer_id")
    @Field("customer_id")
    private String customerId;
    

    @NotBlank
    @JsonProperty("merchant_id")
    @Field("merchant_id")
    private String merchantId;
    

    @DecimalMin("0.01")
    @JsonProperty("amount")
    @Field("amount")
    private BigDecimal amount;
    

    @NotNull
    @JsonProperty("transaction_date")
    @Field("transaction_date")
    private LocalDateTime transactionDate;
    

    
    @JsonProperty("payment_method")
    @Field("payment_method")
    private PaymentMethod paymentMethod;
    

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    @Override
    public String toString() {
        return "Transaction{"  + ", ".join(["transactionId=" + transactionId, "customerId=" + customerId, "merchantId=" + merchantId, "amount=" + amount, "transactionDate=" + transactionDate, "paymentMethod=" + paymentMethod]) + "}";
    }
}