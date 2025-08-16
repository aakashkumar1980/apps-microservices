package com.example.tutorial.common.exceptions.handler;

import com.example.tutorial.common.exceptions.ApplicationException;
import com.example.tutorial.common.exceptions.RequestValidationException;
import com.example.tutorial.common.exceptions.RequestValidationMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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
   * <HTTP REQUEST BODY VALIDATION :: JSON body Format Issues>
   * This typically happens when the JSON format is incorrect or required fields are missing.
   *
   * @param ex the HttpMessageNotReadableException that was thrown
   * @return a ResponseEntity with a structured validation message and HTTP status 400
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<RequestValidationMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    logger.error("HttpMessageNotReadableException caught: {}", ex.getMessage(), ex);

    return ResponseEntity.badRequest().body(
      new RequestValidationMessage(
        "Request JSON body is not as per the expected format",
        Map.of("error", ex.getMessage())
      )
    );
  }

  /**
   * <HTTP REQUEST BODY VALIDATION :: JSON Validation Issues>
   * Handles validation errors that occur when request body is of correct format but validation rules fails.
   * This method will extract the validation errors and return them in a structured format.
   *
   * @param ex the MethodArgumentNotValidException that was thrown
   * @return a ResponseEntity with a structured validation message and HTTP status 400
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RequestValidationMessage> handleValidationErrors(MethodArgumentNotValidException ex) {
    logger.error("MethodArgumentNotValidException caught: {}", ex.getMessage(), ex);

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
    );

    return ResponseEntity.badRequest().body(
      new RequestValidationMessage(
        "Api request validation failed",
        errors
      )
    );
  }

  /**
   * <APPLICATION CUSTOM EXCEPTION>
   * Handles custom request validation exceptions.
   * This method will return the validation message provided in the exception.
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
