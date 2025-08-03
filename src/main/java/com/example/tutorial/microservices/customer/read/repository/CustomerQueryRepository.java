package com.example.tutorial.microservices.customer.read.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.customer.Customer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerQueryRepository extends CouchbaseRepository<BaseDto<Customer>, String> {

  /**
   * Retrieves all customers.
   * Uses a N1QL query to select all documents of type Customer.
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'customer::%'")
  List<BaseDto<Customer>> getAllCustomers();

  /**
   * Retrieves a customer by its ID.
   * Uses the CouchbaseRepository's findById method.
   *
   * @param offerId the ID of the customer
   * @return Optional containing the BaseDto<Customer> if found, or empty if not found
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'customer::%' AND ANY offerId IN enrolledOfferIds SATISFIES offerId = $1 END")
  List<BaseDto<Customer>> getCustomersByOfferId(String offerId);
}

