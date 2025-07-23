package com.example.tutorial.microservices.merchant.write.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.merchant.Merchant;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantCommandRepository extends CouchbaseRepository<BaseDto<Merchant>, String> {

}

