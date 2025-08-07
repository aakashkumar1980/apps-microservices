# createOffer
## Flow Diagram
```mermaid
flowchart TD

%% REST Client
    POSTMAN["Postman<br>POST /offer"]:::external

%% Offer Write Microservice
    subgraph Offer_Write_Microservice
        style Offer_Write_Microservice fill:#FFF9C4,stroke:#333,stroke-width:1px

        OCC["OfferCommandController<br>createOffer()"]:::controller
        OCS["OfferCommandService<br>createOffer()"]:::service
        CV["CampaignValidation<br>validateCampaign()"]:::service
        MV["MerchantValidation<br>validateMerchant()"]:::service
        OV["OfferValidation<br>validateCampaignBudgetNotExceeded()"]:::service
        OCR["OfferCommandRepository<br>save()"]:::repository
        OEP["OfferEventPublisher<br>publishCreateOfferEvent()"]:::event
    end

%% Merchant Subscriber
    subgraph Merchant_Write_Microservice
        style Merchant_Write_Microservice fill:#F1F8E9,stroke:#689F38,stroke-width:1px

        MES["OfferEventSubscriber<br>subscribeCreateOfferEvent()"]:::subscriber
        MC["MerchantCommandService<br>linkOfferToMerchant()"]:::service
    end

%% Customer Subscriber
    subgraph Customer_Write_Microservice
        style Customer_Write_Microservice fill:#FCE4EC,stroke:#C2185B,stroke-width:1px

        CES["OfferEventSubscriber<br>subscribeCreateOfferEvent()"]:::subscriber
        CC["CustomerCommandService<br>assignOfferToCustomer()"]:::service
        CEP["CustomerOfferEventPublisher<br>publishOfferAssignedEvent()"]:::event
    end

%% Campaign Subscriber
    subgraph Campaign_Write_Microservice
        style Campaign_Write_Microservice fill:#BBDEFB,stroke:#1976D2,stroke-width:1px

        CAS["OfferEventSubscriber<br>subscribeCreateOfferEvent()"]:::subscriber
        CCS["CampaignCommandService<br>linkOfferToCampaign()"]:::service
    end

%% External Systems
    COUCHBASE_OFFER["Couchbase<br>(local - Offer)"]:::external
    COUCHBASE_MERCHANT["Couchbase<br>(local - Merchant)"]:::external
    COUCHBASE_CUSTOMER["Couchbase<br>(local - Customer)"]:::external
    COUCHBASE_CAMPAIGN["Couchbase<br>(local - Campaign)"]:::external
    KAFKA1["Kafka Topic<br>OFFER_CREATED"]:::kafka
    KAFKA2["Kafka Topic<br>OFFER_ASSIGNED"]:::kafka

%% Flow Steps (Main Command Flow)
    POSTMAN -->|1: Send Offer JSON| OCC
    OCC -->|2: Delegate to service| OCS
    OCS -->|3: Validate campaign| CV
    OCS -->|4: Validate merchant| MV
    OCS -->|5: Check budget| OV
    OCS -->|6: Save offer| OCR
    OCR -->|7: Save to DB| COUCHBASE_OFFER
    OCS -->|8: Publish OFFER_CREATED| OEP
    OEP -->|9: Send to Kafka| KAFKA1

%% Subscriptions via Kafka OFFER_CREATED
    KAFKA1 --> MES
    MES -->|1: Cache offer| MC
    MC -->|2: Update merchant| COUCHBASE_MERCHANT

    KAFKA1 --> CES
    CES -->|1: Cache offer| CC
    CC -->|2: Update customer| COUCHBASE_CUSTOMER
    CC -->|3: Publish OFFER_ASSIGNED| CEP
    CEP -->|4: Send to Kafka| KAFKA2

    KAFKA1 --> CAS
    CAS -->|1: Cache offer| CCS
    CCS -->|2: Update campaign| COUCHBASE_CAMPAIGN

%% Styling
classDef controller fill:#AED581,stroke:#33691E,stroke-width:1px;
classDef service fill:#FFF3E0,stroke:#F57C00,stroke-width:1px;
classDef repository fill:#E0F2F1,stroke:#00796B,stroke-width:1px;
classDef util fill:#E1BEE7,stroke:#6A1B9A,stroke-width:1px;
classDef event fill:#F8BBD0,stroke:#AD1457,stroke-width:1px,stroke-dasharray: 5 5;
classDef subscriber fill:#EDE7F6,stroke:#512DA8,stroke-width:1px;
classDef external fill:#FFFFFF,stroke:#000,stroke-width:1px,stroke-dasharray: 5 5;
classDef kafka fill:#FFF3E0,stroke:#FF9800,stroke-width:1px,stroke-dasharray: 5 5;

```
<br/>
<br/>
<br/>

## 🟨 Offer Write Microservice
---
### **1.** `POST /offer` (Triggered by Postman or frontend)
```json
{
  "name": "Electronics Summer Discount",
  "description": "Save $43.30 on select electronics during the Summer Savings Blast campaign.",
  "campaign_id": "campaign::1",
  "merchant_id": "merchant::7",
  "type": "DISCOUNT",
  "discount_amount": 43.3,
  "currency": "USD",
  "valid_from": "2025-07-15T00:00:00",
  "valid_to": "2025-08-07T00:00:00",
  "max_redemptions": 328,
  "status": "ACTIVE",
  "segment_criteria": "GOLD"
}
```
---

### **2.** `OfferCommandController.createOffer()`
- Receives and validates the request body
- Validates required fields
---

### **3.** `CampaignValidation.validateCampaign(campaignId)`
- Fetch Campaign from Cache or REST API call.
- Verifies that campaign:
  - Exists
  - Has status `ACTIVE` <br/>
    (Fails if above conditions are not met ❌)
  
```json
GET /campaigns/campaign::123 → 200 OK
{
  "id": "campaign::123",
  "status": "ACTIVE",
  ...
}
```
---

### **4.** `MerchantValidation.validateMerchant(merchantId)`
- Fetch Merchant from Cache or REST API call.
- Validates that merchant:
  - Exists <br/>
  (Fails if above condition are not met ❌)
  
```json
GET /merchants/merchant::456 → 200 OK
{
  "id": "merchant::456",
  ...
}
```
---

### **5.** `OfferValidation.validateCampaignBudgetNotExceeded(campaignId, offerDiscountAmount)`
- Calls Offer's REST API to fetch all existing offers for that campaign
  - Calculates the total of all `discountAmount`.
  - Adds this to the new offer’s discount to arrive at the total ***totalDiscountAmount***.
- Fetch Campaign from Cache or REST API call.
  - Get's the ***budget*** of that campaign.
- Finally, checks if ***totalDiscountAmount*** exceeds the ***budget*** of the campaign.
  - If it does, the offer is rejected ❌.
  - If not, the offer proceeds to be saved.

```json
Current Offers:
[
  { "id": "offer::1", "discountAmount": 400 },
  { "id": "offer::2", "discountAmount": 500 }
]
New Offer: 300
totalDiscountAmount => (400 + 500) + 300 = 1200

Campaign budget => 1000

RESULT: ❌ REJECTED
  totalDiscountAmount (1200) > budget (1000) 

```
---

### **6.** `OfferCommandRepository.save()`
- Persists the offer in Couchbase
---

### **7.** Couchbase (local - Offer)
```json
{
  "id": "offer::790",
  "title": "20% Cashback on Shoes",
  ...
}
```
---

### **8.** `OfferEventPublisher.publishCreateOfferEvent()`
- Publishes `OFFER_CREATED` event to Kafka
---

### **9.** Kafka Topic: `OFFER_CREATED`
- Triggers subscribers in 3 microservices
---
<br/>


## 🟩 Merchant Write Microservice

#### **MES** – Kafka triggers `subscribeCreateOfferEvent()`

---

#### **1.** Redis Cache
- Writes offer to Redis using `offerId`

---

#### **2.** `MerchantCommandService.linkOfferToMerchant()`

> Adds offer ID to merchant record if not already present.

```json
{
  "id": "merchant::456",
  "linkedOfferIds": ["offer::789", "offer::790"]
}
```

---

#### **3.** Couchbase (local - Merchant)

---

## 🟪 Customer Write Microservice

#### **CES** – Kafka triggers `subscribeCreateOfferEvent()`

---

#### **1.** Redis Cache

---

#### **2.** `CustomerCommandService.assignOfferToCustomer()`

> **Javadoc**:
> ```java
> /**
>  * Assigns offer to all eligible customers.
>  * Uses eligibility engine, updates DB, and publishes event.
>  */
> ```

```json
{
  "id": "customer::123",
  "enrolledOfferIds": ["offer::790"]
}
```

---

#### **3.** Couchbase (local - Customer)

---

#### **4.** `CustomerOfferEventPublisher.publishOfferAssignedEvent()`

---

#### **5.** Kafka Topic: `OFFER_ASSIGNED`

---

## 🟦 Campaign Write Microservice

#### **CAS** – Kafka triggers `subscribeCreateOfferEvent()`

---

#### **1.** Redis Cache

---

#### **2.** `CampaignCommandService.linkOfferToCampaign()`

> Adds the offer to campaign’s `offerIds` list if not already present.

```json
{
  "id": "campaign::123",
  "offerIds": ["offer::789", "offer::790"]
}
```

---

#### **3.** Couchbase (local - Campaign)

---

## 🧠 Design Highlights

- Redis is used for caching only; no fallback logic in this write flow
- `OFFER_ASSIGNED` event is part of event chaining in Customer microservice
- Each microservice writes to its own local Couchbase bucket
- Kafka topics decouple services and improve scalability

---

## 📂 Microservices Involved

- `OfferCommandController`, `OfferCommandService`
- `MerchantCommandService`, `CustomerCommandService`, `CampaignCommandService`
- Kafka Event Publishers and Subscribers
