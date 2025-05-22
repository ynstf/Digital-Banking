# Digital-Banking

## E-Banking Application Backend

This repository contains the **Spring Boot Banking Management System** backend, featuring JWT-based authentication, RESTful APIs for managing customers, accounts, and operations, as well as Swagger/OpenAPI documentation.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Prerequisites](#prerequisites)
4. [Installation](#installation)
5. [Configuration](#configuration)
6. [Running the Application](#running-the-application)
7. [API Endpoints](#api-endpoints)

   * Authentication
   * Customers
   * Accounts
   * Operations
   * Dashboard
8. [Security](#security)
9. [Swagger Documentation](#swagger-documentation)
10. [Project Structure](#project-structure)
11. [License](#license)

---

## Project Overview

The Banking Management Backend provides a robust API for managing bank customers, accounts (current and savings), and account operations (credit, debit, transfer). It includes analytics endpoints for dashboard insights and is secured with JWT-based authentication.

## Technology Stack

* **Language**: Java 17
* **Framework**: Spring Boot 3
* **Security**: Spring Security, JWT (HS512)
* **Persistence**: Spring Data JPA, H2/MySQL
* **Documentation**: springdoc-openapi 2.1.0 (Swagger UI)
* **Build Tool**: Maven

## Prerequisites

* JDK 17+
* Maven 3.6+
* (Optional) MySQL database

## Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/your-org/banking-backend.git
   cd banking-backend
   ```
2. **Build the project**:

   ```bash
   mvn clean install
   ```

## Configuration

Update `src/main/resources/application.properties` with your database and JWT settings:

```properties
spring.application.name=eBanking
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/E-BANK?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto = create
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MariaDBDialect
spring.jpa.show-sql=true
security.jwt.expiration=1800
security.jwt.secretKey = <9faa372517acd1389758d3750fc07acf00f542277f26feec1ce4593e93f64e338>
```

## Running the Application

```bash
mvn spring-boot:run
```

The API will be accessible at `http://localhost:8080/`.

## API Endpoints

### Authentication

![image](https://github.com/user-attachments/assets/ec641b16-180b-4eca-9a76-194508ea4bf3)


* **POST** `/auth/login`
  **Request**: `{ "username": "admin", "password": "admin123" }`
  **Response**: `{ "jwt": "<token>", "type": "Bearer", "username": "admin", "roles": ["ROLE_ADMIN"] }`

* **GET** `/auth/profile` (Requires Bearer token)
  *Returns* authenticated user details.

### Customers

![image](https://github.com/user-attachments/assets/2cb846ed-6bce-405d-a0ac-7891dbc6fb68)

* **GET** `/customers` (ROLE\_USER)
* **GET** `/customers/{id}` (ROLE\_USER)
* **GET** `/customers/search?kw={keyword}` (ROLE\_USER)
* **POST** `/customers` (ROLE\_ADMIN)

  ```json
  { "name": "Alice", "email": "alice@example.com" }
  ```
* **PUT** `/customers/{id}` (ROLE\_ADMIN)
* **DELETE** `/customers/{id}` (ROLE\_ADMIN)

### Accounts

![image](https://github.com/user-attachments/assets/d0aabc06-9b23-4c2c-86df-ad3a7f4b7c06)



* **GET** `/accounts` (ROLE\_USER)
* **GET** `/accounts/{id}` (ROLE\_USER)
* **POST** `/accounts/current` (ROLE\_ADMIN)

  ```json
  { "customerId": 1, "initialBalance": 5000, "overDraft": 1000 }
  ```
* **POST** `/accounts/saving` (ROLE\_ADMIN)

  ```json
  { "customerId": 1, "initialBalance": 3000, "interestRate": 3.5 }
  ```

### Operations

![image](https://github.com/user-attachments/assets/d2349790-80eb-4526-8ba8-16a49bba7da8)

* **POST** `/accounts/credit` (ROLE\_ADMIN)

  ```json
  { "accountId": "<uuid>", "amount": 200, "description": "Salary" }
  ```
* **POST** `/accounts/debit` (ROLE\_ADMIN)

  ```json
  { "accountId": "<uuid>", "amount": 50, "description": "Grocery" }
  ```
* **POST** `/accounts/transfer` (ROLE\_ADMIN)

  ```json
  { "sourceAccountId": "<uuid1>", "destAccountId": "<uuid2>", "amount": 100 }
  ```
* **GET** `/accounts/{id}/operations` (ROLE\_USER)
* **GET** `/accounts/{id}/operations?page={p}&size={s}` (ROLE\_USER)

### Dashboard

![image](https://github.com/user-attachments/assets/ac9041a7-20d8-48cb-81d1-0766e561e745)


* **GET** `/dashboard/summary` (ROLE\_ADMIN)
  *Returns* counts, balances, trends, and top customers.

## Security

![image](https://github.com/user-attachments/assets/3d005f4a-a649-4a8c-aeeb-5e0c01dad58b)


* **JWT Authentication**: Include `Authorization: Bearer <token>` in requests.

* **Roles**:

  * `ROLE_USER`: Read-only operations
  * `ROLE_ADMIN`: Full access

* **In-Memory Users** (for demo):

  ```properties
  user1 / password1 -> ROLE_USER
  admin / admin123 -> ROLE_ADMIN
  ```

## Swagger Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

![image](https://github.com/user-attachments/assets/14e42d70-2a81-463d-a300-d7f365f26882)


It includes security integration: click "Authorize" and paste your Bearer JWT.


## Project Structure

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.bank
│   │   │       ├── config      # Swagger & Security configuration
│   │   │       ├── controllers # REST APIs
│   │   │       ├── dtos        # Data Transfer Objects
│   │   │       ├── entities    # JPA entities
│   │   │       ├── exceptions  # Custom exceptions
│   │   │       ├── repositories# Spring Data JPA repos
│   │   │       ├── services    # Business logic
│   │   │       └── utils       # Mappers & utilities
│   └── resources
│       └── application.yml
└── pom.xml
```


