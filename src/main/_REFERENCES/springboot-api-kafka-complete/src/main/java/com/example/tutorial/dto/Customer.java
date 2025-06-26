package com.example.tutorial.dto;

import java.util.List;
import java.util.UUID;

public class Customer {

    private UUID customerId;
    private String name;
    private String email;
    private String phoneNumber;
    private String preferredChannel;
    private List<UUID> enrolledOfferIds;


    public UUID getCustomerid() {
        return customerId;
    }

    public void setCustomerid(UUID customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phoneNumber;
    }

    public void setPhonenumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPreferredchannel() {
        return preferredChannel;
    }

    public void setPreferredchannel(String preferredChannel) {
        this.preferredChannel = preferredChannel;
    }

    public List<UUID> getEnrolledofferids() {
        return enrolledOfferIds;
    }

    public void setEnrolledofferids(List<UUID> enrolledOfferIds) {
        this.enrolledOfferIds = enrolledOfferIds;
    }

    @Override
    public String toString() {
        return "Customer{" + 
               " + ", ".join(['customerId=" + customerId +', 'name=" + name +', 'email=" + email +', 'phoneNumber=" + phoneNumber +', 'preferredChannel=" + preferredChannel +', 'enrolledOfferIds=" + enrolledOfferIds +']) + "}";
    }
}
