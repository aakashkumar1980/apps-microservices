package com.example.tutorial.microservices.campaign.read.repository;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignQueryRepository<Campaign> extends CouchbaseRepository<Campaign, String> {

  /**
   * Finds all campaigns with the specified status.
   *
   * @param status the status of the campaigns to find
   * @return a list of campaigns with the specified status
   */
  List<Campaign> findByStatus(String status);
}
