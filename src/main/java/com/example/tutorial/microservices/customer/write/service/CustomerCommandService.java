package com.example.tutorial.microservices.customer.write.service;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.customer.Customer;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.validation.CustomerEligibilityEngineClient;
import com.example.tutorial.microservices.customer.write.repository.CustomerCommandRepository;
import com.example.tutorial.microservices.customer.write.service.events.publisher.CustomerOfferEventPublisher;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerCommandService {

  private static final Logger log = LoggerFactory.getLogger(CustomerCommandService.class);

  @Autowired
  private CustomerCommandRepository customerCommandRepository;

  @Autowired
  private CustomerEligibilityEngineClient customerEligibilityEngineClient;

  @Autowired
  private CustomerOfferEventPublisher customerOfferEventPublisher;

  @Autowired
  private APIUtils apiUtils;

  @Value("${customers.api.url}")
  private String customersApiUrl;

  /**
   * Assigns an offer to all eligible customers.
   * <pre>
   * This method retrieves all customers from the repository and checks each customer's eligibility for the specified offer
   * using the {@code CustomerEligibilityEngineClient}.
   * If a customer is eligible, the offer ID is added to their list of enrolled offers and the customer is updated in the repository.
   * An event is published to notify that the offer has been assigned to eligible customers.
   * </pre>
   * TODO: Implement @Retry as this is an internal service call.
   *
   * @param offerId the ID of the offer to assign
   * @return a list of {@code BaseDto<Customer>} containing all customers who were assigned the offer
   */
  public void assignOfferToCustomer(String offerId) {
    log.info("Assigning offer {} to eligible customers", offerId);
    List<BaseDto<Customer>> eligibleCustomers = new ArrayList<BaseDto<Customer>>();

    /** PERSIST DATA **/
    // fetch all customers and iterate through them
    List<BaseDto<Customer>> allCustomers = apiUtils.fetchDtoList(
        customersApiUrl, new TypeReference<List<BaseDto<Customer>>>() {});
    allCustomers.forEach(customer -> {
      // check if the customer is eligible for the offer
      log.debug("Checking eligibility for customer {} for offer {}", customer.getId(), offerId);
      boolean eligible = customerEligibilityEngineClient.isEligible(customer.getId(), offerId);
      if (eligible) {
        log.info("Customer {} is eligible for offer {}", customer.getId(), offerId);
        // if eligible, add the offer ID to the customer's enrolled offers
        customer.getData().getEnrolledOfferIds().add(offerId);
        // save the updated customer back to the repository
        customerCommandRepository.save(customer);
        eligibleCustomers.add(customer);

      } else {
        log.warn("Customer {} is not eligible for offer {}", customer.getId(), offerId);
        // TODO: Logic to handle ineligibility, e.g., notifying the customer or logging
      }
    });

    /** PUBLISH EVENT **/
    // publish the offer assignment event, which can be used by other services like ""Recommendation Engine" etc.
    if(CollectionUtils.isNotEmpty(eligibleCustomers)) {
      customerOfferEventPublisher.publishOfferAssignedEvent(offerId, eligibleCustomers);
    }
  }

  /**
   * Unassigns an offer from all customers who have it enrolled.
   * <pre>
   * This method retrieves all customers from the repository, then checks each customer's enrolled offers for the specified
   * offer ID.
   * If found, the offer ID is removed from their list of enrolled offers.
   * Also publishes an event to notify that the offer has been unassigned from customers.
   *  </pre>
   *
   * @param offerId The ID of the offer to be unassigned.
   */
  public void unassignOfferFromCustomer(String offerId) {
    log.info("Unassigning offer {} from customers", offerId);
    List<BaseDto<Customer>> unassignedCustomers = new ArrayList<BaseDto<Customer>>();

    /** PERSIST DATA **/
    // retrieve all customers from the repository
    List<BaseDto<Customer>> allCustomers = apiUtils.fetchDtoList(
        customersApiUrl, new TypeReference<List<BaseDto<Customer>>>() {});
    // iterate through each customer to remove the offer ID from their enrolled offers
    allCustomers.forEach(customer -> {
      // check if the customer has the offer ID in their enrolled offers, then only remove it, else log a warning
      List<String> enrolledOfferIds= customer.getData().getEnrolledOfferIds();
      if(enrolledOfferIds.contains(offerId)) {
        log.info("Removing offer {} from customer {}", offerId, customer.getId());
        enrolledOfferIds.remove(offerId);
        // save the updated customer back to the repository
        customerCommandRepository.save(customer);
        unassignedCustomers.add(customer);

      } else {
        log.warn("Customer {} does not have offer {} enrolled", customer.getId(), offerId);
        return; // Skip to the next customer if the offer is not enrolled
      }

      /** PUBLISH EVENT **/
      // Publish the offer unassignment event, which can be used by other services like "Recommendation Engine" etc.
      if (CollectionUtils.isNotEmpty(unassignedCustomers)) {
        customerOfferEventPublisher.publishOfferUnassignedEvent(offerId, unassignedCustomers);
      }
    });
  }
}
