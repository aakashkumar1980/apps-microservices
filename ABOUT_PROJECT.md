# 🧑‍💻 ABOUT ME :: Akhila Bezawada

Hi, I’m **Akhila Bezawada**. I’m a **Backend and Cloud Developer** with over **seven years of experience** working on large-scale, event-driven systems using **Java**, **Spring Boot**, **Vert.X**, and **AWS Cloud**.

Most of my work revolves around building **microservices** that are **scalable**, **reliable**, and **high-performing**. I’ve designed and developed **APIs** that handle millions of transactions using **Kafka** for asynchronous communication and patterns like **CQRS** and **Saga** (rollback transaction) for consistency across distributed services.

I really enjoy working with **reactive and asynchronous programming** — especially with **Vert.X**, **Java Streams**, and **CompletableFuture** — to build systems that can process thousands of lightweight events efficiently.

On the cloud side, I’ve worked extensively with **AWS**, setting up **EKS clusters**, **API Gateway**, **Lambda functions**, and using **CloudWatch** for observability and monitoring. I also focus a lot on **security and automation**, using **OAuth2**, **Okta**, and **CI/CD pipelines** with **GitHub Actions** and **Jenkins**.

I love solving backend performance challenges, designing clean architectures, and continuously improving how systems communicate and scale.  


# 💳 Global Merchant Services[GMS] portfolio :: Digital Merchant Offers[DiMo] - Credit Card Offers
My latest project was around the **Credit Card Offers Platform**, which basically manages the entire lifecycle of an offer — right from when it’s created by the marketing team to when the customer finally receives their reward.

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
>
> ## 🎯 Redemption and Reward Fulfillment Flow
>> ### 🧾 **Redemption Service**  (triggered when a customer makes a purchase)
> 1. **Receive Transaction**
> - Gets a transaction event (merchant, amount, card, time) from the stream.
> - Validates data and ensures it’s not duplicated.
> 
> 2. **Match with Offers**
> - Searches active offers for that merchant or category (MCC).
> - Checks customer enrollment and eligibility rules like min spend and date range.
> 
> 3. **Decide Eligibility**
> - Marks the transaction as **eligible** or **ineligible** (with reason).
> - Applies offer-level and customer-level caps or budgets.
> 
> 4. **Publish Result**
> - Emits **`redemption.approved`** for qualified transactions.
> - Emits **`redemption.rejected`** for non-qualified ones.
> - These events are consumed by the **Reward Fulfillment Service**.
>
>
>> ### 💰 **Reward Fulfillment Service**  (triggered when a redemption is approved)
> 1. **Receive Redemption Event**
> - Listens to **`redemption.approved`** events from Kafka.
> - Validates data and links to the correct offer.
>
> 2. **Load Reward Policy**
> - Retrieves reward type (cashback, points, etc.) and rate (e.g., 10% cashback).
> - Ensures offer and customer are still eligible for reward.
> 
> 3. **Calculate & Fulfill**
> - Calculates reward based on transaction amount and rules.
> - Applies caps and budget limits.
> - Publishes **`reward.fulfilled`** once credited successfully.
>
> ### 🧠 Summary
> - **Redemption Service**: Detects qualifying transactions for active offers.
> - **Reward Fulfillment Service**: Calculates and credits rewards for approved redemptions.  
> Together, they form the core of the **offer-to-reward flow**, ensuring accurate and reliable reward delivery.
>

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




![_ApplicationFlow](_readme_assets/application_flow.png)