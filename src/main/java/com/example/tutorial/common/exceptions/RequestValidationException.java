package com.example.tutorial.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for handling request validation errors.
 * This exception is thrown when a request does not meet the required validation criteria.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestValidationException extends RuntimeException {
  private final RequestValidationMessage requestValidationMessage;

  public RequestValidationException(RequestValidationMessage requestValidationMessage) {
    super(requestValidationMessage.getMessage());
    this.requestValidationMessage = requestValidationMessage;
  }

  // Getters and Setters
  public RequestValidationMessage getRequestValidationMessage() {
    return requestValidationMessage;
  }

}
