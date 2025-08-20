package com.example.tutorial.microservices.merchant.write.repository;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.merchant.Merchant;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantCommandRepository extends CouchbaseRepository<BaseDto<Merchant>, String> {

}

