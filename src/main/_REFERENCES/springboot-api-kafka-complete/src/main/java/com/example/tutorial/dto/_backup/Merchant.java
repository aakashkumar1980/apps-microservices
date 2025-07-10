package com.example.tutorial.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Merchant {

    private UUID merchantId;
    private String name;
    private MerchantCategory category;
    private String location;
    private List<Offer> activeOffers;
    private BigDecimal performanceScore;


    public UUID getMerchantid() {
        return merchantId;
    }

    public void setMerchantid(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MerchantCategory getCategory() {
        return category;
    }

    public void setCategory(MerchantCategory category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Offer> getActiveoffers() {
        return activeOffers;
    }

    public void setActiveoffers(List<Offer> activeOffers) {
        this.activeOffers = activeOffers;
    }

    public BigDecimal getPerformancescore() {
        return performanceScore;
    }

    public void setPerformancescore(BigDecimal performanceScore) {
        this.performanceScore = performanceScore;
    }

    @Override
    public String toString() {
        return "Merchant{" + 
               " + ", ".join(['merchantId=" + merchantId +', 'name=" + name +', 'category=" + category +', 'location=" + location +', 'activeOffers=" + activeOffers +', 'performanceScore=" + performanceScore +']) + "}";
    }
}
