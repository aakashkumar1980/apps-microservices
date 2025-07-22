package com.example.tutorial.microservices.customer.read.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.customer.Customer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id = $1")
  Optional<BaseDto<Customer>> getCustomerById(String id);
}

