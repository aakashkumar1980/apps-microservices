package com.example.tutorial.common.utils.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerEligibilityEngineClient {

  private static final Logger log = LoggerFactory.getLogger(CustomerEligibilityEngineClient.class);

  @Autowired
  private RestTemplate restTemplate;

  @Value("${customer.eligibility.engine.url}")
  private String customerEligibilityEngineUrl;

  /**
   * Checks if a customer is eligible for a specific offer.
   * TODO: Implement the actual eligibility logic, when the Customer Eligibility Engine is available.
   * TODO: Implement via. CircuitBreaker as it's an external service call.
   *
   * @param customerId the ID of the customer
   * @param offerId the ID of the offer
   * @return true if the customer is eligible for the offer, false otherwise
   */
  public boolean isEligible(String customerId, String offerId) {
    // mock implementation for eligibility check. now simply returning random boolean
    log.info("Checking offer eligibility for customer ID: {} for offer ID: {}", customerId, offerId);
    return Math.random() < 0.5; // Randomly return true or false
  }
}
