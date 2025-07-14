package com.example.tutorial.microservices.campaign.read.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.campaign.Campaign;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
   * Retrieves a campaign by its ID.
   * This method uses a N1QL query to select a document of type Campaign
   * (identified by the document ID starting with 'campaign::').
   *
   * @param id the ID of the campaign to retrieve.
   * @return an Optional containing the BaseDto<Campaign> if found, or empty if not found.
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id = $1")
  Optional<BaseDto<Campaign>> getCampaignById(String id);
}
