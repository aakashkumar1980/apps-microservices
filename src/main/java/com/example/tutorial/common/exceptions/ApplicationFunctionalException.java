package com.example.tutorial.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApplicationFunctionalException extends RuntimeException {

  public ApplicationFunctionalException(String message) {
    super(message);
  }

  public ApplicationFunctionalException(String message, Throwable cause) {
    super(message, cause);
  }

}
