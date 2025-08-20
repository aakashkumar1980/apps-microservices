package com.example.tutorial.common.utils.validation;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.KafkaEventType;
import com.example.tutorial.common.datamodel.campaign.Campaign;
import com.example.tutorial.common.datamodel.campaign.events.CampaignEvent;
import com.example.tutorial.common.datamodel.offer.Offer;
import com.example.tutorial.common.utils.APIUtils;
import com.example.tutorial.common.utils.CacheUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
   * <p>
   * This method fetches all offers associated with the specified campaign,
   * sums their discount amounts along with the current offer's discount amount,
   * and compares the total to the campaign's budget.
   * If the total discount amount exceeds the campaign budget, a
   * {@link RequestValidationException} is thrown.
   * </p>
   *
   * @param campaignId           the ID of the campaign to validate
   * @param offerDiscountAmount  the discount amount of the current offer
   * @throws RequestValidationException if the campaign budget has been exceeded
   */
  public void validateCampaignBudgetNotExceeded(String campaignId, BigDecimal offerDiscountAmount) {
    log.info("Validating campaign budget for campaign ID: {}", campaignId);

    /** STEP 1: Fetch the campaign's budget from the cache or REST API */
    BigDecimal budget = fetchCampaignBudget(campaignId);
    /** STEP 2: Calculate the total discount amount for all offers associated with the campaign */
    double totalDiscountAmount = calculateTotalDiscountAmount(campaignId, offerDiscountAmount);
    /** STEP 3: Validate that the total discount amount (in the existing offers plus the current offer)
     * does not exceed the campaign budget */
    validateBudgetNotExceeded(campaignId, totalDiscountAmount, budget);
  }

  // -- PRIVATE METHODS -- //
  /**
   * Fetches the campaign budget from cache or via REST API.
   *
   * @param campaignId The ID of the campaign.
   * @return The budget of the campaign.
   * @throws RequestValidationException if the campaign is not found.
   */
  private BigDecimal fetchCampaignBudget(String campaignId) {
    Optional<CampaignEvent> campaignEventOptional = cacheUtils.getCache(
        campaignId, new TypeReference<CampaignEvent>() {},
        campaignsApiUrl, new TypeReference<BaseDto<Campaign>>() {},
        new CampaignEvent(campaignId, KafkaEventType.CAMPAIGN_UPDATED)
    );
    if (campaignEventOptional.isEmpty()) {
      throw new RequestValidationException(
          new RequestValidationMessage("Campaign not found", Map.of("campaignId", campaignId))
      );
    }
    BigDecimal budget = campaignEventOptional.get().getBudget();
    log.debug("Campaign budget for campaign ID {}: {}", campaignId, budget);
    return budget;
  }

  /**
   * Calculates the total discount amount for all offers associated with the campaign,
   * including the current offer's discount amount.
   *
   * @param campaignId The ID of the campaign.
   * @param offerDiscountAmount The discount amount of the current offer.
   * @return The total discount amount.
   */
  private double calculateTotalDiscountAmount(String campaignId, BigDecimal offerDiscountAmount) {
    double totalDiscountAmount = offerDiscountAmount.doubleValue();
    List<BaseDto<Offer>> allOffersByCampaignId = apiUtils.fetchDtoList(
        offersApiUrl + "/campaigns/" + campaignId,
        new TypeReference<List<BaseDto<Offer>>>() {}
    );
    if (!allOffersByCampaignId.isEmpty()) {
      totalDiscountAmount += allOffersByCampaignId.stream()
          .mapToDouble(offer -> offer.getData().getDiscountAmount().doubleValue())
          .sum();
      log.debug("Total discount amount for campaign ID {}: {}", campaignId, totalDiscountAmount);
    } else {
      log.debug("No offers found for campaign ID: {}", campaignId);
    }

    // round up to two decimal places
    BigDecimal rounded = new BigDecimal(totalDiscountAmount).setScale(2, RoundingMode.CEILING);
    return rounded.doubleValue();
  }

  /**
   * Compares the total discount amount with the campaign budget and throws an exception if exceeded.
   *
   * @param campaignId The ID of the campaign.
   * @param totalDiscountAmount The total discount amount.
   * @param budget The campaign budget.
   * @throws RequestValidationException if the budget is exceeded.
   */
  private void validateBudgetNotExceeded(String campaignId, double totalDiscountAmount, BigDecimal budget) {
    if (BigDecimal.valueOf(totalDiscountAmount).compareTo(budget) > 0) {
      RequestValidationMessage validationMessage = new RequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Campaign budget exceeded for campaign ID: %s. Total discount amount: %s, Campaign budget: %s",
              campaignId, totalDiscountAmount, budget))
      );
      throw new RequestValidationException(validationMessage);
    } else {
      log.info("Campaign budget validation passed for campaign ID: {}", campaignId);
    }
  }

}
