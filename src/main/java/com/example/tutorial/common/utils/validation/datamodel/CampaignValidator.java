package com.example.tutorial.common.utils.validation.datamodel;

import com.example.tutorial.common.datamodel.campaign.Campaign;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * CampaignValidator is a custom validator for the Campaign class.
 * It implements the ConstraintValidator interface to define validation logic.
 */
public class CampaignValidator implements ConstraintValidator<ValidCampaign, Campaign> {

  /**
   * This method checks the validity of a Campaign object based on custom rules.
   * @param campaign
   * @param context
   * @return
   */
  @Override
  public boolean isValid(Campaign campaign, ConstraintValidatorContext context) {
    if (campaign == null) return true; // skip nulls
    boolean valid = true;

    /** CHECK RULE: endDate must be after startDate */
    if (campaign.getStartDate() != null && campaign.getEndDate() != null) {
      if (!campaign.getEndDate().isAfter(campaign.getStartDate())) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("endDate must be after startDate")
            .addPropertyNode("endDate")
            .addConstraintViolation();
        valid = false;
      }
    }

    // Add more checks here as needed
    return valid;
  }
}
