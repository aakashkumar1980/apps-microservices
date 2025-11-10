# 🧑‍ ABOUT ME
Hi, I’m **ABC**. I’m a **Backend & Microservices Developer** with over **X years of experience** working on large-scale, 
microservices event-driven systems using **Java**, **Spring Boot**, **Confluent Kafka**, and **Vert.X** tech stack.

# 🌐 ABOUT PROJECT
My latest project was around the Credit Card Offers Platform, which basically manages the entire lifecycle of an offer — 
right from when it’s created by the marketing team to when the customer finally receives their reward.
![Offer Workflow](_readme_assets/offer_workflow.png)

- The lifecycle of an offer starts with **Offer Creation**, where the marketing or campaign team sets up all the rules — who’s eligible, what kind of reward it gives, when it starts and ends, and which merchants are included.
- Once the offer is created, it is reviewed and approved through the **Offer Approval & Governance Service** to ensure compliance with company policies.
- After approval, the offer goes into the **Publication phase (also called Impressions)**, where it becomes visible to customers — like on the bank’s app, website, or through push notifications.
- Then comes **Enrollment or Activation**. Some offers are auto-applied, but others require the customer to actually activate them. So we track who enrolled and when.
- Next is the **Transaction and Redemption phase**. Whenever a customer makes a purchase, those transactions flow through our backend systems. We validate whether the purchase matches any active offer — checking things like merchant code, amount, and time period. If it qualifies, we mark that offer as **redeemed**.
- After that comes **Reward Fulfillment**, where the customer actually receives their benefit — like cashback or reward points credited to their account.
- And finally, we have **Analytics and Reporting**, which helps the business understand how the offer performed — like how many people redeemed it, total spend increase, and which offers were most effective.
<br>

So overall, I’ve worked across different microservices of this lifecycle ensuring transactions are processed accurately and efficiently while maintaining **scalability** and **low latency** in the system.
> <details>
> <summary>Top 20 Microservices List (click to expand)</summary>
> 1. Campaign Management Service: Owns campaigns (goals, budgets, timelines); parent container for offers. <br><br>
> 2. Offer Authoring Service: Creates/edits offers (reward type, rates, caps, start/end, channels). <br>
> 3. Offer Approval & Governance Service: Workflow for review/approval, policy checks, versioning. <br>
> 4. Offer Publication Service: Publishes approved offers to channels/partners; manages on/off switches. <br>   
> 5. Offer Catalog Service: Read-optimized catalog of active offers by merchant/category/location. <br>  
> 6. Offer Eligibility Service: Central rules for offer-level eligibility (spend min, MCCs, channels, geo). <br><br>   
> 7. Merchant Registry Service: Merchant master data (IDs, MCCs, locations, brand hierarchies). <br>
> 8. Merchant Eligibility Service: Resolves which merchants/locations qualify for each offer. <br><br>
> 9. Customer Profile Service: Cardholder master (cards, segments, status, product tiers). <br>
> 10. Customer Eligibility Service: Determines which customers qualify (segments, status, KYC, product). <br>
> 11. Customer Enrollment Service: Manages opt\-in/opt\-out, activation windows, enrollment state. <br><br>
> 12. Transaction Ingestion Service: Consumes card transactions from network/core; normalizes, enriches. <br>
> 13. Redemption Service (triggered when a customer makes a purchase): Matches purchases to active offers; applies rule checks and caps; emits redemption results. <br>
> 14. Reward Calculation Service: Calculates cashback/points based on redemption and policy (tiers, rounding). <br>
> 15. Wallet & Ledger Service: Stores rewarded balances/points; supports statements and adjustments. <br>
> 16. Dispute & Reversal Service: Handles chargebacks/refunds; reverses redemptions/rewards when needed. <br>
> 17. Settlement & Reconciliation Service: Settles with partners/merchants; reconciles costs and reimbursements. <br><br>
> 18. Notification Service: Sends real-time confirmations (“You earned $5 cashback”), summaries. <br><br>
> 19. Analytics & Reporting Service: KPIs: activation, redemption rate, ROI, lift; dashboards and extracts. <br><br>
> 20. Partner Integration Service: Business-facing integration with aggregators (Cardlytics, Rakuten) for offer sync and status updates. <br><br>
> </details>
> <details>
> <summary>Top AI/ML Project List (click to expand)</summary> <br>
> 1. Offer Ranking & Personalization: Ranks offers per user using contextual bandits/learning-to-rank. <br>
> 2. Offer Forecasting: Predicts offer performance (redemptions, spend lift) using time-series models. <br>
> 3. Customer Eligibility Scoring: Predicts which customers are likely to be eligible for specific offers. <br>
> 4. Budget Optimization: Allocates campaign budget across segments/merchants to maximize ROI. <br>
> 5. Fraud & Abuse Detection: Flags unusual redemption patterns, manufactured spend, location anomalies. <br>
> </details>

###  🛠️ TECH STACK
Here are some of the key technologies and skills I work with:
- **Programming Languages**: Java
- **Frameworks**: Spring Boot, Vert.X
- **Messaging Systems**: Confluent Kafka
- **Resilience & Fault Tolerance**: Resilience4J (Circuit Breaker, Rate Limiter, Retry)
- **Databases**: MySQL, Couchbase (NoSQL), Redis Cache
- **Containerization & Orchestration**: Docker, Kubernetes
- **CI/CD Tools**: Jenkins, GitHub Actions
- **Monitoring & Logging**: ELK Stack
- **Security**: API Gateway, OAuth2, Okta

## APPLICATION ARCHITECTURE
The application is built on a microservices architecture comprising over 30 plus microservices, primarily developed using **Spring Boot** and **Vert.X** frameworks. 
All services are containerized with **Docker** and orchestrated across **Kubernetes** clusters, ensuring scalability and high availability.

Security is enforced at the edge through an **API Gateway**, which serves as the single entry point for all external requests. 
Authentication and authorization are handled using **OAuth2 protocol** integrated with **Okta** as the identity provider, ensuring secure access control across all microservices APIs.

The architecture implements **CQRS** (Command Query Responsibility Segregation) to optimize read and write operations separately, improving performance and scalability. 
For managing complex distributed transactions that span multiple microservices, the system employs the **SAGA** pattern, ensuring **data consistency**.

Inter-service communication follows an **event-driven architecture** pattern, leveraging **Confluent Kafka** as the central event bus for asynchronous messaging.
This design promotes loose coupling between services and enables real-time data processing across the distributed system.

To ensure system resilience and fault tolerance, the platform incorporates **Resilience4J patterns** including circuit breakers, rate limiters, and retry mechanisms. 
These patterns prevent cascading failures, handle temporary outages gracefully, and maintain service availability even when downstream dependencies experience issues.

### APPLICATION FLOW
![Application Flow](_readme_assets/application_flow.v2.png)

#### Request Initiation from Client (e.g. Cardlytics, Rakuten)
This application handles the creation and lifecycle of credit-card offers using a secure, event-driven microservices architecture. 
The process begins when a client such as Cardlytics requests an OAuth2 token from Okta to authenticate. Once the token is received, 
the client invokes the Offer Write Service API through the API Gateway. The gateway validates the token, enforces security rules, 
and forwards the request to the appropriate internal endpoint.

#### Offer Creation Process
Within the Offer Write Service, the @RestController layer exposes the /offers API, secured using @PreAuthorize with OAuth2 scopes. 
The controller delegates to a @Service class that executes multiple validations before persisting the offer.
It performs:
- Campaign validation (via the Campaign Read Service using Spring boot RestTemplate)
- Merchant validation (via the Merchant Read Service using Spring boot RestTemplate)
- Budget validation to ensure campaign funding limits

If all checks succeed, the service stores the offer in the Offer Document database and publishes a Kafka event (OFFER_CREATED) 
to notify downstream systems.

#### Event Propogation (Async Write)
The Campaign, Merchant, and Customer Write Services each consume the OFFER_CREATED Kafka event through @KafkaListener. 
They update their respective data stores—campaign, merchant, and customer documents—so that the ecosystem remains synchronized.
This event-driven model promotes loose coupling and eventual consistency across services.

#### Application Failure Handling
<b><i>Technical Failures (Retry Mechanism & Service Support)</i></b>
To handle technical failures during event processing, each microservice implements retry logic using Resilience4J. 
If a service fails to process an event (e.g., due to a temporary database outage), Resilience4J automatically retries the operation based on a configured policy. 
If retries are exhausted without success, the failed event is logged to a dead-letter topic in Kafka for later analysis and manual intervention.

<b><i>Business Failures (SAGA :: Compensating Transactions)</i></b>
In case a downstream update fails (for example, the Campaign Write Service cannot update its document), the SAGA pattern initiates a compensating transaction.
The failing service triggers the Offer Write Service compensation endpoint (cancel/$offerId). This service retrieves the offer record from its compensation database, 
cancels it, and publishes a Kafka OFFER_CANCELLED event. The Merchant Write Service and Customer Write Service (Compensation) components listen for this event to 
revert any prior updates in their own stores, ensuring data consistency across the distributed system. 

> NOTE: Service to Service communication can be done via REST API calls (synchronous) or Kafka events (asynchronous) based on the use case requirements.<br>
For example generally for <u>Read operations REST API (via. RestTemplate)</u> calls are preferred, whereas for <u>Write operations Kafka</u> events are used for better scalability and decoupling.

<br><br>
# 🏆 MY WORK AND ACHIEVEMENTS
<b>Performance Optimization</b><br>
Apart from designing the Offer Write Service and implementing the SAGA-based rollback flow, my major focus was on improving system 
performance, reliability, and maintainability. One of my biggest achievements was optimizing the asynchronous processing pipeline 
using Vert.x Futures. This reduced thread contention and improved offer ingestion throughput by nearly 25% 
under load.
<details>
<summary>Spring Boot vs Vert.X (click to expand)</summary>

![_SpringBoot](_readme_assets/springboot_flow.png)
![_VertX](_readme_assets/vertx_flow.png)

<b>Vert.x vs Spring Boot: The Core Advantage</b> The fundamental difference is in threading models: <br>
<b>Spring Boot</b> uses blocking threads where each request occupies a thread (~200 total default) that remains blocked during I/O operations 
like database queries, limiting concurrency to ~200-400 users.<br> 
<b>Vert.x</b> uses non-blocking Event Loops with callbacks—just 8 Event Loop threads handle requests and immediately delegate blocking 
operations to a separate Worker pool while registering callbacks, becoming free in milliseconds to handle thousands more requests. 
When blocking operations complete, callbacks fire on Event Loops to send responses. This means while Spring Boot's threads waste 
time waiting, Vert.x's Event Loops stay 100% free, enabling 10,000+ concurrent connections with 10-100x better throughput 
for I/O-intensive applications.

NOTE: Ideally, for a high CPU bound processing go for Spark like frameworks, whereas for high I/O bound processing go for Vert.X like frameworks.
Spring Boot is more suited for traditional monolithic applications with moderate I/O load.

<b>Optimized Configurations (mid I/O workload)</b><br>
TOMCAT:<br>
&nbsp;&nbsp;<b>server.tomcat.threads.max= <i>N x (1 + W/C)</i></b><br>
&nbsp;&nbsp;&nbsp;&nbsp;<i>e.g. 4x(1+180ms/20ms)-> <b>40</b>, default is 200</i> (max concurrent users: 40)

VERT.X:<br>
&nbsp;&nbsp;<b>VertxOptions().setEventLoopPoolSize(<i>Nx2</i>)</b>, <i>(e.g. 4x2-> <b>8</b>)</i><br>

&nbsp;&nbsp;X% Blocking, (100-X)% Non-Blocking<br>
&nbsp;&nbsp;<b>VertxOptions().setWorkerPoolSize(<i>N x (1 + ((X/100)W)/C))</i>)</b>,<br>
&nbsp;&nbsp;&nbsp;&nbsp;<i><font color=green>e.g. for <b>*20% blocking</b> -> 4x(1+(20/100x180ms/20ms))-><b>12</b> for latest async libraries</i></font> (max concurrent users: 400)<br>
&nbsp;&nbsp;&nbsp;&nbsp;<i>e.g. for <b>60% blocking</b> -> 4x(1+(60/100x180ms/20ms))-><b>26</b> for legacy blocking libraries</i> (max concurrent users: 80)<br>

where,<br>
&nbsp;&nbsp;N=number of CPU cores <i>(e.g. 4)</i>,<br>
&nbsp;&nbsp;W=i/o average wait time <i>(e.g. 180ms)</i>,<br>
&nbsp;&nbsp;C=average compute time <i>(e.g. 20ms)</i><br>

---
</details>

<details>
<summary>Vert.X Concepts (click to expand)</summary>

Define your custom functions in an asynchronous way using Vert.X Future API to avoid blocking the Event Loop threads.
```java
  /** Immediate Future using "Future.succeededFuture()" **/
  public Future<String> toUpperCase(String input) {
    return Future.succeededFuture(input.toUpperCase());
  }
  public Future<String> toLowerCase(String input) {
    return Future.succeededFuture(input.toLowerCase());
  }  
  
  /** Later Future using "promise.future()" **/
  public Future<User> fetchUserFromDatabase(String userId) {
    Promise<User> promise = Promise.promise();
    
    // async operation - result comes later
    mongoClient.findOne("users", new JsonObject().put("id", userId), null)
    .onSuccess(json -> {
      User user = new User(json);
      promise.complete(user);  // Complete when ready
    })
    .onFailure(err -> {
      promise.fail(err);  // Fail if error
    });
    
    return promise.future();
  }
  
  public Future<String> callExternalAPI(String endpoint) {
    Promise<String> promise = Promise.promise();
    
    // async operation - result comes later
    webClient.get(443, "api.example.com", endpoint)
    .ssl(true)
    .send()
    .onSuccess(response -> {
      promise.complete(response.bodyAsString());
    })
    .onFailure(err -> {
      promise.fail(err);
    });
    
    return promise.future();
  }  
```

USAGE
- Single Call to the async function (using <b>onSuccess</b>/<b>onFailure</b>)
  ```java
    toUpperCase("hello")
      .onSuccess(result -> {
        System.out.println("Uppercase: " + result);
      })
      .onFailure(err -> {
        System.err.println("Error: " + err.getMessage());
      });
  ```

- Multiple calls to the multiple async functions
  - Series (using <b>compose</b>)
    ```java
      toUpperCase("hello")
        .compose(upper -> toLowerCase(upper))
    
        .onSuccess(result -> {
          System.out.println("Final Result: " + result);
        })
        .onFailure(err -> {
          System.err.println("Error: " + err.getMessage());
        });
    ```
  - Parallel (using <b>CompositeFuture.all</b>)
    ```java
      CompositeFuture.all(
        toUpperCase("hello"),
        toLowerCase("WORLD")
      ).onSuccess(composite -> {
        String upper = composite.resultAt(0);
        String lower = composite.resultAt(1);
        System.out.println("Uppercase: " + upper);
        System.out.println("Lowercase: " + lower);
      }).onFailure(err -> {
        System.err.println("Error: " + err.getMessage());
      });
    ```


</details>

<b>Kafka Configuration Tuning</b><br>
I also tuned Kafka producer and consumer configurations, like batch size and linger settings, which reduced event latency across services.

<details>
<summary>Kafka (click to expand)</summary>

<b>Kafka Architecture - Brokers, Topics, and Partitions</b>
![_KafkaPODs](_readme_assets/kafka_pods.png)
Kafka runs on multiple servers called brokers (like distributed post offices). Messages are organized into **topics** (e.g., "offers-topic"), 
and each topic is split into **partitions** (numbered queues: P0, P1, P2) distributed across brokers for parallel processing. Consider topic as
a bucket and partitions as sub-buckets within it.<br>

<b>Publishing an Event</b><br>
When Offer Service publishes an "OfferCreated" event <i>{offerId: "123", discount: 20%}</i>, distributes either distributes it across partitions 
using a key (e.g., offerId) or round-robin if no key is provided. The messages are stored in the partitions in the order they arrive like 
in an arraylist. just like arraylist have an index, each message in a partition has an **offset** (0, 1, 2...) that uniquely identifies its position.
Below is the code snippet to publish an event using Spring KafkaTemplate.
```java
  /** Publisher Code: **/
  // Publish OfferCreated event to "offers-topic", first argument is the topic name, second is the key, third is the message payload
  kafkaTemplate.send("offers-topic", offerId, offerCreatedEventJson);
```

```properties
### Producer Configuration: ###
# === Connection ===
bootstrap.servers=$broker1:9092,$broker2:9092,$broker3:9092,...

# === Serialization ===
key.serializer=org.apache.kafka.common.serialization.StringSerializer
value.serializer=org.apache.kafka.common.serialization.StringSerializer

# === Durability (No Message Loss) ===
acks=all                           # Wait for all replicas (leader + followers). This ensures no data loss.
retries=2147483647                 # Retry until success (max int)
enable.idempotence=true            # Kafka-level deduplication (avoid duplicate messages) within producer session.
max.in.flight.requests.per.connection=5  # Can be >1 with idempotence enabled. This improves throughput by allowing multiple requests in flight.

# === High Throughput ===
batch.size=32768                   # 32 KB batches (larger = better throughput)
linger.ms=10                       # Wait 10ms to batch more messages
compression.type=lz4               # Fast compression (snappy or zstd also good)
buffer.memory=67108864             # 64 MB buffer for batching

# === Timeout ===
request.timeout.ms=30000           # 30 seconds
delivery.timeout.ms=120000         # 2 minutes total (includes retries)
```

<b>Consuming Events</b><br>
<i>Across different services (Share Message)</i>:<br>
Consumers (e.g., Merchant Service, Customer Service) subscribe to topics and read messages from partitions. Same messages can be consumed by 
multiple services independently by using it's own **consumer group** names (i.e. MerchantServiceGroup, CustomerServiceGroup). Each consumer 
tracks its own offsets per partition, so it knows which messages it has already processed. When a consumer reads a message, it can **commit** 
the offset to Kafka, so that if it restarts, it can resume from the last committed offset instead of re-reading all messages. The commit is either automatic 
(at intervals) or manual (after processing using the code).

<i>Across multiple instances (e.g. Docker PODs) of the same service (Scaling Messages)</i>:<br>
If there are multiple instances of Merchant Service running (e.g., for load balancing), Kafka distributes partitions among them. so that 
each instance processes a subset of partitions. For example,
- Instance (POD 1) reads from Partition 0 and 1
- Instance (POD 2) reads from Partition 2 and 3
- Instance (POD 3) reads from Partition 4 and 5 etc.
This way, Kafka ensures high throughput and fault tolerance by distributing messages across brokers and partitions, while allowing multiple 
services and instances to consume events independently.
```java
  /** Consumer Code: **/
  @KafkaListener(topics = "offers-topic", groupId = "MerchantServiceGroup")
  public void listen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
      // Process the message
      System.out.println("Received message: " + record.value());
      
      // Manually commit offset after processing
      acknowledgment.acknowledge();
  }
```

```properties
### Consumer Configuration: ###
# === Connection ===
bootstrap.servers=$broker1:9092,$broker2:9092,$broker3:9092,...

# === Consumer Group (Critical!) ===
group.id=MerchantServiceGroup    # Same group = load balancing across PODs, Different groups = each gets copy

# === Deserialization ===
key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
value.deserializer=org.apache.kafka.common.serialization.StringDeserializer

# === At-Least-Once Delivery (Critical!) ===
enable.auto.commit=false           # MUST be false for at-least-once delivery. Manual commit after processing in the code.
auto.offset.reset=latest           # Start from latest if no prior offset found.

# === High Throughput ===
max.poll.records=500               # Process 500 messages per poll
fetch.min.bytes=50000              # Wait for 50 KB before returning (throughput)
fetch.max.wait.ms=500              # Max 500ms wait (balance throughput vs latency)

# === Consumer Liveness ===
session.timeout.ms=30000           # 30 seconds before consumer kicked out
heartbeat.interval.ms=3000         # Heartbeat every 3 seconds
max.poll.interval.ms=300000        # 5 minutes max processing time per poll

# === Partition Assignment ===
partition.assignment.strategy=org.apache.kafka.clients.consumer.RangeAssignor
```

</details>



<b>Validation and Exception Handling</b><br>
From a development perspective, I implemented a robust validation layer for campaign and merchant lookups, ensuring early failure detection 
before committing data to Couchbase. This cut downstream rollback events by almost 30%. I also standardized exception handling using @ControllerAdvice, 
which simplified debugging and improved error observability.

<details>
<summary>Validation & Exception Handling (click to expand)</summary>

<b>REST API request's json payload validations</b>
1. First define the DTO with validation annotations as per the business rules or the agreed schema.
```java
  import javax.validation.constraints.*;
  
  public class OfferRequestDto {
    @NotNull(message = "Offer name cannot be null")
    @Size(min = 5, max = 100, message = "Offer name must be between 5 and 100 characters")
    private String offerName;
    
    @NotNull(message = "Start date cannot be null")
    @Future(message = "Start date must be in the future")
    private LocalDate startDate;
    
    @NotNull(message = "End date cannot be null")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;
    
    @Min(value = 1, message = "Discount percentage must be at least 1%")
    @Max(value = 100, message = "Discount percentage cannot exceed 100%")
    private Integer discountPercentage;
      
    // Getters and Setters
  }
```

2. Next, simply add @Valid annotation to the controller method parameter to trigger validation.
```java
  @RestController
  @RequestMapping("/offers")
  public class OfferController {
  
    @PostMapping
    public ResponseEntity<String> createOffer(@Valid @RequestBody OfferRequestDto offerRequest) {
        // If validation passes, proceed with business logic
        return ResponseEntity.ok("Offer created successfully");
    }
  }
```

<b>Global Exception Handling</b>
1. First define two categories of custom exceptions - Functional (business rule violations 4xx) and Technical (system failures 500).
```java
  // Functional Exception
  public class FunctionalException extends RuntimeException {
      public FunctionalException(String message) {
          super(message);
      }
  }
  
  // Technical Exception
  public class TechnicalException extends RuntimeException {
      public TechnicalException(String message, Throwable cause) {
          super(message, cause);
      }
  }
```

2. Next, create a global exception handler using @ControllerAdvice to catch and respond appropriately.
```java
  @ControllerAdvice
  public class GlobalExceptionHandler {
  
      @ExceptionHandler(FunctionalException.class)
      public ResponseEntity<String> handleFunctionalException(FunctionalException ex) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
      }
  
      @ExceptionHandler(TechnicalException.class)
      public ResponseEntity<String> handleTechnicalException(TechnicalException ex) {
          // Log the technical error for debugging
          log.error("Technical error occurred", ex);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("An internal error occurred. Please try again later.");
      }
  }
```
</details>


<b>Java Streams Optimization</b><br>
I also optimized several data processing modules using Java Streams to replace complex nested loops with clean, declarative pipelines.
By leveraging operations like filter, map, groupingBy, and parallel streams, I improved both readability and performance of offer validation
and enrichment flows.

<details>
<summary>Java Streams (click to expand)</summary>

1. Using **filter()** to filter active offers based on multiple criteria
```java
/** This filter() example filters a list of offers based on multiple criteria such as merchant applicability, 
  date range, minimum spend, and channel match. It is a direct replacement of multiple if/else conditions looping 
  through the list. **/

List<Offer> matchedOffers = offersList.stream()
    .filter(offer -> offer.isApplicableToMerchant(transaction.getMerchantId()))
    .filter(offer -> offer.isWithinDateRange(transaction.getTimestamp()))
    .filter(offer -> transaction.getAmount().compareTo(offer.getMinSpend()) >= 0)
    .filter(offer -> channelMatches(offer, transaction.getChannel()))
    .collect(Collectors.toList());
```

2. Using **groupingBy()** to group collections by a key and run aggregations. It is similar to SQL GROUP BY clause.

Consider a table of employees with columns: departmentId, salary. Below is the sample table data.
Employee Table:
| employeeId | employeeName | departmentName | salary  |
|------------|--------------|----------------|---------|
| 1          | John Doe     | Sales          | 60000   |
| 2          | Jane Smith   | Sales          | 65000   |
| 3          | Bob Johnson  | Engineering    | 80000   |
| 4          | Alice Brown  | Engineering    | 85000   |
| 5          | Charlie Davis| Engineering    | 55000   |

Result after grouping by departmentId and summing salaries:
| departmentId | totalSalary |
|--------------|-------------|
| Sales        | 125000      |
| Engineering  | 220000      |


In SQL, you would use GROUP BY to aggregate salaries by departmentId like this:
```sqlpp
SELECT 
  departmentId, 
  SUM(salary) as totalSalary
FROM employees
GROUP BY departmentId;
```

In Java Streams, you can achieve the same result using Collectors.groupingBy() as shown below:
```java
  /** The Collectors.groupingBy() example groups a list of Employee objects by their departmentId and calculates the total salary for each department. 
    The first argument to groupingBy() is the key extractor function (Employee::getDepartmentId), 
    and the second argument is a downstream collector (Collectors.summingDouble(Employee::getSalary))
  **/
  Map<String, Double> totalSalaryByDepartment = employeeList.stream()
      .collect(Collectors.groupingBy(
          Employee::getDepartmentId,
          Collectors.summingDouble(Employee::getSalary)
      ));
```
</details>

<b>Security and Reliability Enhancements</b><br>
Security and reliability were other areas I strengthened. I integrated Okta-based OAuth2 across all partner APIs, enforcing granular scopes 
for each operation. Combined with Resilience4j circuit breakers and retry mechanisms, this made our services resilient to network fluctuations 
and partner outages.

<details>
<summary>Retry & Resilience4J (click to expand)</summary>

<b>Retry Example</b><br>
We will use Spring Boot's @Retry to simply try to call internal services which are within the company network e.g. database calls, internal REST API calls etc.
The reason being that internal services are generally more reliable and have lower latency, so a simple retry mechanism is sufficient to handle transient failures.
```java
  import org.springframework.retry.annotation.Backoff;
  import org.springframework.retry.annotation.Retryable;
  import org.springframework.stereotype.Service;

  @Service
  public class InternalServiceClient {

    @Retryable(
      value = { InternalServiceException.class },       // Retry on this exception
      maxAttempts = 5,                                  // Max 5 attempts
      backoff = @Backoff(delay = 2000, multiplier = 2)  // Exponential backoff starting at 2s
    )
    public String callInternalService(String request) {
      // Code to call internal service (e.g., REST API, database)
      // If it fails, throw InternalServiceException to trigger retry
    }
  }
```

<b>Resilience4J Circuit Breaker Example</b><br>
For external services (e.g., third-party APIs), we will use Resilience4J Circuit Breaker to prevent overwhelming the external service 
during outages or high latency periods. This protects our system from cascading failures and allows it to degrade gracefully.

Concept of Circuit Breaker:
- Closed State: Normal operation, calls go through.
- Open State: After a threshold of failures, the circuit opens, and calls are blocked for a timeout period.
- Half-Open State: After the timeout, a limited number of test calls are allowed to check if the external service has recovered.

```java
  import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
  import org.springframework.stereotype.Service; 
   
  @Service
  public class ExternalServiceClient {
    @CircuitBreaker(name = "externalServiceCircuitBreaker", fallbackMethod = "fallbackResponse")
    public String callExternalService(String request) {
      // Code to call external service (e.g., third-party REST API)
      // If it fails, Circuit Breaker will open after threshold is reached
    }

    // Fallback method when Circuit Breaker is open
    public String fallbackResponse(String request, Throwable t) {
      return "Default response due to external service failure";
    }
  }
```
</details>

<b>CI/CD and Monitoring Improvements</b><br>
On the delivery side, I led the CI/CD automation using GitHub Actions, Hydra, and XLR pipelines, bringing deployment time down by roughly 40% 
while maintaining zero downtime releases on AWS EKS. I also introduced proactive CloudWatch and ELK monitoring dashboards, which reduced 
incident resolution time by over half.

<b>SAGA Compensation Flow Enhancements</b><br>
Finally, I solved several cross-service consistency issues by improving our compensation event flow. I identified race conditions between offer 
creation and campaign updates and refactored the compensation listeners to handle idempotent rollbacks. This made our SAGA implementation 
more stable and reliable in production.

Overall, I’d say my biggest contributions were improving system throughput, operational resilience, and observability — turning what was initially 
a tightly coupled offer system into a high-performing, self-healing microservices ecosystem.

