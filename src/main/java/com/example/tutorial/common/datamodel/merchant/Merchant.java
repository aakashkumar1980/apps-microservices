package com.example.tutorial.common.datamodel.merchant;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.List;

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
    @JsonProperty("code")
    @Field("code")
    private String code;

    @JsonProperty("active_offers")
    @Field("active_offers")
    private List<String> activeOffers;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public List<String> getActiveOffers() { return activeOffers; }
    public void setActiveOffers(List<String> activeOffers) { this.activeOffers = activeOffers; }

    @Override
    public String toString() {
        return "Merchant{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", location='" + location + '\'' +
                ", code='" + code + '\'' +
                ", activeOffers=" + activeOffers +
                '}';
    }
}