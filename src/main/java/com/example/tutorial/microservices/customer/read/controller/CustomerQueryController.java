package com.example.tutorial.microservices.customer.read.controller;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.customer.Customer;
import com.example.tutorial.microservices.customer.read.service.CustomerQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerQueryController {

  private static final Logger log = LoggerFactory.getLogger(CustomerQueryController.class);

  @Autowired
  private CustomerQueryService customerQueryService;

  /**
   * Retrieves all customers.
   */
  @GetMapping
  public ResponseEntity<List<BaseDto<Customer>>> getAllCustomers() {
    List<BaseDto<Customer>> allCustomers = customerQueryService.getAllCustomers();
    log.info("Total customers found: {}", allCustomers.size());
    return ResponseEntity.ok(allCustomers);
  }

  /**
   * Retrieves a customer by its ID.
   */
  @GetMapping("/{id}")
  public ResponseEntity<BaseDto<Customer>> getCustomerById(@PathVariable String id) {
    Optional<BaseDto<Customer>> customerOptional = customerQueryService.getCustomerById(id);
    if (customerOptional.isPresent()) {
      log.info("Customer with ID: {} found", id);
      return ResponseEntity.ok(customerOptional.get());
    } else {
      log.warn("Customer with ID: {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves customers associated with a specific offer ID.
   *
   * @param offerId the ID of the offer
   * @return ResponseEntity containing a list of customers or no content if none found
   */
  @GetMapping("/offers/{offerId}")
  public ResponseEntity<List<BaseDto<Customer>>> getCustomersByOfferId(@PathVariable String offerId) {
    List<BaseDto<Customer>> customers = customerQueryService.getCustomersByOfferId(offerId);
    if (!customers.isEmpty()) {
      log.info("Found {} customers for offer ID: {}", customers.size(), offerId);
      return ResponseEntity.ok(customers);
    } else {
      log.warn("No customers found for offer ID: {}", offerId);
      return ResponseEntity.noContent().build();
    }
  }

  // -- Additional Endpoints for supporting operations  -- //
  @PostMapping("/offers/by-ids")
  public ResponseEntity<List<BaseDto<Customer>>> getCustomersByOfferIds(@RequestBody List<String> offerIds) {
    List<BaseDto<Customer>> customers = new ArrayList<BaseDto<Customer>>();
    for (String offerId : offerIds) {
      customers.addAll(customerQueryService.getCustomersByOfferId(offerId));
    }

    log.info("Total customers found by offer IDs: {}", customers.size());
    return ResponseEntity.ok(customers);
  }

}
