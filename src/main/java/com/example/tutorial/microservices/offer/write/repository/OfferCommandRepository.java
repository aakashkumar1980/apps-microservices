package com.example.tutorial.microservices.offer.write.repository;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.offer.Offer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferCommandRepository extends CouchbaseRepository<BaseDto<Offer>, String> {

}
