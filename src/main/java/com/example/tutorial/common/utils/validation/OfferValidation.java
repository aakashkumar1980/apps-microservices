package com.example.tutorial.common.utils.validation;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.KafkaEventType;
import com.example.tutorial.common.dto.campaign.Campaign;
import com.example.tutorial.common.dto.campaign.events.CampaignEvent;
import com.example.tutorial.common.dto.offer.Offer;
import com.example.tutorial.common.exceptions.RequestValidationException;
import com.example.tutorial.common.exceptions.RequestValidationMessage;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.CacheUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OfferValidation {

  private static final Logger log = LoggerFactory.getLogger(OfferValidation.class);

  @Autowired
  private CacheUtils cacheUtils;

  @Autowired
  private APIUtils apiUtils;

  @Value("${offers.api.url}")
  private String offersApiUrl;

  @Value("${campaigns.api.url}")
  private String campaignsApiUrl;

  /**
   * Validates that the campaign budget has not been exceeded.
   * This method will first fetch all the offers associated with the campaign,
   * and then do the summation of the discountAmount of those offers and compare it with the campaign budget.
   * If the total discount amount exceeds the campaign budget, then a RequestValidationException will be thrown.
   *
   * @param campaignId The ID of the campaign to validate.
   * @throws RequestValidationException if the campaign budget has been exceeded.
   */
  public void validateCampaignBudgetNotExceeded(String campaignId, BigDecimal offerDiscountAmount) {
    log.info("Validating campaign budget for campaign ID: {}", campaignId);

    /** STEP 1: Fetch the campaign budget from cache or via REST API. **/
    Optional<CampaignEvent> campaignEventOptional = cacheUtils.getCache(
        campaignId, new TypeReference<CampaignEvent>() {},
        campaignsApiUrl, new TypeReference<BaseDto<Campaign>>() {},
        new CampaignEvent(campaignId, KafkaEventType.CAMPAIGN_UPDATED)
    );
    BigDecimal budget = campaignEventOptional.get().getBudget();
    log.debug("Campaign budget for campaign ID {}: {}", campaignId, budget);

    /** STEP 2: Fetch all offers associated with the campaign and calculate the total discount amount till now. **/
    double totalDiscountAmount = offerDiscountAmount.doubleValue(); // initialize with the current offer's discount amount
    List<BaseDto<Offer>> allOffersByCampaignId = apiUtils.fetchBaseDtoList(
        offersApiUrl + "/campaigns/" + campaignId,
        new TypeReference<List<BaseDto<Offer>>>() {}
    );
    if (!allOffersByCampaignId.isEmpty()) {
      // do the summation of the discountAmount of all offers plus the offerDiscountAmount of the current offer
      totalDiscountAmount = totalDiscountAmount + allOffersByCampaignId.stream()
          .mapToDouble(offer -> offer.getData().getDiscountAmount().doubleValue())
          .sum();
      log.debug("Total discount amount for campaign ID {}: {}", campaignId, totalDiscountAmount);

    } else {
      log.debug("No offers found for campaign ID: {}", campaignId);
    }

    /** STEP 3: Compare the total discount amount with the campaign budget. **/
    /** If it exceeds the budget, throw an exception. **/
    if (BigDecimal.valueOf(totalDiscountAmount).compareTo(budget) > 0) {
      RequestValidationMessage validationMessage = new RequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Campaign budget exceeded for campaign ID: {}. Total discount amount: {}, Campaign budget: {}",
              campaignId, totalDiscountAmount, budget))
      );
      throw new RequestValidationException(validationMessage);

    } else {
      log.info("Campaign budget validation passed for campaign ID: {}", campaignId);
    }
  }

}
