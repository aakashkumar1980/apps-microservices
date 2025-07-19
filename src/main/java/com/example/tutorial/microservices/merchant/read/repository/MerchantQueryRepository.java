package com.example.tutorial.microservices.merchant.read.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.merchant.Merchant;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantQueryRepository extends CouchbaseRepository<BaseDto<Merchant>, String> {

  /**
   * Retrieves all merchants.
   * Uses a N1QL query to select all documents of type Merchant.
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'merchant::%'")
  List<BaseDto<Merchant>> getAllMerchants();

  /**
   * Retrieves a merchant by its ID.
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id = $1")
  Optional<BaseDto<Merchant>> getMerchantById(String id);
}

