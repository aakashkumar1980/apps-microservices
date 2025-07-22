package com.example.tutorial.microservices.customer.read.service;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.customer.Customer;
import com.example.tutorial.microservices.customer.read.repository.CustomerQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerQueryService {

  private static final Logger log = LoggerFactory.getLogger(CustomerQueryService.class);

  @Autowired
  private CustomerQueryRepository customerQueryRepository;

  /**
   * Returns all customers.
   */
  public List<BaseDto<Customer>> getAllCustomers() {
    log.info("Fetching all customers from the repository");
    return customerQueryRepository.getAllCustomers();
  }

  /**
   * Returns a customer by its ID.
   */
  public Optional<BaseDto<Customer>> getCustomerById(String id) {
    log.info("Fetching customer with ID: {}", id);
    return customerQueryRepository.getCustomerById(id);
  }
}

