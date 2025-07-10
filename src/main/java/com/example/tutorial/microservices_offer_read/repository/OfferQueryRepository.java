package com.example.tutorial.microservices_offer_read.repository;

import com.example.tutorial.common.dto.offer.Offer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferQueryRepository extends CouchbaseRepository<Offer, String> {
    // Spring Data provides findAll, findById, etc.
}
