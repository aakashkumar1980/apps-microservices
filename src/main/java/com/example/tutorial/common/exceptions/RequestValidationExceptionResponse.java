package com.example.tutorial.common.exceptions;

import java.util.Map;

public class RequestValidationExceptionResponse {
  private String message;
  private Map<String, String> errors;

  public RequestValidationExceptionResponse(String message, Map<String, String> errors) {
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

}
