package com.example.tutorial.common.datamodel.customer;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.List;

public class Customer {

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

  @JsonProperty("enrolled_offer_ids")
  @Field("enrolled_offer_ids")
  private List<String> enrolledOfferIds;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPhoneNumber() { return phoneNumber; }
  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
  public List<String> getEnrolledOfferIds() { return enrolledOfferIds; }
  public void setEnrolledOfferIds(List<String> enrolledOfferIds) { this.enrolledOfferIds = enrolledOfferIds; }

  @Override
  public String toString() {
    return "Customer{" +
        "name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", enrolledOfferIds=" + enrolledOfferIds +
        '}';
  }
}