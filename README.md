# ecommerce-spring-cloud-inventory-service

REST microservice that manages stock levels for products in the e-commerce demo system.

## What This Service Does

The inventory service stores and manages the quantity of each product in stock. Each record links a `productId` to a `quantity`. The service exposes endpoints to query stock, create entries, reduce quantity, and delete records.

It registers itself with Eureka on startup and reads its configuration from the central Config Server, following the standard Spring Cloud bootstrap pattern used across all services in this system.

## Endpoints

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/inventory/{productId}` | Get stock for a product |
| `POST` | `/api/inventory` | Create a new inventory entry |
| `PATCH` | `/api/inventory/{productId}/decrease` | Decrease stock by a given quantity |
| `PATCH` | `/api/inventory/{productId}/increase` | Increase stock by a given quantity |
| `DELETE` | `/api/inventory/{productId}` | Delete an inventory entry |

## Key Configuration

| Property | Value | Why |
|---|---|---|
| `spring.application.name` | `inventory-service` | Used by Eureka as the service identifier and by Config Server to resolve the correct YAML file |
| `spring.config.import` | `configserver:http://localhost:8888` | Pulls configuration from the central Config Server at startup |
| `server.port` | `8082` | Defined in `inventory-service.yml` on the config-repo |
| `spring.datasource.url` | PostgreSQL | Defined in `inventory-service.yml` on the config-repo |

## How to Run

Prerequisites: Config Server and Discovery Server must be running first.

```
./mvnw spring-boot:run
```

## Stack

| Component | Version |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.6 |
| Spring Cloud | 2025.1.1 |
| Spring Data JPA | managed by Boot |
| Flyway | managed by Boot |
| PostgreSQL driver | managed by Boot |
| MapStruct | 1.6.3 |
| Eureka Client | managed by Cloud BOM |
| Config Client | managed by Cloud BOM |

## Startup Order

```
1. config-server      :8888
2. discovery-server   :8761
3. product-service    :8081
4. inventory-service  :8082   <- this service
5. order-service      :8083
6. api-gateway        :8080
```

---

# Project Overview — Spring Cloud Microservices Demo

## Goal

A minimal distributed e-commerce system built to explore the core components of Spring Cloud: centralized configuration, service discovery, inter-service communication, API gateway, and resilience patterns.

## Architecture

```
[Client HTTP]
      |
[API Gateway :8080]
      |
      +---> [Product Service :8081]
      +---> [Inventory Service :8082]
      +---> [Order Service :8083]
                  |
                  +---> [Product Service]   (via OpenFeign)
                  +---> [Inventory Service] (via OpenFeign)

All services register on  --> [Eureka Discovery Server :8761]
All services read config from --> [Config Server :8888]
Config Server reads from      --> [config-repo on GitHub]
```

## Repository Structure (Polyrepo)

| # | Repository | Purpose |
|---|---|---|
| 1 | `spring-cloud-config-repo` | YAML configuration files, read by Config Server via Git |
| 2 | `spring-cloud-config-server` | Reads config-repo and exposes it to all services |
| 3 | `spring-cloud-discovery-server` | Eureka — service registry |
| 4 | `spring-cloud-api-gateway` | Single entry point, routes to microservices |
| 5 | `spring-cloud-product-service` | Product CRUD |
| 6 | `spring-cloud-inventory-service` | Inventory CRUD |
| 7 | `spring-cloud-order-service` | Order orchestration, calls product and inventory |

## Spring Cloud Concepts Covered

| Concept | Component | Repository |
|---|---|---|
| Centralized configuration | Spring Cloud Config | config-server + config-repo |
| Service discovery | Eureka | discovery-server |
| Client-side load balancing | Spring Cloud LoadBalancer | built into Feign and Gateway |
| Inter-service communication | OpenFeign | order-service |
| API Gateway / routing | Spring Cloud Gateway | api-gateway |
| Circuit Breaker | Resilience4j | order-service (optional) |

## Common Stack

```xml
<!-- Spring Boot -->
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.6</version>
</parent>

<!-- Spring Cloud BOM (in dependencyManagement of each pom.xml) -->
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-dependencies</artifactId>
      <version>2025.1.1</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

**Java:** 21