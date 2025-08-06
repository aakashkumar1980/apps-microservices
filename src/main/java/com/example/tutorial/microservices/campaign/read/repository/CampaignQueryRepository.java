package com.example.tutorial.microservices.campaign.read.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.campaign.Campaign;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignQueryRepository extends CouchbaseRepository<BaseDto<Campaign>, String> {

  /**
   * Retrieves all campaigns.
   * This method uses a N1QL query to select all documents of type Campaign
   * (identified by the document ID starting with 'campaign::').
   *
   * @return a list of BaseDto<Campaign> objects.
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'campaign::%'")
  List<BaseDto<Campaign>> getAllCampaigns();

  /**
   * Retrieves a campaign by its Offer ID.
   *
   * @param offerId the ID of the campaign's offer
   * @return Optional containing the BaseDto<Campaign> if found, or empty if not found
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'campaign::%' AND ANY offerId IN data.offer_ids SATISFIES offerId = $1 END")
  List<BaseDto<Campaign>> getCampaignsByOfferId(String offerId);
}
