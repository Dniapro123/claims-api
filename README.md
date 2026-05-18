

# Claims API

A portfolio backend project built with Java and Spring Boot.  
The application simulates a small insurance claims management module with customers, claims, status workflow, validation, relational persistence, database migrations, and integration tests.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway
- Docker Compose
- Gradle
- Lombok
- Swagger / OpenAPI
- JUnit 5
- MockMvc
- GitHub Actions CI

## Features

- Customer management
- Claim management
- Customer-to-Claim relationship
- Claim status workflow
- Request validation
- Global exception handling
- Pagination and sorting
- Filtering claims by status
- PostgreSQL database
- Versioned database migrations with Flyway
- Integration tests for REST API endpoints
- CI pipeline with GitHub Actions

## Domain Overview

A customer can have multiple insurance claims.

A claim contains:

- title
- description
- amount
- status
- creation timestamp
- assigned customer

Claim statuses follow a controlled workflow.

## API Endpoints

### Customers

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/customers` | Create customer |
| GET | `/api/customers` | List customers |
| GET | `/api/customers/{id}` | Get customer by ID |
| PUT | `/api/customers/{id}` | Update customer |
| DELETE | `/api/customers/{id}` | Delete customer |

### Claims

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/claims` | Create claim |
| GET | `/api/claims` | List claims |
| GET | `/api/claims/{id}` | Get claim by ID |
| PUT | `/api/claims/{id}/status` | Update claim status |
| DELETE | `/api/claims/{id}` | Delete claim |

## Example Request

Create customer:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+48123123123"
}
````

Create claim:

```json
{
  "title": "Broken laptop",
  "description": "Screen does not work",
  "amount": 1200,
  "customerId": 1
}
```

Update claim status:

```json
{
  "status": "IN_REVIEW"
}
```

## Error Response Example

```json
{
  "timestamp": "2026-05-18T07:35:27.695Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/customers",
  "fieldErrors": [
    {
      "field": "email",
      "message": "must be a well-formed email address"
    }
  ]
}
```

## Local Development

### Prerequisites

* Java 17
* Docker
* Docker Compose

### Start PostgreSQL

```bash
docker compose up -d
```

### Run the application

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

On Windows PowerShell:

```powershell
.\gradlew.bat bootRun --args='--spring.profiles.active=dev'
```

### Run tests

```bash
./gradlew clean test
```

On Windows PowerShell:

```powershell
.\gradlew.bat clean test
```

## Database Migrations

Database schema is managed by Flyway.

Migration files are located in:

```text
src/main/resources/db/migration
```

Current migrations:

* `V1__create_claims_table.sql`
* `V2__create_customers_and_link_claims.sql`

Hibernate is configured with:

```yaml
spring.jpa.hibernate.ddl-auto: validate
```

This means Hibernate validates the schema but does not generate or update tables automatically.

## Swagger / OpenAPI

After starting the application, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI docs:

```text
http://localhost:8080/v3/api-docs
```

## CI

This project uses GitHub Actions to run tests automatically on every push and pull request to `main`.

Workflow file:

```text
.github/workflows/ci.yml
```

## Project Structure

```text
src
├── main
│   ├── java/com/ulad/claims
│   │   ├── controller
│   │   ├── dto
│   │   ├── exception
│   │   ├── model
│   │   ├── repository
│   │   └── service
│   └── resources
│       ├── db/migration
│       ├── application.yml
│       ├── application-dev.yml
│       └── application-test.yml
└── test
    └── java/com/ulad/claims
```

## Why This Project

This project was created as a backend portfolio application.
The goal was to go beyond a simple CRUD app and demonstrate practical backend skills:

* designing REST APIs
* working with relational data
* handling validation and errors
* managing database schema changes
* writing integration tests
* using Docker and CI
* keeping a clean layered architecture

## Author

Uladzislau Budziankou






