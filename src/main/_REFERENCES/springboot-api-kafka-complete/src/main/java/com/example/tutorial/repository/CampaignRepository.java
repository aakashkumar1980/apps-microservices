package com.example.tutorial.repository;

import com.example.tutorial.model.Campaign;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CampaignRepository extends CouchbaseRepository<Campaign, UUID> {
}