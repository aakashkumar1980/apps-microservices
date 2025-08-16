package com.example.tutorial.common.exceptions.api;

import java.util.Map;

/**
 * Represents a validation message for requests, containing a message and a map of errors.
 */
public class APIRequestValidationMessage {
  private String message;
  private Map<String, String> errors;

  public APIRequestValidationMessage(String message, Map<String, String> errors) {
    this.message = message;
    this.errors = errors;
  }

  // Getters and Setters
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  public Map<String, String> getErrors() {
    return errors;
  }
  public void setErrors(Map<String, String> errors) {
    this.errors = errors;
  }

  @Override
  public String toString() {
    return "APIRequestValidationMessage{" +
           "message='" + message + '\'' +
           ", errors=" + errors +
           '}';
  }

}
