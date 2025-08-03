package com.example.tutorial.common.exceptions.handler;

import com.example.tutorial.common.exceptions.ApplicationException;
import com.example.tutorial.common.exceptions.RequestValidationException;
import com.example.tutorial.common.exceptions.RequestValidationMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

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

  /** ********************* **/
  /** VALIDATION EXCEPTIONS **/
  /** ********************* **/
  /**
   * Handles when the request body is not readable.
   * This typically happens when the JSON format is incorrect or required fields are missing.
   *
   * @param ex the HttpMessageNotReadableException that was thrown
   * @return a ResponseEntity with an error message and HTTP status 400
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<RequestValidationMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    logger.warn("HttpMessageNotReadableException caught: {}", ex.getMessage(), ex);

    RequestValidationMessage validationMessage =
        new RequestValidationMessage("Request body is not readable or is malformed", Map.of("error", ex.getMessage()));
    return ResponseEntity.badRequest().body(validationMessage);
  }

  /**
   * Handles validation errors that occur when request body is of correct format but validation rules fails.
   * This method will extract the "javax validation errors" and return them in a structured format.
   *
   * @param ex the MethodArgumentNotValidException that was thrown
   * @return a ResponseEntity with validation error messages and HTTP status 400
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RequestValidationMessage> handleValidationErrors(MethodArgumentNotValidException ex) {
    logger.warn("MethodArgumentNotValidException caught: {}", ex.getMessage(), ex);

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage()));

    RequestValidationMessage validationMessage =
        new RequestValidationMessage("Api request validation failed", errors);
    return ResponseEntity.badRequest().body(validationMessage);
  }

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
