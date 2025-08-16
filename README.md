This project implements dynamic field value comparisions of the REST API HTTP request json body. 

# Framework (One Time Setup)
## Custom annotations for field validation `ValidCampaign`
Custom annotation class is created to validate the `Campaign` data model. 
This annotation is used to apply custom validation logic on the data model fields.
```java
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CampaignValidator.class)
public @interface ValidCampaign {}
```

## Custom validator class `CampaignValidator`
Create  a custom validator class that implements `ConstraintValidator` interface. 
Here, the main implementation of the validation logic is done.
```java
public class CampaignValidator implements ConstraintValidator<ValidCampaign, Campaign> {
  @Override
  public boolean isValid(Campaign campaign, ConstraintValidatorContext context) {
    boolean valid = true;
    
    /** CHECK RULE: xyz */
    ...
    
    return valid;
  }
}
```

## Data Model Class `Campaign`
Here, the custom annotation is applied to the data model class.
```java
@ValidCampaign
public class Campaign {}
```
<br/><br/>


# Implementation (examples)
Now, as an example we will implement a validation rule that checks if the `endDate` is after the `startDate` in the `Campaign` data model.
```java
public class CampaignValidator implements ConstraintValidator<ValidCampaign, Campaign> {
  @Override
  public boolean isValid(Campaign campaign, ConstraintValidatorContext context) {
     boolean valid = true;
     
    /** CHECK RULE: endDate must be after startDate */
    if (campaign.getStartDate() != null && campaign.getEndDate() != null) {
      if (!campaign.getEndDate().isAfter(campaign.getStartDate())) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("endDate must be after startDate") // the APIRequestValidationMessage's errors map value
            .addPropertyNode("endDate") // the APIRequestValidationMessage's errors map key
            .addConstraintViolation();
        valid = false;
      }
    }
    
     return valid;     
  }
}
```