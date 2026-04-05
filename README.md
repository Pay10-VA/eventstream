# Eventstream

EventStream is a real-time event ingestion and analytics platform built with Java and Spring Boot, designed to capture and process user behavior events at scale. Events are published to a REST API, streamed through Apache Kafka with retry and dead letter queue support, persisted to MongoDB for historical analysis, and tracked in Redis using a sliding window algorithm for live event counts.

### Tech Stack

[![My Skills](https://skillicons.dev/icons?i=java,spring,kafka,redis,mongodb,docker,js,nodejs)](https://skillicons.dev)

## Architecture Diagram

## Features

- Event Ingestion: REST API endpoint for receiving user behavior events from any client

- Kafka Streaming: Events are published to a Kafka topic and consumed asynchronously to decouple ingestion from processing

- Retry & DLQ: Failed events are retried automatically before being routed to a dead letter queue

- Persistent Storage: All events are stored in MongoDB for historical querying and replay

- Real-time Analytics: Redis sliding window tracks live event counts over the last 10 minutes

- User Journey Lookup: Query all events tied to a specific user or session

- Dockerized Infrastructure: Kafka, Kafka UI, MongoDB, and Redis all spun up with a single `docker-compose up -d` command

- Node.js Simulator: Realistic ecommerce event simulation across 15 concurrent users with weighted device types and probabilistic user behavior

## Getting Started

1. Run `sh install.sh` to install all dependencies
2. Run `sh start.sh`. This command will create & run the docker containers and then start the Spring API
3. In another terminal window, run `sh test.sh` to run the simulation script

## API Endpoints

### Events

| Method | Endpoint                          | Description                        |
| ------ | --------------------------------- | ---------------------------------- |
| POST   | `/api/events`                     | Ingest a new event into the system |
| GET    | `/api/events/user/{userId}`       | Get all events for a specific user |
| GET    | `/api/events/session/{sessionId}` | Get all events within a session    |

### Analytics

| Method | Endpoint                              | Description                                        |
| ------ | ------------------------------------- | -------------------------------------------------- |
| GET    | `/api/analytics/counts`               | Get real-time event counts for the last 10 minutes |
| GET    | `/api/analytics/counts/user/{userId}` | Get event counts for a specific user               |

### Health

| Method | Endpoint      | Description                            |
| ------ | ------------- | -------------------------------------- |
| GET    | `/api/health` | Check if the service is up and running |

## Project Structure

### Analytics API

```
src/main/java/com/example/eventstream/
├── controller/ # Handles incoming HTTP requests and returns responses. No business logic.
│ └── dto/ # Request and response objects that define the shape of the API
├── service/ # Business logic layer. Orchestrates Kafka, MongoDB, and Redis operations
│ └── interfaces/ # Service contracts that controllers depend on instead of concrete implementations
├── repository/ # Data access layer. Handles all reads and writes to MongoDB and Redis
│ └── interfaces/ # Repository contracts that services depend on instead of concrete implementations
├── model/ # Domain objects that map to MongoDB documents
├── mapper/ # Converts DTOs to domain models keeping each layer independent
├── enums/ # Shared enums for EventType used across all layers
└── config/ # Spring configuration classes for MongoDB and Redis
```

Each layer depends on interfaces rather than concrete implementations in order to keep the code loosely coupled, easy to test, and simple to swap out.

### Root Directory

`start.sh`: Shell script to start up the Spring Application from the root directory

`test.sh`: Shell script to run the JavaScript simulation script and send events to the Spring API

`install.sh`: Shell script to install dependencies in BOTH `analytics-api` and `event-simulator`
