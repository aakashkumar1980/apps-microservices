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
public class OfferImpression {


    @Id
    @JsonProperty("impression_id")
    @Field("impression_id")
    private String impressionId;
    

    @NotBlank
    @JsonProperty("customer_id")
    @Field("customer_id")
    private String customerId;
    

    @NotBlank
    @JsonProperty("offer_id")
    @Field("offer_id")
    private String offerId;
    

    @NotNull
    @JsonProperty("impression_time")
    @Field("impression_time")
    private LocalDateTime impressionTime;
    

    
    @JsonProperty("ad_platform")
    @Field("ad_platform")
    private String adPlatform;
    

    public String getImpressionId() { return impressionId; }
    public void setImpressionId(String impressionId) { this.impressionId = impressionId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getOfferId() { return offerId; }
    public void setOfferId(String offerId) { this.offerId = offerId; }
    public LocalDateTime getImpressionTime() { return impressionTime; }
    public void setImpressionTime(LocalDateTime impressionTime) { this.impressionTime = impressionTime; }
    public String getAdPlatform() { return adPlatform; }
    public void setAdPlatform(String adPlatform) { this.adPlatform = adPlatform; }

    @Override
    public String toString() {
        return "OfferImpression{"  + ", ".join(["impressionId=" + impressionId, "customerId=" + customerId, "offerId=" + offerId, "impressionTime=" + impressionTime, "adPlatform=" + adPlatform]) + "}";
    }
}