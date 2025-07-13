package com.example.tutorial.common.dto.merchant;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.List;

@Document
public class Merchant {

    @JsonProperty("name")
    @Field("name")
    @NotBlank(message = "Merchant name is required")
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
        return "Merchant{" +
            ", name='" + name + '\'' +
            ", category='" + category + '\'' +
            ", location='" + location + '\'' +
            ", activeOffers=" + activeOffers +
            '}';
    }
}