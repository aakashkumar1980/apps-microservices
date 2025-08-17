package com.example.tutorial.microservices.campaign.write.repository;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignCommandRepository extends CouchbaseRepository<Campaign, String> {
}
