package com.example.tutorial.common.exceptions;

import com.example.tutorial.common.exceptions.core.ApplicationFunctionalException;

import java.util.Map;

public class ApiValidationException extends ApplicationFunctionalException {

  private Map<String, String> validationErrors;
  public ApiValidationException(String message) {
    super(message);
  }

  public ApiValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  // getter and setter
  public Map<String, String> getValidationErrors() {
    // if validationErrors is null, return an empty map
    if (validationErrors == null) {
      return Map.of();
    } else {
      return validationErrors;
    }
  }

  public void setValidationErrors(Map<String, String> validationErrors) {
    this.validationErrors = validationErrors;
  }

  public void updateErrors(String key, String value) {
    if (this.validationErrors == null) {
      this.validationErrors = Map.of(key, value);
    } else {
      this.validationErrors.put(key, value);
    }
  }
}
