package com.example.tutorial.microservices.offer.read.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferQueryRepository extends CouchbaseRepository<BaseDto<Offer>, String> {

}
