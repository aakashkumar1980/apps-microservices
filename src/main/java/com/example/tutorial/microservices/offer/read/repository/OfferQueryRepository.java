package com.example.tutorial.microservices.offer.read.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

  /**
   * Retrieves an offer by its ID.
   * This method uses a N1QL query to select a document of type Offer
   * (identified by the document ID starting with 'offer::') with the specified ID.
   *
   * @param id the ID of the offer to retrieve.
   * @return an Optional containing the BaseDto<Offer> if found, or empty if not found.
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id = $1")
  Optional<BaseDto<Offer>> getOfferById(String id);
}
