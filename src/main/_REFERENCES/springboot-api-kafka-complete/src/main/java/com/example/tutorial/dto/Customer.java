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
public class Customer {


    @Id
    @NotBlank
    @JsonProperty("customer_id")
    @Field("customer_id")
    private String customerId;
    

    @NotBlank
    @JsonProperty("name")
    @Field("name")
    private String name;
    

    @Email
    @JsonProperty("email")
    @Field("email")
    private String email;
    

    
    @JsonProperty("phone_number")
    @Field("phone_number")
    private String phoneNumber;
    

    
    @JsonProperty("preferred_channel")
    @Field("preferred_channel")
    private String preferredChannel;
    

    
    @JsonProperty("enrolled_offer_ids")
    @Field("enrolled_offer_ids")
    private List<String> enrolledOfferIds;
    

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPreferredChannel() { return preferredChannel; }
    public void setPreferredChannel(String preferredChannel) { this.preferredChannel = preferredChannel; }
    public List<String> getEnrolledOfferIds() { return enrolledOfferIds; }
    public void setEnrolledOfferIds(List<String> enrolledOfferIds) { this.enrolledOfferIds = enrolledOfferIds; }

    @Override
    public String toString() {
        return "Customer{"  + ", ".join(["customerId=" + customerId, "name=" + name, "email=" + email, "phoneNumber=" + phoneNumber, "preferredChannel=" + preferredChannel, "enrolledOfferIds=" + enrolledOfferIds]) + "}";
    }
}