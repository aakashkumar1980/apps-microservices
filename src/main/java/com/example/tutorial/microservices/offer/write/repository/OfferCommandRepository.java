package com.example.tutorial.microservices.offer.write.repository;

import com.example.tutorial.common.dto.offer.Offer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferCommandRepository extends CouchbaseRepository<Offer, String> {
    // Spring Data provides save, deleteById, etc.
}
