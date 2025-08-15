# Spring Framework DI (Dependency Injection) and IOC (Inversion of Control).

## Normal Java Code
  ```java
  public class Campaign {
      public Campaign() {
      }
  }
  
  public class Offer {
      public Campaign campaign;
      public Offer(public Campaign campaign) {
          this.campaign = campaign;
      }
  }
  ```
  Example of creating objects.
  - using constructor
    ```java
    Campaign campaign = new Campaign(); 
    Offer offer = new Offer(campaign);
    ```

## Spring Framework DI (Dependency Injection) and IOC (Inversion of Control)
  ```java
  @Component
  public class Campaign {
      public Campaign() {
      }
  }
  
  @Component
  public class Offer {
      private final Campaign campaign;
      
      @Autowired // Spring will inject the Campaign bean here
      public Offer(Campaign campaign) {
          this.campaign = campaign;
      }
  }
  ```

  Example of creating objects.
  - using @Autowired annotation
    ```java
    # using @Autowired annotation
    
    @Autowired
    private Campaign campaign;
    
    @Autowired
    private Campaign campaign;  
    ```

  - using `ApplicationContext` to get beans
    ```java
    @Autowired
    private ApplicationContext applicationContext;
    
    Campaign campaign = applicationContext.getBean(Campaign.class); 
    Offer offer = applicationContext.getBean(Offer.class);
    ```
NOTE: 
- IOC is a design principle where the control of object creation and management is inverted from the application code to a framework (like Spring). This allows for better separation of concerns, easier testing, and more flexible code.
- Dependency Injection (DI) is a specific implementation of IOC where dependencies are provided to a class rather than the class creating them itself. In Spring, this is typically done using annotations like `@Autowired`.
- Scopes in Spring define the lifecycle of beans. Common scopes include:
  - `singleton`: One instance per Spring container (default).<br>
    ```java
    Campaign campaign = applicationContext.getBean(Campaign.class); 
    # here, the same instance of Campaign is returned every time.
    ```
  - `prototype`: A new instance every time requested.<br/>
    for this scope, you can use `@Scope("prototype")` annotation e.g.
      ```java
      @Component
      @Scope("prototype")
      public class Campaign {
          public Campaign() {
          }
      }
    
      @Autowired
      private ApplicationContext applicationContext;
    
      Campaign campaign1 = applicationContext.getBean(Campaign.class);
      Campaign campaign2 = applicationContext.getBean(Campaign.class);
      ...
      # here, each call to getBean() returns a new instance of Campaign.
        NOTE: @Autowired cannot be used with prototype scope directly, as it will always inject the same instance.
      ```
  - `request`: One instance per HTTP request (for web applications).
  - `session`: One instance per HTTP session (for web applications).
  - `application`: One instance per ServletContext (for web applications).
<br/><br/>


# Spring Boot REST API Microservice Tutorial
This project demonstrates how to build a REST API microservice from scratch using Spring Boot. The API manages campaigns and follows a layered architecture with clear separation of concerns.

## Steps to Create the REST API
1. **Initialize the Spring Boot Project**
   - Create gradle files and setup base project structure like 
     - <project_folder>/src/main/java
     - <project_folder>/src/main/resources
     <br/><br/>
     - <project_folder>/src/test/java
     - <project_folder>/src/test/resources
     <br/><br/>
     - <project_folder>/build.gradle
     - <project_folder>/gradle.properties
     - <project_folder>/settings.gradle
   - Add dependencies for `spring-boot-starter-web` and any utilities (e.g., Apache Commons) in the `build.gradle` file.

2. **Define the Data Model**
   - Create classes (e.g., `Campaign`) to represent your domain objects.
   - Example:
     ```java
     public class Campaign {
       private Long id;
       private String name;
       // ...other fields...
       // getters and setters
     }
     ```

3. **Implement the Service Layer**
   - Two service classes for business logic:
     - **CampaignCommandService**: Handles write operations (Create, Update, Delete).
     - **CampaignQueryService**: Handles read operations (Retrieve, List).
     
   - **Annotation Explanations:**
     `@Service`: Marks the class as a Spring service component. Same as <i>@Component</i>, but specifically indicates that the class provides business logic.
     ```java
     @Service
     public class CampaignCommandService {
       public Long createCampaign(Campaign campaign) {
         ...
         return campaign.getId();
       }
     }
     ```

4. **Create Controller Classes**
   - The API uses two controllers to separate read and write operations:
     - **CampaignCommandController**: Handles write operations (Create, Update, Delete).
     - **CampaignQueryController**: Handles read operations (Retrieve/List).

   - **Annotation Explanations:**<br/>  
     class-level annotations:
     - `@RestController`: Marks the class as a REST controller. 
        NOTE: It is a combination of <i>@Controller</i> and <i>@ResponseBody</i> annotations. These annotations indicate that the class handles HTTP requests and responses, and the response body will be serialized to JSON or XML.
     - `@RequestMapping`: Sets the base URL for all endpoints in the controller. e.g. "/api/campaigns".<br/><br/>
     ```java
         @RestController
         @RequestMapping("/api/campaigns")
         public class CampaignCommandController {}
     ``` 

     method-level annotations:
     - `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` : Map HTTP methods to handler methods. <br/>

     argument-level annotations (REST API inputs):
     - `@RequestBody`: Binds the HTTP request body to a method parameter (used for JSON payloads).
     - `@PathVariable`: Binds a URI template variable to a method parameter. (e.g. `/api/campaigns/{id}` where `{id}` is a path variable).
     - `@RequestParam`: Binds a query parameter to a method parameter (e.g. `/api/campaigns?status=active` where `status` is a query parameter).<br/><br/>

     ```java
         GET /api/campaigns
         @GetMapping 
         public ResponseEntity<List<Campaign>> getAllCampaigns() {}
         
     ```

5. **Configure Persistence (Mock or Real DB)**
   - For demonstration, use mock data utilities. For production, integrate with a database (e.g., Couchbase).

6. **Test the API**
   - Use tools like Postman or curl to test endpoints for creating, updating, retrieving, and deleting campaigns.

## Example Endpoints
- `POST /api/campaigns` — Create a campaign
- `GET /api/campaigns` �� List all campaigns
- `GET /api/campaigns/{id}` — Get campaign by ID
- `PUT /api/campaigns/{id}` — Update campaign
- `DELETE /api/campaigns/{id}` — Delete campaign

## How to Run
1. Build the project:  
   `./gradlew build`
2. Start the application:  
   `./gradlew bootRun`
3. Test endpoints using Postman or curl.

