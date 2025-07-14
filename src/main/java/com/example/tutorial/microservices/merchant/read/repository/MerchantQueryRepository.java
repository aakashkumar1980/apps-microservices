package com.example.tutorial.microservices.merchant.read.repository;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.merchant.Merchant;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantQueryRepository extends CouchbaseRepository<BaseDto<Merchant>, String> {

  /**
   * Retrieves all merchants.
   * This method uses a N1QL query to select all documents of type Merchant
   * (identified by the document ID starting with 'merchant::').
   *
   * @return a list of BaseDto<Merchant> objects.
   */
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'merchant::%'")
  List<BaseDto<Merchant>> getAllMerchants();
}
