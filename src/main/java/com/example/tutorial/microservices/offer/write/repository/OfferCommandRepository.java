package com.example.tutorial.microservices.offer.write.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferCommandRepository extends CouchbaseRepository<BaseDto<Offer>, String> {

  /**
   * Retrieves all offers by a specific campaign ID.
   * This method uses a N1QL query to select all documents of type Offer
   * (identified by the document ID starting with 'offer::') that are associated with the specified campaign ID.
   *
   * @param campaignId the ID of the campaign to filter offers by.
   * @return a list of BaseDto<Offer> objects associated with the specified campaign ID.
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'offer::%' AND data.campaign_id = $1")
  List<BaseDto<Offer>> getOffersByCampaignId(String campaignId);
}
