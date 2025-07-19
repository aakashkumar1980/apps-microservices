package com.example.tutorial.common.dto.merchant;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.List;

@Document
public class Merchant {

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

    @NotEmpty
    @Pattern(regexp = "^MR[0-9]{4,}$", message = "Merchant ID must start with 'MR' followed by at least 4 digits")
    @Field("merchant_code")
    private String merchantCode;

    @JsonProperty("active_offers")
    @Field("active_offers")
    private List<String> activeOffers;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getMerchantCode() { return merchantCode; }
    public void setMerchantCode(String merchantCode) { this.merchantCode = merchantCode; }
    public List<String> getActiveOffers() { return activeOffers; }
    public void setActiveOffers(List<String> activeOffers) { this.activeOffers = activeOffers; }

    @Override
    public String toString() {
        return "Merchant{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", location='" + location + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", activeOffers=" + activeOffers +
                '}';
    }
}