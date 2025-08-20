package com.example.tutorial.microservices.campaign.read.repository;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignQueryRepository extends CouchbaseRepository<BaseDto<Campaign>, String> {


  /**
   * Retrieves all campaigns by their ID prefix.
   * <p>
   * The method name <code>findBy<u>IdStartingWith</u></code> follows the Spring Data naming convention:
   * <ul>
   *   <li><b>findBy</b>: indicates a query method</li>
   *   <li><b>Id</b>: filters based on the ID field</li>
   *   <li><b>StartingWith</b>: matches IDs that start with the specified prefix</li>
   * </ul>
   * Other matching methods can be used, such as <code>Like</code>, <code>EndingWith</code>, <code>Containing</code>, etc.
   * <p>
   * The entity type is <code>BaseDto&lt;Campaign&gt;</code>, which contains the row key or ID.
   * <pre>
   * BaseDto
   *   + String id
   * </pre>
   *
   * @param idPrefix the prefix of the campaign ID
   * @return a list of <code>BaseDto&lt;Campaign&gt;</code> objects matching the criteria
   */
  // OR @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'campaign::%'")
  public List<BaseDto<Campaign>> findByIdStartingWith(String idPrefix);

  /**
   * Retrieves all campaigns by their ID prefix and data status.
   * <p>
   * The method name <code>findBy<u>IdStartingWith</u>AndData<u>Status</u></code> follows the Spring Data naming convention:
   * <ul>
   *   <li><b>findBy</b>: indicates a query method</li>
   *   <li><b>Id</b>: filters based on the ID field</li>
   *   <li><b>StartingWith</b>: matches IDs that start with the specified prefix</li>
   *   <li><b>And</b>: combines multiple conditions</li>
   *   <li><b>Data</b>: refers to the data field within the BaseDto</li>
   *   <li><b>Status</b>: filters based on the status field within the Campaign data</li>
   * </ul>
   * The entity type is <code>BaseDto&lt;Campaign&gt;</code>, which contains the row key or ID and the campaign data.
   * <pre>
   *   <BaseDto>
   *   + String id
   *   + T data
   *    <Campaign>
   *      + CampaignStatus status
   * </pre>
   * @param status the data status of the campaign
   * @return a list of <code>BaseDto&lt;Campaign&gt;</code> objects matching the criteria
   * */
  // OR, public List<BaseDto<Campaign>> findByIdStartingWithAndDataStatus(String idPrefix, String status);
  @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND META().id LIKE 'campaign::%' AND data.status = $status")
  public List<BaseDto<Campaign>> findCampaignByStatus(String status);
}
