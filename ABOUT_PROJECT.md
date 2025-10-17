# 🧑‍💻 ABOUT ME :: Akhila Bezawada

Hi, I’m **Akhila Bezawada**. I’m a **Backend and Cloud Developer** with over **seven years of experience** working on large-scale, event-driven systems using **Java**, **Spring Boot**, **Vert.X**, and **AWS Cloud**.

Most of my work revolves around building **microservices** that are **scalable**, **reliable**, and **high-performing**. I’ve designed and developed **APIs** that handle millions of transactions using **Kafka** for asynchronous communication and patterns like **CQRS** and **Saga** for consistency across distributed services.

I really enjoy working with **reactive and asynchronous programming** — especially with **Vert.X**, **Java Streams**, and **CompletableFuture** — to build systems that can process thousands of lightweight events efficiently.

On the cloud side, I’ve worked extensively with **AWS**, setting up **EKS clusters**, **API Gateway**, **Lambda functions**, and using **CloudWatch** for observability and monitoring. I also focus a lot on **security and automation**, using **OAuth2**, **Okta**, and **CI/CD pipelines** with **GitHub Actions** and **Jenkins**.

I love solving backend performance challenges, designing clean architectures, and continuously improving how systems communicate and scale.  


# 💳 PROJECT (Global Merchant Services[GMS] portfolio :: Digital Merchant Offers[DiMo] project) :: Credit Card Offers

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


# ARCHITECTURE
In my recent assignment, I worked on a new **partner integration platform** that connects our offer system with multiple global offer aggregators like **Cardlytics**, **Rakuten**, and a few others.  
The goal of this initiative was to make our platform more flexible so that we could onboard different offer partners easily and exchange offer data securely through standardized APIs and backend File processing.

## 🧩 API Engine
The first part of this integration platform is the **API Engine**. 
This engine is responsible for handling real-time API calls between our offer platform and external partners like **Cardlytics** etc.  

The integration is **two-way**, though.
### Inbound Flow (Ingress)
How this works with **Cardlytics** as an example is that they create and manages offers on their side — for example, “10% cashback at Starbucks” or “5% on groceries”. So, instead of us manually setting up these offers, Cardlytics now **calls our APIs** directly to push new offers, update existing ones, or block offers when needed.

All these requests come through our **AWS API Gateway**, which acts as the secure entry layer for partner integrations.  
We’ve protected this gateway using **Okta OAuth2**, so each request from Cardlytics must have a valid access token before it even reaches our internal services.

Once the API Gateway validates the request, it routes it into our internal offer platform where we apply business rules, validations, and process the incoming data.  
Every change — like offer creation or updates — is then published as **Kafka events**, which allows other services in our ecosystem to pick up those changes asynchronously and act on them.  
This ensures the system remains **loosely coupled and scalable**.

### Outbound Flow (Egress)
We also send updates back to Cardlytics — things like offer status changes, customer enrollments, or reward fulfillment confirmations.  
But for outbound traffic, we don’t hit Cardlytics’ real endpoints directly.  
Instead, we use a **proxy layer** built on **AWS API Gateway (HTTP API)** with a **custom domain**.  
This proxy helps us mask the real URLs, control the flow, apply retry logic, and add additional protection using **AWS WAF** and **Secrets Manager** for credentials.

So, from a logical point of view, it works like this:  
**Cardlytics → AWS API Gateway (Okta secured) → Our Offer Platform (Kafka events) → AWS Proxy Gateway → Cardlytics APIs.**

That’s the overall logical architecture of the **API Engine** — designed for secure, real-time, two-way integration with global offer partners like Cardlytics and Rakuten.



## File Engine