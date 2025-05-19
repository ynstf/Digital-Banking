# Digital-Banking

## E-Banking Application Backend

A robust Spring Boot application providing RESTful API services for a complete banking system with account management, customer management, and transaction processing capabilities.

### Overview

This backend system powers a digital banking application with the following features:

- Customer management (create, update, delete, search)
- Bank account management (current accounts and saving accounts)
- Transaction operations (credit, debit, transfers)
- Account history and operations tracking
- JWT-based authentication and authorization

### Tech Stack

- **Framework**: Spring Boot 3.4.5
- **Java Version**: 21
- **Database**: MySQL
- **ORM**: Hibernate/JPA
- **Security**: Spring Security with JWT
- **API Documentation**: Swagger/OpenAPI
- **Mapping**: MapStruct
- **Build Tool**: Maven

### Architecture

The application follows a layered architecture:

- **Entities**: JPA entities representing the database model
- **Repositories**: Data access layer using Spring Data JPA
- **DTOs**: Data Transfer Objects for API exchanges
- **Services**: Business logic implementation
- **Controllers**: RESTful API endpoints
- **Security**: Authentication and authorization configuration

### Database Model

#### Key Entities
- **Customer**: Stores customer information
- **BankAccount**: Abstract base class for accounts
  - **CurrentAccount**: Standard account with overdraft facility
  - **SavingAccount**: Savings account with interest rate
- **AccountOperation**: Records all transactions on accounts

### Getting Started

#### Prerequisites
- JDK 21
- MySQL Database
- Maven

#### Configuration

Database connection settings in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/E-BANK?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
```

#### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   
The application will start on port 8085 (configurable in application.properties).

### Security

The application uses JWT token-based authentication with the following features:

- Token-based stateless authentication
- Role-based access control (USER and ADMIN roles)
- Method-level security with Spring Security annotations
- CORS configuration for frontend integration

Default users:
- Username: `user1` / Password: `12345` (USER role)
- Username: `admin` / Password: `12345` (USER and ADMIN roles)

### API Endpoints

#### Authentication
- `POST /auth/login`: Authenticate and receive JWT token

#### Customers
- `GET /customers`: List all customers
- `GET /customers/{id}`: Get customer by ID
- `POST /customers`: Create new customer
- `PUT /customers/{id}`: Update customer
- `DELETE /customers/{id}`: Delete customer
- `GET /customers/search`: Search customers by keyword
- `GET /customers/{customerId}/accounts`: Get accounts for a customer

#### Bank Accounts
- `GET /accounts`: List all accounts
- `GET /accounts/{accountId}`: Get account details
- `GET /accounts/{accountId}/operations`: Get account operations
- `GET /accounts/{accountId}/pageOperations`: Get paginated account operations
- `POST /accounts/debit`: Perform debit operation
- `POST /accounts/credit`: Perform credit operation
- `POST /accounts/transfer`: Transfer between accounts

### Test Data

The application creates sample data at startup through the `CommandLineRunner` bean:
- 3 sample customers
- Current and Saving accounts for each customer
- Sample transactions on each account

### API Documentation

API documentation is available via Swagger UI at:
```
http://localhost:8085/swagger-ui/index.html
```

### Configuration Options

Key properties that can be configured in `application.properties`:
- `server.port`: Application port (default: 8085)
- `spring.jpa.hibernate.ddl-auto`: Database schema generation strategy
- `spring.jpa.show-sql`: SQL query logging

### Dependencies

- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- Spring Boot Starter Validation
- Spring Boot Starter OAuth2 Resource Server
- MySQL Connector
- MapStruct
- Lombok
- SpringDoc OpenAPI (Swagger)
- Bootstrap (for Thymeleaf templates)

### Project Structure

```
backend/
├── src/main/java/ma/banking/backend/
│   ├── dtos/
│   ├── entities/
│   ├── enums/
│   ├── exceptions/
│   ├── mappers/
│   ├── repositories/
│   ├── security/
│   ├── services/
│   ├── web/
│   └── Bknd2Application.java
└── src/main/resources/
    └── application.properties
```

### Key Features

1. **Polymorphic Account Management**:
   - Support for different account types with inheritance
   - Type-specific operations and properties

2. **Transaction Management**:
   - Credit/debit operations with validation
   - Fund transfers between accounts
   - Insufficient balance protection

3. **Customer Management**:
   - Full CRUD operations
   - Customer search functionality

4. **Security**:
   - JWT-based authentication 
   - Role-based authorization
   - Method-level security

5. **DTO Pattern**:
   - Clean separation between API and domain model
   - MapStruct for efficient mapping



## E-Banking Application Frontend

### Overview

This E-Banking application is a comprehensive web-based platform built with Angular that allows users to manage banking operations. The application provides a secure interface for customers and administrators to access account information, perform transactions, and manage customer data.

### Features

#### Authentication & Authorization
- **User Authentication**: Secure login system with JWT token-based authentication
- **Role-Based Access Control**: Different access levels for regular users and administrators
- **Persistent Sessions**: Remembers user login sessions using local storage

#### Account Management
- **Account Search**: Look up accounts by ID
- **Account Details**: View account balance and transaction history
- **Pagination**: Navigate through transaction history pages

#### Transaction Operations
- **Debit Operations**: Withdraw funds from accounts
- **Credit Operations**: Deposit funds into accounts
- **Transfer Operations**: Move funds between accounts

#### Customer Management
- **Customer Listing**: View all customers with search functionality
- **Customer Creation**: Add new customers to the system (admin only)
- **Customer Details**: View customer information and associated accounts
- **Customer Deletion**: Remove customers from the system (admin only)

### Technical Stack

- **Framework**: Angular 12+
- **UI Components**: Bootstrap with Bootstrap Icons
- **API Communication**: HttpClient with Interceptors
- **Form Handling**: Reactive Forms
- **Routing**: Angular Router with Guards for protected routes
- **Authentication**: JWT Token-based auth with role-based access control

### Project Structure

```
frontend/src/app/
│
├── accounts/                # Account management components
├── admin-template/          # Admin dashboard layout
├── customer-accounts/       # Customer accounts listing
├── customers/               # Customer management components
├── guards/                  # Authentication and authorization guards
├── interceptors/            # HTTP interceptors for auth tokens
├── login/                   # Authentication components
├── model/                   # Data models and interfaces
├── navbar/                  # Navigation component
├── new-customer/            # Customer creation component
├── not-authorized/          # Access denied component
├── services/                # API services
│   ├── accounts.service.ts  # Account operations service
│   ├── auth.service.ts      # Authentication service
│   └── customer.service.ts  # Customer operations service
│
├── app-routing.module.ts    # Application routes
└── app.module.ts            # Main application module
```

### Setup Instructions

#### Prerequisites
- Node.js (v14+)
- npm (v6+)
- Angular CLI

#### Installation

1. Clone the repository
   ```bash
   git clone <repository-url>
   cd <repository-directory>/frontend
   ```

2. Install dependencies
   ```bash
   npm install
   ```

3. Configure the environment
   
   Edit `src/environments/environment.ts` to set your backend API URL:
   ```typescript
   export const environment = {
     production: false,
     backendHost: "http://localhost:8085"  // Update with your backend URL
   };
   ```

4. Run the development server
   ```bash
   ng serve
   ```

5. Navigate to `http://localhost:4200/` in your browser

#### Building for Production

```bash
ng build --configuration production
```

The build artifacts will be stored in the `dist/` directory.

### Authentication Flow

1. User enters credentials on the login page
2. Upon successful authentication, a JWT token is stored in local storage
3. The HTTP interceptor automatically attaches the token to all API requests
4. Role-based guards protect routes based on user permissions
5. Token expiration is handled by intercepting 401 responses

### User Roles

- **User**: Can view accounts and perform basic operations
- **Admin**: Can manage customers, create new accounts, and perform all operations

### API Endpoints

The frontend communicates with the following API endpoints:

- Authentication: `POST /auth/login`
- Customers: 
  - List: `GET /customers`
  - Search: `GET /customers/search?keyword=`
  - Create: `POST /customers`
  - Delete: `DELETE /customers/{id}`
- Accounts:
  - Details: `GET /accounts/{id}/pageOperations`
  - Debit: `POST /accounts/debit`
  - Credit: `POST /accounts/credit`
  - Transfer: `POST /accounts/transfer`
  - Customer accounts: `GET /customers/{id}/accounts`

### Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
