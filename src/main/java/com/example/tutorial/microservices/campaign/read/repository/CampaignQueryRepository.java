package com.example.tutorial.microservices.campaign.read.repository;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignQueryRepository extends CouchbaseRepository<Campaign, String> {

  /**
   * Finds all campaigns with the specified status.
   *
   * @param status the status of the campaigns to find
   * @return a list of campaigns with the specified status
   */
  @Query("" +
      "#{#n1ql.selectEntity} WHERE #{#n1ql.filter} " +
      "AND status = $1"
  )
  List<Campaign> getCampaignsByStatus(CampaignStatus status);

  /**
   * Finds campaigns that contain the specified offer ID.
   *
   * @param offerId the offer ID to search for in campaigns
   * @return a list of campaigns that contain the specified offer ID
   */
  @Query("" +
      "#{#n1ql.selectEntity} WHERE #{#n1ql.filter} " +
      "AND ANY v IN linked_offers SATISFIES v = $1 END"
  )
  List<Campaign> getCampaignsByOfferId(String offerId);
}
