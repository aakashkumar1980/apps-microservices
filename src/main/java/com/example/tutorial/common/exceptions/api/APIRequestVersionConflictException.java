package com.example.tutorial.common.exceptions.api;

import com.example.tutorial.common.exceptions.ApplicationFunctionalException;


/**
 * Exception thrown when there is a version conflict in an API request.
 * This typically occurs when the resource being updated has been modified
 * by another process since it was last retrieved.
 */
public class APIRequestVersionConflictException extends ApplicationFunctionalException {
  private final APIRequestValidationMessage APIRequestValidationMessage;

  public APIRequestVersionConflictException(APIRequestValidationMessage APIRequestValidationMessage) {
    super(APIRequestValidationMessage.getMessage());
    this.APIRequestValidationMessage = APIRequestValidationMessage;
  }

  // Getters and Setters
  public APIRequestValidationMessage getRequestValidationMessage() {
    return APIRequestValidationMessage;
  }

}
