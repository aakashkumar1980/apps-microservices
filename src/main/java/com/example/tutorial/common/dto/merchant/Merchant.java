package com.example.tutorial.common.dto.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;

@Document
public class Merchant {
    @Id
    @JsonProperty("merchant_id")
    @NotBlank(message = "Merchant ID is required")
    private String merchantId;

    @JsonProperty("name")
    @NotBlank(message = "Merchant name is required")
    @Size(max = 100, message = "Merchant name must be at most 100 characters")
    private String name;

    @JsonProperty("category")
    @NotNull(message = "Category is required")
    private MerchantCategory category;

    @JsonProperty("location")
    @NotBlank(message = "Location is required")
    private String location;

    @JsonProperty("active_offers")
    @NotNull(message = "Active offers list is required")
    private List<@NotBlank String> activeOffers;

    @JsonProperty("performance_score")
    private Double performanceScore;

    public Merchant() {
        // Default constructor
    }

    // Getters and Setters
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
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

    public List<String> getActiveOffers() {
        return activeOffers;
    }

    public void setActiveOffers(List<String> activeOffers) {
        this.activeOffers = activeOffers;
    }

    public Double getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(Double performanceScore) {
        this.performanceScore = performanceScore;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "merchantId='" + merchantId + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", location='" + location + '\'' +
                ", activeOffers=" + activeOffers +
                ", performanceScore=" + performanceScore +
                '}';
    }
}
