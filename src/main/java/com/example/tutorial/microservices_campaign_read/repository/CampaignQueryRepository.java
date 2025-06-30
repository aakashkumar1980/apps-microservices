package com.example.tutorial.microservices_campaign_read.repository;

import com.example.tutorial.common.dto.campaign.Campaign;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignQueryRepository extends CouchbaseRepository<Campaign, String> {
    // Spring Data provides findAll, findById, etc.
}
