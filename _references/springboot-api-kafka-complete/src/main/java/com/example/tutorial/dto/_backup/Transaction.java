package com.example.tutorial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private UUID transactionId;
    private UUID customerId;
    private UUID merchantId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private PaymentMethod paymentMethod;


    public UUID getTransactionid() {
        return transactionId;
    }

    public void setTransactionid(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getCustomerid() {
        return customerId;
    }

    public void setCustomerid(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getMerchantid() {
        return merchantId;
    }

    public void setMerchantid(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactiondate() {
        return transactionDate;
    }

    public void setTransactiondate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public PaymentMethod getPaymentmethod() {
        return paymentMethod;
    }

    public void setPaymentmethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Transaction{" + 
               " + ", ".join(['transactionId=" + transactionId +', 'customerId=" + customerId +', 'merchantId=" + merchantId +', 'amount=" + amount +', 'transactionDate=" + transactionDate +', 'paymentMethod=" + paymentMethod +']) + "}";
    }
}
