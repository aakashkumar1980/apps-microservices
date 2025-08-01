package com.example.tutorial.common.utils.validation;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.dto.customer.Customer;
import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.common.dto.offer.events.OfferEvent;
import com.example.tutorial.common.exceptions.ApplicationFunctionalException;
import com.example.tutorial.common.utils.APIUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OfferValidation {

  private static final Logger log = LoggerFactory.getLogger(OfferValidation.class);

  @Autowired
  private APIUtils apiUtils;

  @Value("${offers.api.url}")
  private String offersApiUrl;

  /**
   * Validates the current enrollments for an offer against its maximum allowed redemptions.
   * If the current enrollments exceed or equal the maximum redemptions, an event is published
   * and an exception is thrown to prevent further assignments.
   *
   * @param offerId The ID of the offer to validate.
   * @param allCustomers List of all customers to check against the offer's enrollments.
   * @throws ApplicationFunctionalException if the maximum enrollments for the offer are reached.
   */
  public void checkEnrollmentsCap(String offerId, List<BaseDto<Customer>> allCustomers) {
    long currentEnrollments = allCustomers.stream()
        .filter(c -> c.getData().getEnrolledOfferIds().contains(offerId))
        .count();
    log.info("Current enrollments for offer {}: {}", offerId, currentEnrollments);
    Optional<BaseDto<Offer>> offerOptional = apiUtils.fetchAndCacheBaseDtoById(
        offersApiUrl, offerId, new TypeReference<BaseDto<Offer>>() {},
        new OfferEvent(offerId, KafkaEventType.OFFER_UPDATED));
    if(offerOptional.isPresent()) {
      int maxRedemptions = offerOptional.get().getData().getMaxRedemptions();
      if (currentEnrollments >= maxRedemptions) {
        throw new ApplicationFunctionalException(String.format("Max enrollments ({}) reached for offer {}. No more assignments allowed.", maxRedemptions, offerId));
      }
    } else {
      throw new ApplicationFunctionalException("Offer not found: " + offerId);
    }
  }
}
