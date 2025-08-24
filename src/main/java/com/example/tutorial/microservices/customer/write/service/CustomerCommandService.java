package com.example.tutorial.microservices.customer.write.service;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.customer.Customer;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.validation.CustomerEligibilityEngineClient;
import com.example.tutorial.microservices.customer.write.repository.CustomerCommandRepository;
import com.example.tutorial.microservices.customer.write.service.events.publisher.CustomerOfferEventPublisher;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
  @SuppressWarnings("unchecked")
  public void assignOfferToCustomer(String offerId) {
    log.info("Assigning offer {} to eligible customers", offerId);

    List<BaseDto<Customer>> eligibleCustomers = apiUtils.fetchDtoList(
        customersApiUrl, new TypeReference<List<BaseDto<Customer>>>() {})
      .stream()
        /** DATA VALIDATION **/
        .filter(customerObj -> {
          BaseDto<Customer> customer = (BaseDto<Customer>) customerObj;
          log.debug("Checking eligibility for customer {} for offer {}", customer.getId(), offerId);

          /** STEP 1: Check if customer is eligible for the offer.
           * If eligible, proceed to assign the offer **/
          boolean eligible = customerEligibilityEngineClient.isEligible(customer.getId(), offerId);
          if (!eligible) log.warn("Customer {} is not eligible for offer {}", customer.getId(), offerId);
          return eligible;
        }).toList();

    /** PERSIST DATA **/
    eligibleCustomers.forEach(customerDto -> {
      log.info("Customer {} is eligible for offer {}", customerDto.getId(), offerId);
      /** STEP 2: Assign offer to customer **/
      customerDto.getData().getEnrolledOfferIds().add(offerId);
      /** STEP 3: Save updated customer **/
      customerCommandRepository.save(customerDto);
    });

    /** PUBLISH EVENT **/
    if (!eligibleCustomers.isEmpty()) {
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
  @SuppressWarnings("unchecked")
  public void unassignOfferFromCustomer(String offerId) {
    log.info("Unassigning offer {} from customers", offerId);

    List<BaseDto<Customer>> unassignedCustomers = apiUtils.fetchDtoList(
        customersApiUrl, new TypeReference<List<BaseDto<Customer>>>() {})
      .stream()
        /** DATA VALIDATION **/
        .filter(customerObj -> {
          BaseDto<Customer> customer = (BaseDto<Customer>) customerObj;

          /** STEP 1: Check if customer has the offer enrolled.
           * If yes, proceed to unassign the offer **/
          boolean hasOffer = customer.getData().getEnrolledOfferIds().contains(offerId);
          if (!hasOffer) log.warn("Customer {} does not have offer {} enrolled", customer.getId(), offerId);
          return hasOffer;
        }).toList();

    /** PERSIST DATA **/
    unassignedCustomers.forEach(customerDto -> {
      /** STEP 2: Unassign offer from customer **/
      log.info("Removing offer {} from customer {}", offerId, customerDto.getId());
      customerDto.getData().getEnrolledOfferIds().remove(offerId);
      /** STEP 3: Save updated customer **/
      customerCommandRepository.save(customerDto);
    });

    /** PUBLISH EVENT **/
    if (!unassignedCustomers.isEmpty()) {
      customerOfferEventPublisher.publishOfferUnassignedEvent(offerId, unassignedCustomers);
    }
  }
}
