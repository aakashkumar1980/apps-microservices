package com.example.tutorial.microservices.customer.read.controller;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.customer.Customer;
import com.example.tutorial.microservices.customer.read.service.CustomerQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    log.info("Fetching all customers");
    return ResponseEntity.ok(customerQueryService.getAllCustomers());
  }

  /**
   * Retrieves a customer by its ID.
   */
  @GetMapping("/{id}")
  public ResponseEntity<BaseDto<Customer>> getCustomerById(@PathVariable String id) {
    log.info("Fetching customer with ID: {}", id);
    Optional<BaseDto<Customer>> customer = customerQueryService.getCustomerById(id);
    if (customer.isPresent()) {
      return ResponseEntity.ok(customer.get());
    } else {
      log.warn("Customer with ID: {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }
}

