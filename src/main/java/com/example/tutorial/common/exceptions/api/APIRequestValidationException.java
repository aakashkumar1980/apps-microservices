package com.example.tutorial.common.exceptions.api;

import com.example.tutorial.common.exceptions.ApplicationFunctionalException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for handling request validation errors.
 * This exception is thrown when a request does not meet the required validation criteria.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class APIRequestValidationException extends ApplicationFunctionalException {
  private final APIRequestValidationMessage APIRequestValidationMessage;

  public APIRequestValidationException(APIRequestValidationMessage APIRequestValidationMessage) {
    super(APIRequestValidationMessage.getMessage());
    this.APIRequestValidationMessage = APIRequestValidationMessage;
  }

  // Getters and Setters
  public APIRequestValidationMessage getRequestValidationMessage() {
    return APIRequestValidationMessage;
  }

}
