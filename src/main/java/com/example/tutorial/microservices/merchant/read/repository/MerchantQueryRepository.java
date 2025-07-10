package com.example.tutorial.microservices.merchant.read.repository;

import com.example.tutorial.common.dto.merchant.Merchant;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantQueryRepository extends CouchbaseRepository<Merchant, String> {
    // Spring Data provides findAll, findById, etc.
}
