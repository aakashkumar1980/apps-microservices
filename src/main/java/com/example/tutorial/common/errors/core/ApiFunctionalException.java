package com.example.tutorial.common.errors.core;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiFunctionalException extends RuntimeException {

  private Map<String, String> errors;
  public ApiFunctionalException(String message) {
    super(message);
  }

  public ApiFunctionalException(String message, Throwable cause) {
    super(message, cause);
  }

  // getter and setter
  public Map<String, String> getErrors() {
    // if errors is null, return an empty map
    if (errors == null) {
      return Map.of();
    } else {
      return errors;
    }
  }

  public void setErrors(Map<String, String> errors) {
    this.errors = errors;
  }

  public void updateErrors(String key, String value) {
    if (this.errors == null) {
      this.errors = Map.of(key, value);
    } else {
      this.errors.put(key, value);
    }
  }
}
