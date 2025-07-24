package com.example.tutorial.microservices.customer.write.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.customer.Customer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCommandRepository extends CouchbaseRepository<BaseDto<Customer>, String> {

}
