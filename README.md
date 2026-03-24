# TradeSim



TradeSim is a production-ready, containerized backend REST API for a virtual equity trading platform. It features a strict transactional ledger designed to enforce ACID compliance and prevent common financial system exploits (like double-spending) using an Escrow pattern.

## Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3 (REST, Data JPA, Validation)
* **Concurrency:** Java Virtual Threads
* **Database:** PostgreSQL
* **Migrations:** Flyway
* **Infrastructure:** Docker & Docker Compose
* **CI/CD:** GitHub Actions (GHCR) & AWS EC2

## Current Features

* **Transactional User Onboarding:** Registers users and automatically provisions a funded fiat account (`$100,000.00`) within a single database transaction.
* **Order Ingestion & Validation:** Accepts BUY/SELL orders and enforces strict balance and asset ownership checks before persisting the intent to the ledger.
* **Escrow Pattern:** Deducts fiat or asset quantities immediately upon order creation to prevent double-spending while orders are in the `OPEN` state.
* **Financial Precision:** Utilizes `BigDecimal` and `NUMERIC(19,4)` strictly across the Java and SQL layers to prevent floating-point rounding errors.

## Running Locally

### Prerequisites
* Docker Desktop installed and running.
* Java 21 JDK installed.

### 1. Start the Database
Spin up the isolated PostgreSQL container. Flyway will automatically run the schema migrations upon application boot.
```bash
docker-compose up -d
```

### 2. Run the Application
Start the Spring Boot trading engine.
```bash
./mvnw spring-boot:run
```
The API will be available at `http://localhost:8080`.

## API Endpoints

### Register a User
```http
POST /api/v1/users/register
Content-Type: application/json

{
  "username": "trader_john",
  "email": "john@tradesim.com"
}
```

### Place an Order
```http
POST /api/v1/orders
Content-Type: application/json

{
  "accountId": "UUID-FROM-REGISTRATION",
  "ticker": "AAPL",
  "side": "BUY",
  "price": 150.00,
  "quantity": 10
}
```

## Deployment

This project includes a fully automated CI/CD pipeline. Every push to the `main` branch triggers a GitHub Action that uses a multi-stage `Dockerfile` to compile the application and publish a lean Alpine runtime image to the GitHub Container Registry (GHCR). It is currently configured for deployment on an AWS EC2 instance.

***