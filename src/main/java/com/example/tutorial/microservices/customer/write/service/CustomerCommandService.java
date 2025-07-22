package com.example.tutorial.microservices.customer.write.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.customer.Customer;
import com.example.tutorial.common.utils.validation.CustomerEligibilityEngineClient;
import com.example.tutorial.microservices.customer.write.repository.CustomerCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCommandService {

  private static final Logger log = LoggerFactory.getLogger(CustomerCommandService.class);

  @Autowired
  private CustomerCommandRepository customerCommandRepository;

  @Autowired
  private CustomerEligibilityEngineClient customerEligibilityEngineClient;

  /**
   * Assigns an offer to all customers who are eligible for it.
   * This method retrieves all customers from the repository, then
   * checks each customer's eligibility for the specified offer using the CustomerEligibilityEngineClient.
   * If a customer is eligible, the offer ID is added to their list of enrolled offers.
   *
   * @param offerId The ID of the offer to be assigned.
   */
  public void assignOfferToCustomer(String offerId) {
    // Retrieve all customers from the repository
    List<BaseDto<Customer>> allCustomers = customerCommandRepository.getAllCustomers();

    // Iterate through each customer to check eligibility for the offer
    allCustomers.forEach(customer -> {
      // Check if the customer is eligible for the offer
      boolean eligible = customerEligibilityEngineClient.isEligible(customer.getId(), offerId);
      if (eligible) {
        log.info("Customer {} is eligible for offer {}", customer.getId(), offerId);
        // Add the offer ID to the customer's enrolled offers
        customer.getData().getEnrolledOfferIds().add(offerId);
        // Save the updated customer back to the repository
        customerCommandRepository.save(customer);
      } else {
        log.warn("Customer {} is not eligible for offer {}", customer.getId(), offerId);
        // TODO: Logic to handle ineligibility, e.g., notifying the customer or logging
      }
    });
  }

}
