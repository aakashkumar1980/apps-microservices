package com.example.tutorial.common.exceptions.handler;

import com.example.tutorial.common.exceptions.ApplicationException;
import com.example.tutorial.common.exceptions.ApplicationFunctionalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handles all exceptions that are not specifically handled by other exception handlers.
   * This method will log the exception and return a generic error response.
   *
   * @param ex the exception that was thrown
   * @return a ResponseEntity with a generic error message and HTTP status 500
   */
  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<String> handleApplicationException(ApplicationException ex) {
    // Determine the HTTP status based on the exception type
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // default status
    if (ex.getClass().isAnnotationPresent(ResponseStatus.class)) {
      ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
      status = responseStatus.value();
    }

    logger.error("ApplicationException caught: {}", ex.getMessage(), ex);
    return new ResponseEntity<>("Application error occurred", status);
  }

  /**
   * Handles ApplicationFunctionalException specifically, allowing for custom handling of API-related errors.
   * This method will log the exception and return a specific error response.
   *
   * @param ex the ApplicationFunctionalException that was thrown
   * @return a ResponseEntity with a specific error message and HTTP status
   */
  @ExceptionHandler(ApplicationFunctionalException.class)
  public ResponseEntity<String> handleApiFunctionalException(ApplicationFunctionalException ex) {
    // Determine the HTTP status based on the exception type
    HttpStatus status = HttpStatus.BAD_REQUEST; // default status
    if (ex.getClass().isAnnotationPresent(ResponseStatus.class)) {
      ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
      status = responseStatus.value();
    }

    logger.warn("ApplicationFunctionalException caught: {}", ex.getMessage(), ex);
    return new ResponseEntity<>(String.format("Application functional error occurred: %s", ex.getMessage()), status);
  }

}
