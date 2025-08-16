package com.example.tutorial.common.exceptions.api.handler;

import com.example.tutorial.common.exceptions.api.APIRequestValidationException;
import com.example.tutorial.common.exceptions.api.APIRequestValidationMessage;
import com.example.tutorial.common.exceptions.ApplicationTechnicalException;
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
public class APIGlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(APIGlobalExceptionHandler.class);

  /**
   * Handles all exceptions that are not specifically handled by other exception handlers.
   * This method will log the exception and return a generic error response.
   *
   * @param ex the exception that was thrown
   * @return a ResponseEntity with a generic error message and HTTP status 500
   */
  @ExceptionHandler(ApplicationTechnicalException.class)
  public ResponseEntity<String> handleApplicationTechnicalException(ApplicationTechnicalException ex) {
    logger.error("ApplicationTechnicalException caught: {}", ex.getMessage(), ex);

    return ResponseEntity.internalServerError().body(
        ex.getMessage()
    );
  }

  /** ********************* **/
  /** VALIDATION EXCEPTIONS **/
  /** ********************* **/
  /**
   * Handles APIRequestValidationException specifically, allowing for "custom" handling of API-related errors.
   * This method will log the exception and return a specific error response.
   *
   * @param ex the APIRequestValidationException that was thrown
   * @return a ResponseEntity with a specific error message and HTTP status 400
   */
  @ExceptionHandler(APIRequestValidationException.class)
  public ResponseEntity<APIRequestValidationMessage> handleAPIRequestValidationException(APIRequestValidationException ex) {
    logger.warn("APIRequestValidationException caught: {}", ex.getMessage(), ex);

    return ResponseEntity.badRequest().body(
        ex.getRequestValidationMessage()
    );
  }
}
