package com.example.tutorial.microservices.offer.write.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.offer.Offer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferCommandRepository extends CouchbaseRepository<BaseDto<Offer>, String> {

  /**
   * Finds all offers associated with a specific campaign ID.
   *
   * @param campaignId the ID of the campaign
   * @return a list of offers associated with the given campaign ID
   */
  List<BaseDto<Offer>> findByDataCampaignId(String campaignId);
}
