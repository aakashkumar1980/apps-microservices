This project implements custom exception handling to provide clear and structured error responses 
for both technical and application custom validation errors.

# Framework (One Time Setup)
## Custom Exceptions
First we define following custom exceptions that will be used to handle errors in the application. This way we can
distinguish between technical errors and application-specific validation errors.

### Generic Exceptions
All exception extends `RuntimeException` <sup>(as it shouldn't be caught using try/catch across all the component layers and directly
sent to the global exception handler for processing)</sup>.

#### `ApplicationTechnicalException`
This is used for technical errors that occur during the application's operation. 
This can be used to handle checked exceptions like `SQLException`, `IOException`, etc., or any other unexpected runtime exceptions that are not related to user input validation.
```java
public class ApplicationTechnicalException extends RuntimeException {}
```
<br/>

#### `ApplicationFunctionalException`
This is used for functional errors that occur during the application's operation, such as business logic violations.
```java
public class ApplicationFunctionalException extends RuntimeException {}
```
<br/>

### REST API Exceptions
#### `RequestValidationException` <sup>(extends ApplicationFunctionalException)</sup>
This exception is used specifically for the HTTP Request body validations or Data validations during thr REST API flow.

It is annotated with `@ResponseStatus(HttpStatus.BAD_REQUEST)` which means it will return a 400 Bad Request Error response when thrown.
```java
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestValidationException extends RuntimeException {
  private final RequestValidationMessage APIRequestValidationMessage;
  ...
}  
```

This exception uses a custom class `RequestValidationMessage` to encapsulate validation error details, including a message and a map of error fields.
> `RequestValidationMessage`
> ```java
> public class RequestValidationMessage {
>   private String message;
>   private Map<String, String> errors;
>   ...
> }
> ```
> JSON (output) ->
> ```json
> {
>  "message": "Api request validation failed",
>  "errors": {
>    "endDate": "must be a future date",
>    "offerIds": "must not be empty",
>    "startDate": "must be a date in the present or in the future"
>  }
>}
>```
<br/>

## Exception Handlers
The application uses a global exception handler to catch and process these exceptions, ensuring that all errors are handled consistently.

### Generic Exceptions Handler
There is no handler for generic exceptions in spring boot, and depending upon the module, custom handlers should be implemented.

### REST API Exceptions Handler `APIGlobalExceptionHandler`
For the REST API process, spring boot provides a way to handle exceptions globally using `@ControllerAdvice`.

```java
@ControllerAdvice
public class APIGlobalExceptionHandler {
 
  /** For handling technical exceptions during the REST API flow */
  @ExceptionHandler(ApplicationTechnicalException.class)
  public ResponseEntity<String> handleApplicationException(ApplicationTechnicalException ex) {}
  
  /** For handling Data validation exceptions during the REST API flow */
  @ExceptionHandler(APIRequestValidationException.class)
  public ResponseEntity<APIRequestValidationMessage> handleAPIRequestValidationException(APIRequestValidationException ex) {}
}
```
<br/><br/>


# Implementation (examples)
As an example, below is the implementation of the Data Validation exception handling in a REST API service.

## Usage in Service Classes `CampaignCommandService`
```java
public Optional<Campaign> updateCampaign(Long id, Campaign campaign) {
  ...
  APIRequestValidationMessage validationMessage = new APIRequestValidationMessage(
      "Api request validation failed",
      Map.of("error", String.format("Campaign with ID %s not found for update.,", id))
  );
  throw new APIRequestValidationException(validationMessage);
}  
```

> JSON (output) ->
> ```json
> {
>  "message": "Api request validation failed",
>  "errors": {
>    "error": "Campaign with ID 123 not found for update."
>  }
>}
> ```
