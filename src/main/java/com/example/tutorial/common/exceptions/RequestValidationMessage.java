package com.example.tutorial.common.exceptions;

import java.util.Map;

public class RequestValidationMessage {
  private String message;
  private Map<String, String> errors;

  public RequestValidationMessage(String message, Map<String, String> errors) {
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
    return "RequestValidationMessage{" +
           "message='" + message + '\'' +
           ", errors=" + errors +
           '}';
  }

}
