package com.example.tutorial.microservices.offer.read.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferQueryRepository extends CouchbaseRepository<BaseDto<Offer>, String> {

  /**
   * Retrieves all offers.
   * This method uses a N1QL query to select all documents of type Offer
   * (identified by the document ID starting with 'offer::').
   *
   * @return a list of BaseDto<Offer> objects.
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'offer::%'")
  List<BaseDto<Offer>> getAllOffers();
}
