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

#### Request Initiation from Client
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

> NOTE: Service to Service communication can be done via REST API calls (synchronous) or Kafka events (asynchronous) based on the use case requirements.
For example generally for Read operations REST API calls are preferred, whereas for Write operations Kafka events are used for better scalability and decoupling.


# 🛠️ MY WORK AND ACHIEVEMENTS
Apart from designing the Offer Write Service and implementing the SAGA-based rollback flow, my major focus was on improving system 
performance, reliability, and maintainability. One of my biggest achievements was optimizing the asynchronous processing pipeline 
using Vert.x Futures and CompletableFutures. This reduced thread contention and improved offer ingestion throughput by nearly 25% 
under load. 

I also tuned Kafka producer and consumer configurations, like batch size and linger settings, which reduced event latency across services.

From a development perspective, I implemented a robust validation layer for campaign and merchant lookups, ensuring early failure detection 
before committing data to Couchbase. This cut downstream rollback events by almost 30%. I also standardized exception handling using @ControllerAdvice, 
which simplified debugging and improved error observability.

I also optimized several data processing modules using Java Streams to replace complex nested loops with clean, declarative pipelines.
By leveraging operations like filter, map, groupingBy, and parallel streams, I improved both readability and performance of offer validation
and enrichment flows.

Security and reliability were other areas I strengthened. I integrated Okta-based OAuth2 across all partner APIs, enforcing granular scopes 
for each operation. Combined with Resilience4j circuit breakers and retry mechanisms, this made our services resilient to network fluctuations 
and partner outages.

On the delivery side, I led the CI/CD automation using GitHub Actions, Hydra, and XLR pipelines, bringing deployment time down by roughly 40% 
while maintaining zero downtime releases on AWS EKS. I also introduced proactive CloudWatch and ELK monitoring dashboards, which reduced 
incident resolution time by over half.

Finally, I solved several cross-service consistency issues by improving our compensation event flow. I identified race conditions between offer 
creation and campaign updates and refactored the compensation listeners to handle idempotent rollbacks. This made our SAGA implementation 
more stable and reliable in production.

Overall, I’d say my biggest contributions were improving system throughput, operational resilience, and observability — turning what was initially 
a tightly coupled offer system into a high-performing, self-healing microservices ecosystem.

-- END --

<br>
Most of my work revolves around building **microservices** that are **scalable**, **reliable**, and **high-performing**. I’ve designed and developed **APIs** that handle millions of transactions using **Kafka** for asynchronous communication and patterns like **CQRS** and **SAGA** (rollback distributed transactions) for consistency across distributed services.<br>
[[SAGA diagram :: click to open](https://github.com/aakashkumar1980/apps-microservices/blob/module4/chapter2_event-driven-architecture-campaign-offer-saga/_readme_assets/offer_create-saga.png)]
> <b>Happy Path</b>: When an offer is created, it flows through multiple services (campaign, customer, merchant) that each update their data models, and if all succeed, the offer becomes active across the system.<br>
> <b>Failure Path</b>: If any service fails during the process (like linking offer to campaign fails), a compensating transaction is triggered that cancels the offer and notifies all previous services to roll back their changes, ensuring data consistency across all systems
<br>

I really enjoy working with **reactive and asynchronous programming** — especially with **Java Streams**, **Vert.X** and **CompletableFuture** — to build systems that can process thousands of lightweight events efficiently.

```java
/** JAVA STREAMS **/
// filter() active offers based on multiple criteria
- Merchant Applicability: Offer must be valid for the transaction's merchant.
- Date Range: Transaction timestamp must fall within offer's active period.
- Minimum Spend: Transaction amount must meet or exceed offer's minimum spend threshold.
- Channel Match: Transaction channel must be eligible for the offer.
List<Offer> matchedOffers = offersList.stream()
    .filter(offer -> offer.isApplicableToMerchant(transaction.getMerchantId()))
    .filter(offer -> offer.isWithinDateRange(transaction.getTimestamp()))
    .filter(offer -> transaction.getAmount().compareTo(offer.getMinSpend()) >= 0)
    .filter(offer -> channelMatches(offer, transaction.getChannel()))
    .collect(Collectors.toList());
    
// groupingBy() is used to group the colections by a key e.g. campaignId and then run the aggregations.
- Aggregates redemption data to calculate the total discount amount distributed for each campaign. 
- It's a critical metric for campaign performance monitoring and budget tracking in the rewards platform.     
Map<Campaign, Double> totalDiscountAmountByCampaign = redemptionList.stream()
  .collect(Collectors.groupingBy(
    Redemption::getCampaignId,
    Collectors.summingDouble(Redemption::getDiscountAmount)
  ));
  
other aggregations examples..
- Redemption Count by Merchant Categories i.e. "Dining",  "Travel", "Groceries". 
- Total Reward Points Issued by Reward Type i.e. "Cashback", "Points","Miles".
- Top Performing Merchants 
```

<br>
I also focus a lot on **security and automation**, using **OAuth2**, **Okta**, and **CI/CD pipelines** with **GitHub Actions** and **Jenkins**.

I love solving backend performance challenges, designing clean architectures, and continuously improving how systems communicate and scale.  


# 💳 Global Merchant Services[GMS] portfolio :: Digital Merchant Offers[DiMo] - Credit Card Offers
My latest project was around the **Credit Card Offers Platform**, which basically manages the entire lifecycle of an offer — right from when it’s created by the marketing team to when the customer finally receives their reward.

![Offer Workflow](_readme_assets/offer_workflow.png)

So, to put it simply, an **offer** is a kind of **promotion or incentive** that a credit card company gives to its customers. For example, things like <br> *“Get 10% cashback on dining this weekend”* or *“Earn 5,000 reward points if you spend $500 in a month.”*  
It’s a way to encourage customers to use their cards more often or spend in certain categories.

I worked on how these offers move through different stages in their lifecycle.  
It usually starts with **Offer Creation**, where the marketing or campaign team sets up all the rules — who’s eligible, what kind of reward it gives, when it starts and ends, and which merchants are included.

Once that’s done, the offer goes into the **Publication phase (also called Impressions)**, where it becomes visible to customers — like on the bank’s app, website, or through push notifications.

Then comes **Enrollment or Activation**. Some offers are auto-applied, but others require the customer to actually activate them. So we track who enrolled and when.

Next is the **Transaction and Redemption phase**. Whenever a customer makes a purchase, those transactions flow through our backend systems. We validate whether the purchase matches any active offer — checking things like merchant code, amount, and time period. If it qualifies, we mark that offer as **redeemed**.

After that comes **Reward Fulfillment**, where the customer actually receives their benefit — like cashback or reward points credited to their account.

And finally, we have **Analytics and Reporting**, which helps the business understand how the offer performed — like how many people redeemed it, total spend increase, and which offers were most effective.

So overall, I’ve worked across different parts of this lifecycle — mainly around **redemption and reward fulfillment**, ensuring transactions are processed accurately and efficiently while maintaining **scalability** and **low latency** in the system.

> # 🧩 MICROSERVICES LIST (top 20)
> 1. **Campaign Management Service**  
>   Owns campaigns (goals, budgets, timelines); parent container for offers.
<br><br>
> 2. **Offer Authoring Service**  
>   Creates/edits offers (reward type, rates, caps, start/end, channels).
> --
> 3. **Offer Approval & Governance Service**  
>   Workflow for review/approval, policy checks, versioning.
>
> 4. **Offer Publication Service**  
>   Publishes approved offers to channels/partners; manages on/off switches.
>
> 5. **Offer Catalog Service**  
>   Read-optimized catalog of active offers by merchant/category/location.
>
> 6. **Offer Eligibility Service**  
>   Central rules for offer-level eligibility (spend min, MCCs, channels, geo).
<br><br>
> 7. **Merchant Registry Service**  
>   Merchant master data (IDs, MCCs, locations, brand hierarchies).
>
> 8. **Merchant Eligibility Service**  
>   Resolves which merchants/locations qualify for each offer.
<br><br>
> 9. **Customer Profile Service**  
>   Cardholder master (cards, segments, status, product tiers).
>
> 10. **Customer Eligibility Service**  
>   Determines which customers qualify (segments, status, KYC, product).
>
> 11. **Customer Enrollment Service**  
>   Manages opt-in/opt-out, activation windows, enrollment state.
<br><br>
> 12. **Transaction Ingestion Service**  
>   Consumes card transactions from network/core; normalizes, enriches.
>
> 13. 🧾 **Redemption Service** (triggered when a customer makes a purchase)  
>   Matches purchases to active offers; applies rule checks and caps; emits redemption results.
>> 1. **Receive Transaction**
>>   - Gets a transaction event (merchant, amount, card, time) from the stream.
>>   - Validates data and ensures it’s not duplicated.
>> 2. **Match with Offers**
>>   - Searches active offers for that merchant or category (MCC).
>>   - Checks customer enrollment and eligibility rules like min spend and date range.
>> 3. **Decide Eligibility**
>>   - Marks the transaction as **eligible** or **ineligible** (with reason).
>>   - Applies offer-level and customer-level caps or budgets.
>> 4. **Publish Result**
>>   - Emits **`redemption.approved`** for qualified transactions.
>>   - Emits **`redemption.rejected`** for non-qualified ones.
>>   - These events are consumed by the **Reward Fulfillment Service**.
>
> 14. 💰 **Reward Calculation Service**  
>>   Calculates cashback/points based on redemption and policy (tiers, rounding).
>> 1. **Receive Redemption Event**
>>   - Listens to **`redemption.approved`** events from Kafka.
>>   - Validates data and links to the correct offer.
>> 2. **Load Reward Policy**
>>   - Retrieves reward type (cashback, points, etc.) and rate (e.g., 10% cashback).
>>   - Ensures offer and customer are still eligible for reward.
>> 3. **Calculate & Fulfill**
>>   - Calculates reward based on transaction amount and rules.
>>   - Applies caps and budget limits.
>>   - Publishes **`reward.fulfilled`** once credited successfully.
>
> 15. **Wallet & Ledger Service**  
>   Stores rewarded balances/points; supports statements and adjustments.
<br><br> 
> 16. **Notification Service**  
>   Sends real-time confirmations (“You earned $5 cashback”), summaries.
<br><br> 
> 17. **Dispute & Reversal Service**  
>   Handles chargebacks/refunds; reverses redemptions/rewards when needed.
>
> 18. **Settlement & Reconciliation Service**  
>   Settles with partners/merchants; reconciles costs and reimbursements.
<br><br> 
> 19. **Analytics & Reporting Service**  
>   KPIs: activation, redemption rate, ROI, lift; dashboards and extracts.
<br><br> 
> 20. **Partner Integration Service**  
>   Business-facing integration with aggregators (Cardlytics, Rakuten) for offer sync and status updates.
>
<br><br>
> # 🤖 AI/ML PROJECTS LIST
> 1. **Offer Ranking & Personalization**  
>   Ranks offers per user using contextual bandits/learning-to-rank.
>
> 2. **Customer Propensity Scoring**  
>   Likelihood to enroll/redeem; feeds targeting and suppression lists.
>
> 3. **Budget Optimization**  
>   Allocates campaign budget across segments/merchants to maximize ROI.
>
> 4. **Reward Liability Forecasting**  
>   Projects future payout/points liability from active offers.
>
> 5. **Fraud & Abuse Detection**  
>   Flags unusual redemption patterns, manufactured spend, location anomalies.
>
> 6. **Merchant Affinity & Similarity**  
>   Embeddings to cluster merchants; improves cross-sell and category targeting.
>
> 7. **Anomaly Detection for Transactions**  
>   Detects data quality or pipeline issues impacting redemptions.


<br><br>
# ARCHITECTURE (Logical Overview)
In my recent assignment, I worked on a new **partner integration platform** that connects our offer system with multiple global offer aggregators like **Cardlytics**, **Rakuten**, and a few others.  

The goal of this initiative was to make our platform more flexible so that we could onboard different offer partners easily and exchange offer data securely through standardized APIs.
The integration is **two-way**, though and is responsible for handling real-time API calls between our offer platform and external partners like **Cardlytics** etc.  

How this works with **Cardlytics** as an example is that they create and manages offers on their side — for example, “10% cashback at Starbucks” or “5% on groceries”. So, instead of us manually setting up these offers, Cardlytics now **calls our APIs** directly to push new offers, update existing ones, or block offers when needed.

All these requests come through our **AWS API Gateway**, which acts as the secure entry layer for partner integrations. We’ve protected this gateway using **Okta OAuth2**, so each request from Cardlytics must have a valid access token before it even reaches our internal services.

Once the API Gateway validates the request, it routes it into our internal offer platform where we apply business rules, validations, and process the incoming data. Every change — like offer creation or updates — is then published as **Kafka events**, which allows other services in our ecosystem to pick up those changes asynchronously and act on them. This ensures the system remains **loosely coupled and scalable**.
That’s the overall logical architecture of the **API** — designed for secure, real-time, two-way integration with global offer partners like Cardlytics and Rakuten.
<br>


# 🏗️ ARCHITECTURE (Physical Overview)
![_ApplicationFlow](_readme_assets/application_flow.png)
This is a high-level physical architecture diagram of our **API** for partner integrations. It shows how different application components interact to handle inbound and outbound API calls securely and efficiently.

In our API, the **inbound flow** follows an **event-driven microservices** pattern built on **Spring Boot**, and we’ve implemented it using **CQRS** along with **Saga** for distributed consistency.

So, when a partner like **Cardlytics** or **Rakuten** calls our APIs — for example, `createOffer via. POST /api/v1/offers`, `blockOffer via. PUT /api/v1/offers/{offerId}/block`, or `updateOffer via. POST /api/v1/offers/{offerId}` — the requests first go through the **AWS API Gateway**, which is secured by **Okta OAuth2**. Once the request passes authentication, it reaches our **Offer API Service**, which is a **Spring Boot** application exposing REST endpoints. This service handles schema validation using `@Valid`, applies **idempotency checks** with Redis, and uses a centralized `@ControllerAdvice` for error handling.

After validation, following the **Command Query Responsibility Segregation (CQRS)** approach. The API sends the request to the **Offer Command Service**, which processes the command, applies business rules, and updates the **write model** (stored in CouchbaseDB).  
Once the write operation succeeds, the service publishes a domain event to **Kafka (Amazon MSK)** — like `EVENT_OFFER_CREATED`, `EVENT_OFFER_UPDATED`, or `EVENT_OFFER_BLOCKED`. This is done using the **Spring KafkaTemplate**, often wrapped in a **json data format** to ensure the database transaction and Kafka publish remain consistent.

For transactions, we use the **Saga pattern** to coordinate **multi-step business processes** that span multiple microservices — for example, when an offer update triggers changes in the **Enrollment** or **Reward** services. Instead of using a single distributed transaction, each service performs its local transaction and publishes an event. Other services listen to that event, perform their own actions, and emit the next event in the flow.  

If any step fails, compensating events are published to roll back previous actions. We follow a **choreography-based Saga (de-centralized process)** here, where Kafka events drive the sequence of updates, supported by **Resilience4j** for retries and circuit-breaking.

## Summary
So, putting it all together —  
Partners send requests through the **API Gateway (Okta secured)** → our **Spring Boot Offer API** validates and forwards to the **Command service** → the command is processed and **Kafka events** are published → **Query and downstream services** consume those events and update their data asynchronously. The **CQRS** model gives us clean separation and scalability, while the **Saga pattern** ensures data consistency across multiple services in a distributed environment.

<br><br>

# MISCELLANEOUS DIAGRAMS
## Kafka PODs
![_KafkaPODs](_readme_assets/kafka_pods.png)