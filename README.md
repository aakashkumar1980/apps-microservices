This project implements custom exception handling to provide clear and structured error responses 
for both technical and application custom validation errors.

# Framework (One Time Setup)

## Exception Handlers
The application uses a global exception handler to catch and process these exceptions, ensuring that all errors are handled consistently.

### Gradle `build.gradle` and REST API Exceptions Handler `APIGlobalExceptionHandler`

```gradle
dependencies {
  ...
  implementation 'org.springframework.boot:spring-boot-starter-validation'  ...
}
```

```java
@ControllerAdvice
public class APIGlobalExceptionHandler {
 
  ...
  /** REST APT Http Request JSON body - format/syntax issues */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<APIRequestValidationMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {}
  
  /** REST APT Http Request JSON body - field validation issues **/
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<APIRequestValidationMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {}
}
```
<br/><br/>


# Implementation (examples)
As an example, below is the implementation of the Field Validation exception handling in a REST API service.

## Usage in Datamodel `Campaign` and controller classes `CampaignCommandController`
```java
import jakarta.validation.constraints.*;

public class Campaign {
  ...
  @NotBlank
  @Size(min = 3, max = 100)
  @JsonProperty("name")
  private String name;
  
  @NotNull
  @Future
  @JsonProperty("end_date")
  private LocalDateTime endDate;
  ...  
}  

@RestController
@RequestMapping("/api/campaigns")
public class CampaignCommandController {

  @PostMapping
  public ResponseEntity<Campaign> createCampaign(@Valid @RequestBody Campaign campaign) {}
  
  @PutMapping("/{id}")
  public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @Valid @RequestBody Campaign campaign) {}  
  ...
}


```
> JSON (output) ->
> ```json
> {
>   "message": "Api request validation failed",
>   "errors": {
>     "endDate": "must be a future date",
>     "startDate": "must be a date in the present or in the future"
>   }
> }
> ```
