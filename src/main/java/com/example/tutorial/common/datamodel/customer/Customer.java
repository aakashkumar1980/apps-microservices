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

  @JsonProperty("enrolled_offers")
  @Field("enrolled_offers")
  private List<String> enrolledOffers;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPhoneNumber() { return phoneNumber; }
  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
  public List<String> getEnrolledOffers() { return enrolledOffers; }
  public void setEnrolledOffers(List<String> enrolledOffers) { this.enrolledOffers = enrolledOffers; }

  @Override
  public String toString() {
    return "Customer{" +
        "name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", enrolledOffers=" + enrolledOffers +
        '}';
  }
}