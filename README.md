# Claims API — Insurance Claims Management (Spring Boot)

**Claims API** is a small Spring Boot REST service that simulates an insurance claims module.
It allows users to create claims, browse them with pagination, fetch details, update claim status using business rules, and delete claims.

The project is designed as a clean, portfolio-ready backend example: layered architecture, validation, consistent error handling, and Swagger/OpenAPI documentation.

---

## Tech stack
- **Java 17**
- **Spring Boot 3** (Web, Validation)
- **Spring Data JPA** + Hibernate
- **H2 Database** (in-memory for local development)
- **OpenAPI / Swagger UI** (springdoc)
- **Gradle**
- **Testing**: Spring Boot Test, JUnit 5, MockMvc

---

## Key features
- CRUD operations for claims
- **Status workflow with business rules** (controlled transitions)
- Request validation (Bean Validation)
- Global exception handling with consistent JSON error format
- Pagination & sorting via query params (`page`, `size`, `sort`)
- Swagger UI for manual API testing

---

## Claim status workflow (business rules)

Supported statuses:
- `NEW`
- `IN_REVIEW`
- `APPROVED`
- `REJECTED`

Allowed transitions:
- `NEW -> IN_REVIEW` or `NEW -> REJECTED`
- `IN_REVIEW -> APPROVED` or `IN_REVIEW -> REJECTED`
- `APPROVED` and `REJECTED` are terminal (no transitions)

If an invalid transition is requested (e.g. `NEW -> APPROVED`), the API returns **400 Bad Request**.

---

## Architecture (high-level)
The application follows a simple layered architecture:

- **Controller layer** (`controller/`)  
  Exposes REST endpoints, validates requests (`@Valid`) and delegates to the service layer.

- **Service layer** (`service/`)  
  Contains business logic such as the claim **status workflow** and orchestrates repository calls.

- **Repository layer** (`repository/`)  
  Spring Data JPA repositories + filtering (Specifications).

- **Domain model** (`model/`)  
  JPA entities and enums (`ClaimStatus`) with explicit transition rules.

- **DTOs** (`dto/`)  
  Request/response models to keep API contracts stable and avoid exposing entities.

- **Error handling** (`exception/`)  
  Centralized `@RestControllerAdvice` that returns consistent JSON error responses (400/404/500).
---

## Run locally

### Requirements
- Java **17** installed (project uses toolchain)
- No database setup required (H2 is used by default)

### Start the application
```bash
./gradlew bootRun

./gradlew test