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
public class Merchant {


    @Id
    @NotBlank
    @JsonProperty("merchant_id")
    @Field("merchant_id")
    private String merchantId;
    

    @NotBlank
    @JsonProperty("name")
    @Field("name")
    private String name;
    

    
    @JsonProperty("category")
    @Field("category")
    private String category;
    

    
    @JsonProperty("location")
    @Field("location")
    private String location;
    

    
    @JsonProperty("active_offers")
    @Field("active_offers")
    private List<String> activeOffers;
    

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public List<String> getActiveOffers() { return activeOffers; }
    public void setActiveOffers(List<String> activeOffers) { this.activeOffers = activeOffers; }

    @Override
    public String toString() {
        return "Merchant{"  + ", ".join(["merchantId=" + merchantId, "name=" + name, "category=" + category, "location=" + location, "activeOffers=" + activeOffers]) + "}";
    }
}