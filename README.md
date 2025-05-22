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


*******************************************************

## E-Banking Application Frontend

This repository contains the **Angular Banking Management System** frontend, featuring role-based access control, interactive UI, and integration with the Spring Boot backend.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Prerequisites](#prerequisites)
4. [Installation](#installation)
5. [Configuration](#configuration)
6. [Running the Application](#running-the-application)
7. [Project Structure](#project-structure)
8. [Pages & Components](#pages--components)

   * Authentication & Guards
   * Admin Dashboard
   * Customer Management
   * Account Operations
   * User Management
   * Profile & Settings
9. [Navigation Bar](#navigation-bar)
10. [Charting & Dashboard](#charting--dashboard)
11. [Styling & Theming](#styling--theming)
12. [License](#license-frontend)

---

## Project Overview

The frontend is an **Angular 16** application providing a cyberpunk-themed UI for managing banking operations. It interacts with the backend via REST APIs and secures routes with JWT authentication and role guards.

## Technology Stack

* **Framework**: Angular
* **Language**: TypeScript
* **UI**: Bootstrap 5 + custom cyberpunk CSS
* **Charts**: ng2-charts (Chart.js)
* **Routing**: Angular Router with Auth & Role Guards
* **State**: Services + RxJS

## Prerequisites

* Node.js 18+
* npm 8+
* Angular CLI 16+

## Installation

```bash
git clone https://github.com/your-org/banking-frontend.git
cd banking-frontend
npm install
```

## Configuration

Edit `src/environments/environment.ts`:

```ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8085'
};
```

## Running the Application

```bash
ng serve
```

The app runs at `http://localhost:4200/`.

## Project Structure

```
src/
├── app/
│   ├── login/
│   ├── navbar/
│   ├── dashboard/
│   ├── customers/
│   ├── customer-accounts/
│   ├── edit-customers/
│   ├── new-customers/
│   ├── accounts/
│   ├── users/
│   ├── user-list/
│   ├── pages/
│   ├── settings/
│   ├── services/
│   ├── guards/
│   └── app-routing.module.ts
│   └── ....
├── assets/
└── environments/
```

## Pages & Components

Each component folder should include three files (.ts, .html, .css) and a placeholder for screenshots:

* **LoginComponent**

![image](https://github.com/user-attachments/assets/a9c03f0a-32c9-4768-a90f-59e6d69b2c4a)

* **DashboardComponent**

![image](https://github.com/user-attachments/assets/b7ee0496-7f50-4a6c-be16-a30082355a85)

* **CustomersComponent**

 ![image](https://github.com/user-attachments/assets/b0c7c63c-5df2-4443-9331-2e10da19c8e2)

* **NewCustomerComponent**

 ![image](https://github.com/user-attachments/assets/0638ea45-f529-4745-a948-8e00ed5ebc35)

* **EditCustomerComponent**

![image](https://github.com/user-attachments/assets/a2a71792-6108-44a8-9c56-cfc4dbb9a35f)


* **CustomerAccountsComponent**

![image](https://github.com/user-attachments/assets/c782c7af-f00c-43ee-9e7a-1b4426380c9f)

* **AccountsComponent**

![image](https://github.com/user-attachments/assets/9657b55d-b9ee-4e07-bc09-b5721f22258f)

* **CreateCurrentAccountComponent**

![image](https://github.com/user-attachments/assets/42c6ff6d-94f9-4866-9b7e-4dbe466dc2ad)

  
* **CreateSavingAccountComponent**

![image](https://github.com/user-attachments/assets/e6ed2f8e-022b-4762-97de-87b763b0776a)

* **UserListComponent**

![image](https://github.com/user-attachments/assets/06d17e41-c6a8-45a9-b635-6eb86b67d8eb)

* **NewUserComponent**

![image](https://github.com/user-attachments/assets/e7520461-e31d-4972-b349-0ea0d88c6ae5)

* **SettingsComponent**

![image](https://github.com/user-attachments/assets/32cc4986-a59a-4113-b44f-77f270891ffd)


## Navigation Bar

The `navbar.component.html` implements the following menu items:

```html
<nav class="navbar ...">
  <!-- Dashboard -->(ADMIN)
  <!-- Accounts Dropdown -->(ADMIN)
  <!-- Customers Dropdown -->(ADMIN,USER)
  <!-- Operations Link -->(ADMIN,USER)
  <!-- Users Link --> (ADMIN)
  <!-- Profile Dropdown -->(ADMIN,USER)
</nav>
```

Admin dashboard uses **ng2-charts**. Key charts:

1. **Accounts by Type & Average Balance by Account Type**

   ![image](https://github.com/user-attachments/assets/abc82312-6f1f-4646-b68b-357dccc4604d)

2. **Account Creations Over Time**

   ![image](https://github.com/user-attachments/assets/b447b163-2d8b-4551-b475-e13deb677874)

3. **Transactions by Day of Week**

   ![image](https://github.com/user-attachments/assets/dfce22ac-98e1-4b52-a97d-c4b49280d122)

5. **Top 5 Customers by Balance**

   ![image](https://github.com/user-attachments/assets/28d82a2f-223d-4492-bca3-37ab2db577e9)

6. **Recent Operations Feed**

   ![image](https://github.com/user-attachments/assets/3ab74c99-48e7-4e1f-bfb9-b1b405e7c5a6)


Refer to Chart.js examples in `dashboard.component.ts` and HTML.

## Styling & Theming

Custom CSS in `styles.css` and component-level `.css` files:

* Variables for neon colors and gradients
* Keyframe animations for glow and pulse
* 3D transforms on buttons and cards
* Glassmorphism effects with backdrop-filter

![image](https://github.com/user-attachments/assets/4f7b66d6-7e53-4f26-ae34-e4f289b0e166)


# Conclusion

This comprehensive banking management project brings together a robust Backend (Spring Boot, JPA, JWT, Swagger) and a modern Frontend (Angular, Bootstrap, ChartJS) to deliver a full end-to-end solution that enables:

* Centralized management of customers, accounts (current & savings), and financial transactions.
* Strong security through Spring Security and JWT, with fine-grained role-based access control.
* Smooth user experience via a responsive, cyberpunk-themed UI, 3D animations, and an interactive dashboard.
* Actionable insights powered by key metrics (account creation trends, transaction volumes, top customers, average balances).
* Extensibility through a modular architecture, making it easy to add future features (notifications, report exports, multi-currency support).

