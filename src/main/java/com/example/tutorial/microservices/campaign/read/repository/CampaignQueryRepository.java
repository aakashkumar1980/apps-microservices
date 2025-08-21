package com.example.tutorial.microservices.campaign.read.repository;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.CampaignStatus;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignQueryRepository extends CouchbaseRepository<BaseDto<Campaign>, String> {


  /**
   * Retrieves all campaigns.
   *
   * @return a list of <code>BaseDto&lt;Campaign&gt;</code> objects matching the criteria
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'campaign::%'")
  List<BaseDto<Campaign>> getAllCampaigns();

  /**
   * Retrieves all campaigns by their ID prefix and data status.
   *
   * @param status the data status of the campaign
   * @return a list of <code>BaseDto&lt;Campaign&gt;</code> objects matching the criteria
   * */
  @Query("" +
      "#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'campaign::%' " +
      "AND data.status = $status"
  )
  List<BaseDto<Campaign>> getCampaignsByStatus(CampaignStatus status);

  /**
   * Finds campaigns that contain the specified offer ID.
   *
   * @param offerId the offer ID to search for in campaigns
   * @return a list of campaigns that contain the specified offer ID
   */
  @Query("" +
      "#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'campaign::%' " +
      "AND ANY offerId IN data.offer_ids SATISFIES offerId = $offerId END")
  List<BaseDto<Campaign>> getCampaignsByOfferId(String offerId);
}
