# Spring Boot REST API Microservice Tutorial

This project demonstrates how to build a REST API microservice from scratch using Spring Boot. The API manages campaigns and follows a layered architecture with clear separation of concerns.

## Steps to Create the REST API

1. **Initialize the Spring Boot Project**
   - Use [Spring Initializr](https://start.spring.io/) or your IDE to create a new Spring Boot project.
   - Add dependencies for `spring-boot-starter-web` and any utilities (e.g., Apache Commons).

2. **Define the Data Model**
   - Create DTO classes (e.g., `Campaign`) to represent your domain objects.

3. **Implement the Service Layer**
   - Write service classes for business logic (e.g., `CampaignCommandService`, `CampaignQueryService`).

4. **Create Controller Classes**
   - Implement REST controllers for handling HTTP requests (e.g., `CampaignCommandController`, `CampaignQueryController`).

5. **Configure Persistence (Mock or Real DB)**
   - For demonstration, use mock data utilities. For production, integrate with a database (e.g., Couchbase).

6. **Test the API**
   - Use tools like Postman or curl to test endpoints for creating, updating, retrieving, and deleting campaigns.

7. **(Optional) Add Event Publishing and Kafka Integration**
   - Extend the architecture to publish events and handle asynchronous flows.

## Architecture & Flow

Below is a mermaid diagram illustrating the microservice flow for both publisher and subscriber patterns:

```mermaid
%%{ init: { "flowchart": { "htmlLabels": true, "wrappingWidth": 500 } } }%%
flowchart TD

%% %%%%%%%%%%%%%%%%%%%%% %%
%% MicroservicePublisher %%
%% %%%%%%%%%%%%%%%%%%%%% %%
POSTMAN["POST /$uri
  <div style='text-align: left'>
    {
    }
  </div>
"]:::external

subgraph $MicroservicePublisher
style $MicroservicePublisher fill:#FFF9C4,stroke:#333,stroke-width:1px
  MPCC["$CommandController<br> - $controllerFunction()"]:::controller
  MPCS["$CommandService<br> - $serviceFunction()"]:::service

  MPBS["$BusinessService<br> - $serviceFunction()
    <div style='text-align:left; font-style: italic;'>
      <div style='background-color:rgb(252, 251, 240); width:300px;'>
        basedto($model).json
        {
          #emsp; ...,
          #emsp; data: {
          #emsp; }
        }<br>
      </div>
      $SUMMARY
      - $details
    </div>
  "]:::service

  MPCR["$CommandRepository<br> - $repositoryFunction()"]:::repository
  MPEP["$EventPublisher<br> - $publisherEventFunction()"]:::event
end

KAFKA[["kafka topic: $TOPIC
  <div style='text-align:left; background-color:#fcf3f0; width:180px;'>
    event($event).json
    {
      #emsp; <font color=red><b>id</b>:''</font>,
      #emsp; ...
    }
  </div>
"]]:::kafka

CHBMP[("Couchbase DB<br>(bucket: $MicroservicePublisher)")]:::db

%% Flow Steps (Main Command Flow)
POSTMAN -->|1: forward json body | MPCC
MPCC -->|2: delegate to service| MPCS
MPCS -->|3: call business function| MPBS
MPCS -->|6: save data model| MPCR
MPCR -->|7: write to DB| CHBMP
MPCS -->|8: publish event| MPEP
MPEP -->|9: send to kafka topic| KAFKA


%% %%%%%%%%%%%%%%%%%%%%%%% %%
%% MicroserviceSubscriberX %%
%% %%%%%%%%%%%%%%%%%%%%%%% %%
subgraph $MicroserviceSubscriberX
style $MicroserviceSubscriberX fill:#F1F8E9,stroke:#689F38,stroke-width:1px
  MSES["$EventSubscriber<br> - $subscriberEventFunction()"]:::subscriber

  MSCS["$CommandService<br> - $serviceFunction()
    <div style='text-align:left; font-style: italic;'>
      <div style='background-color:rgb(252, 251, 240); width:300px;'>
        basedto($model).json
        {
          #emsp; ...,
          #emsp; data: {
          #emsp; }
        }<br>
      </div>
      $SUMMARY
      - $details
    </div>
  "]:::service
end

CHBMSX[("Couchbase DB<br>(bucket: $MicroserviceSubscriberX)")]:::db

%% Flow Steps (SubscribersX Flow)
KAFKA --> MSES
MSES -->|1: cache event| MSCS
MSCS -->|2: update data model| CHBMSX



%% $$$$$$$ $$
%% Styling %%
%% $$$$$$$ $$
classDef controller fill:#AED581,stroke:#33691E,stroke-width:1px;
classDef service fill:#FFF3E0,stroke:#F57C00,stroke-width:1px;
classDef repository fill:#E0F2F1,stroke:#00796B,stroke-width:1px;
classDef event fill:#F8BBD0,stroke:#AD1457,stroke-width:1px,stroke-dasharray: 5 5;
classDef subscriber fill:#EDE7F6,stroke:#512DA8,stroke-width:1px;
classDef external fill:#FFFFFF,stroke:#000,stroke-width:1px,stroke-dasharray: 5 5;
classDef kafka fill:#fcf3f0,stroke:red,stroke-width:1px,stroke-dasharray: 5 5;
classDef db fill:#FFFFFF,stroke:#000,stroke-width:1px,stroke-dasharray: 5 5;
```

## Example Endpoints

- `POST /api/campaigns` — Create a campaign
- `GET /api/campaigns` — List all campaigns
- `GET /api/campaigns/{id}` — Get campaign by ID
- `PUT /api/campaigns/{id}` — Update campaign
- `DELETE /api/campaigns/{id}` — Delete campaign

## How to Run

1. Build the project:  
   `./gradlew build`
2. Start the application:  
   `./gradlew bootRun`
3. Test endpoints using Postman or curl.

## Next Steps

- Integrate with a real database (e.g., Couchbase, PostgreSQL).
- Add event publishing and Kafka integration for distributed microservices.
- Implement authentication and authorization.


