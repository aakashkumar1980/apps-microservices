package com.example.tutorial.common.exceptions.api.handler;

import com.example.tutorial.common.exceptions.api.APIRequestValidationException;
import com.example.tutorial.common.exceptions.api.APIRequestValidationMessage;
import com.example.tutorial.common.exceptions.ApplicationTechnicalException;
import com.example.tutorial.common.exceptions.api.APIRequestVersionConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
   * <REST API :: HTTP request JSON body - format/syntax issues>
   * This typically happens when the JSON format is incorrect or required fields are missing.
   *
   * @param ex the HttpMessageNotReadableException that was thrown
   * @return a ResponseEntity with a structured validation message and HTTP status 400
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<APIRequestValidationMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    logger.error("HttpMessageNotReadableException caught: {}", ex.getMessage(), ex);

    return ResponseEntity.badRequest().body(
        new APIRequestValidationMessage(
            "Request JSON body is not as per the expected format",
            Map.of("error", ex.getMessage())
        )
    );
  }

  /**
   * <REST API :: HTTP request JSON body - field validation issues>
   * Handles validation errors that occur when request body is of correct format but validation rules fails.
   * This method will extract the validation errors and return them in a structured format.
   *
   * @param ex the MethodArgumentNotValidException that was thrown
   * @return a ResponseEntity with a structured validation message and HTTP status 400
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<APIRequestValidationMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    logger.error("MethodArgumentNotValidException caught: {}", ex.getMessage(), ex);

    Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
        .collect(
            Collectors.toMap(
                error -> error.getField(),
                error -> error.getDefaultMessage(),
                (msg1, msg2) -> msg1 // handle duplicate keys
            )
        );

    return ResponseEntity.badRequest().body(
        new APIRequestValidationMessage(
            "Api request validation failed",
            errors
        )
    );
  }


  /**
   * <REST API :: Http request JSON body - Data validation issues>
   * Handles APIRequestValidationException specifically, allowing for "custom" handling of data validation errors.
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

  /**
   * <REST API :: Http request JSON body - Version conflict issues>
   * Handles APIRequestVersionConflictException specifically, allowing for "custom" handling of version conflict errors.
   * This method will log the exception and return a specific error response.
   *
   * @param ex the APIRequestVersionConflictException that was thrown
   * @return a ResponseEntity with a specific error message and HTTP status 409
   */
  @ExceptionHandler(APIRequestVersionConflictException.class)
  public ResponseEntity<APIRequestValidationMessage> handleAPIRequestVersionConflictException(APIRequestVersionConflictException ex) {
    logger.warn("APIRequestVersionConflictException caught: {}", ex.getMessage(), ex);

    return ResponseEntity.status(HttpStatus.CONFLICT).body(
        ex.getRequestValidationMessage()
    );
  }
}
