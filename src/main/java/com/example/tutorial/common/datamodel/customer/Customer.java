package com.example.tutorial.common.datamodel.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Customer {

  @JsonProperty("name")
  private String name;

  @JsonProperty("email")
  private String email;

  @JsonProperty("phone_number")
  private String phoneNumber;

  @JsonProperty("enrolled_offers")
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