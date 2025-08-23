package com.example.tutorial.common.utils;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.exceptions.ApplicationTechnicalException;
import com.example.tutorial.common.exceptions.api.APIRequestValidationMessage;
import com.example.tutorial.common.exceptions.api.APIRequestVersionConflictException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Component
public class ApplicationUtils {

  /**
   * Copies properties from the source object to the target object.
   *
   * @param source the source object
   * @param target the target object
   * @throws ApplicationTechnicalException if there is an error during property copying
   */
  public void copyProperties(Object source, Object target) {
    try {
      BeanUtils.copyProperties(target, source);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new ApplicationTechnicalException("Error copying properties from source to target", e);
    }
  }

  /**
   * Validates that the version of the current data model matches the version of the existing data model.
   * If the versions do not match, an APIRequestValidationException is thrown.
   *
   * @param id                the ID of the entity being validated
   * @param currentDataModel  the current data model (from client)
   * @param existingDataModel the existing data model (from DB)
   * @param <T>               the type of the data model
   * @return the existing version if validation passes
   * @throws APIRequestVersionConflictException if the versions do not match
   */
  public <T> Integer validateAndGetExistingVersion(String id, BaseDto<T> currentDataModel, BaseDto<T> existingDataModel) {
    Integer currentVersion = currentDataModel.getVersion(); // from client body
    Integer existingVersion  = existingDataModel.getVersion(); // from DB
    if (currentVersion == null || !currentVersion.equals(existingVersion)) {
      throw new APIRequestVersionConflictException(
          new APIRequestValidationMessage("Api request validation failed",
              Map.of("error", "Campaign %s has changed (expected version=%s). Please reload and retry."
                  .formatted(id, existingVersion))));
    }
    return existingVersion;
  }
}
