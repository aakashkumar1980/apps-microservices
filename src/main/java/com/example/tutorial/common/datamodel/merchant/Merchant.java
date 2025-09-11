package com.example.tutorial.common.datamodel.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Merchant {

    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private String category;

    @JsonProperty("location")
    private String location;

    @JsonProperty("code")
    private String code;

    @JsonProperty("active_offers")
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