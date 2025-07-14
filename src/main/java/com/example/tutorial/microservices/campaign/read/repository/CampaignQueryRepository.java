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
}
