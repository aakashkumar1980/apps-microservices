# Exception Handling (REST API flow)
This project implements custom exception handling to provide clear and structured REST API error responses 
for both technical and application custom validation errors.

---
## Framework
This project uses Spring Boot's exception handling capabilities, specifically `@ControllerAdvice` 
and custom exceptions, to manage errors effectively.

### Custom Exceptions
First we define two custom exceptions that will be used to handle errors in the application. This way we can
distinguish between technical errors and application-specific validation errors.

### 1. `ApplicationException`
This is a generic exception used for technical errors that occur during the application's operation. 
It extends `RuntimeException` <sup>(as it shouldn't be caught using try/catch across all the component layers and directly 
sent to the global exception handler for processing)</sup>.

It is also annotated with `@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)` which means it will return 
a 500 Internal Server Error response when thrown.
```java
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {}
```

### 2. `RequestValidationException`
This exception is used specifically for the HTTP Request body validations or Data validations during thr REST API flow..
```java
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestValidationException extends RuntimeException {
  private final RequestValidationMessage requestValidationMessage;
  ...
}  
```

> This exception uses a custom class `RequestValidationMessage` to encapsulate validation error details, including a message and a map of error fields.
> #### `RequestValidationMessage`
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

### Exception Handler

#### `GlobalExceptionHandler`
- Uses Spring's `@ControllerAdvice` to handle exceptions globally.
- Handles both `ApplicationException` and `RequestValidationException`:
  - Logs the error.
  - Returns structured error responses.

### Usage in Service Classes

#### `CampaignCommandService`
- Throws `RequestValidationException` when a campaign is not found for update or delete operations.
- Example:
  ```java
  if (!existingCampaign.isPresent()) {
      RequestValidationMessage validationMessage = new RequestValidationMessage(
          "Api request validation failed",
          Map.of("error", String.format("Campaign with ID %s not found for update.", id))
      );
      throw new RequestValidationException(validationMessage);
  }
  ```

### Summary

- All exceptions are handled centrally by `GlobalExceptionHandler`.
- Validation errors provide detailed feedback via `RequestValidationMessage`.
- Technical errors are returned as HTTP 500 responses.

## Example Error Response

**Validation Error (HTTP 400):**
```json
{
  "message": "Api request validation failed",
  "errors": {
    "error": "Campaign with ID 123 not found for update."
  }
}
```

**Application Error (HTTP 500):**
```
Internal Server Error: <error message>
```



## Implementation
In summary, any exceptions thrown in the application's REST API flow will first have to be caught and
then converted into a structured message format, wrapped into custom exception and then thrown.

```java
try {
    // some code that may throw an exception
    ...
} catch (Exception e) {
    // convert to custom exception
    RequestValidationMessage validationMessage = new RequestValidationMessage(
        "Api request validation failed",
        Map.of("error", String.format("Campaign with ID %s not found or deletion.", id))
    );
    // throw custom exception
    throw new RequestValidationException(validationMessage);
}
```

# Exception Handling (Other flows)

