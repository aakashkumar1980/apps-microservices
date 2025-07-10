package com.example.tutorial.microservices.merchant.write.repository;

import com.example.tutorial.common.dto.merchant.Merchant;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantCommandRepository extends CouchbaseRepository<Merchant, String> {
    // Spring Data provides save, deleteById, etc.
}
