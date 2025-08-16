package com.example.tutorial.common.utils.validation.datamodel;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * ValidCampaign is a custom annotation used to validate Campaign objects.
 * It ensures that the campaign data adheres to specific validation rules.
 *
 * This annotation calls the validator class defined by the @Constraint annotation.
 * This annotation is used to validate the Campaign class by calling CampaignValidator,
 * ensuring that it meets the required criteria before being processed.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CampaignValidator.class)
public @interface ValidCampaign {
  String message() default "Invalid campaign data";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
