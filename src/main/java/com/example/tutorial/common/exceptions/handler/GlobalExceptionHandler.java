package com.example.tutorial.common.exceptions.handler;

import com.example.tutorial.common.exceptions.ApplicationException;
import com.example.tutorial.common.exceptions.RequestValidationException;
import com.example.tutorial.common.exceptions.RequestValidationMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Global exception handler for the application.
 * This class handles exceptions thrown by the application and provides a structured response.
 * It uses Spring's @ControllerAdvice to handle exceptions globally across all controllers.
 */
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
    logger.error("ApplicationException caught: {}", ex.getMessage(), ex);

    return ResponseEntity.internalServerError().body(
        ex.getMessage()
    );
  }

  /** ********************* **/
  /** VALIDATION EXCEPTIONS **/
  /** ********************* **/
  /**
   * Handles RequestValidationException specifically, allowing for "custom" handling of API-related errors.
   * This method will log the exception and return a specific error response.
   *
   * @param ex the RequestValidationException that was thrown
   * @return a ResponseEntity with a specific error message and HTTP status 400
   */
  @ExceptionHandler(RequestValidationException.class)
  public ResponseEntity<RequestValidationMessage> handleRequestValidationExceptionResponse(RequestValidationException ex) {
    logger.warn("RequestValidationException caught: {}", ex.getMessage(), ex);

    return ResponseEntity.badRequest().body(
        ex.getRequestValidationMessage()
    );
  }
}
