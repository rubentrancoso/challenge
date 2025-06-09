**Showcase Guide: User API built with Spring Boot and Java 8**

---

This document presents a clean and functional implementation of a RESTful API for user registration, login, and profile access.

The objective was to create a backend system that covers a simple yet complete user authentication flow:

- Register a user
- Login with credentials
- Access the user profile using a token (JWT)

What follows is an overview of how the project fulfills each part of this flow, alongside additional improvements introduced to ensure robustness, security, and clarity.

---

### ✉️ User Registration (`POST /user`)

**Input:**

```json
{
  "name": "João da Silva",
  "email": "joao@silva.org",
  "password": "hunter2",
  "phones": [
    {
      "number": "987654321",
      "ddd": "21"
    }
  ]
}
```

The API accepts a JSON payload with name, email, password, and a list of phone numbers. Once submitted:

- The service (`SrvUser.java`) verifies if the email already exists.
- The password is hashed using a non-reversible encryption algorithm.
- A new user is persisted (`User.java`, `UserRepository.java`) with timestamps and a generated UUID.
- A JWT token is created (`JwtConfig.java`, `JWTAuthenticationFilter.java`) and included in the response.

**Output:**

```json
{
  "id": "UUID",
  "name": "João da Silva",
  "email": "joao@silva.org",
  "phones": [...],
  "created": "...",
  "modified": "...",
  "last_login": "...",
  "token": "JWT_TOKEN"
}
```

---

### 🔐 User Login (`POST /login`)

**Input:**

```json
{
  "email": "joao@silva.org",
  "password": "hunter2"
}
```

A user logs in with email and password. If credentials are valid:

- The token is regenerated and saved (`SrvAuth.java`).
- The last login timestamp is updated.
- A full user response is returned (same structure as in registration).

**Output:**
Same structure as registration.

**On invalid credentials:**

```json
{"mensagem": "Usuário e/ou senha inválidos"}
```

---

### 🔒 Secured Profile Access (`GET /user/{id}`)

**Input:**

- Path parameter: user ID
- Header: `Authorization: Bearer <token>`

This is a protected endpoint. It validates:

- That the JWT token is present in the `Authorization` header (`JWTAuthorizationFilter.java`)
- That the token matches the one persisted for the user
- That the last login is within the past 30 minutes (`SecurityConfig.java`)

**Output:**
Same structure as user registration.

**On failure:**

```json
{"mensagem": "Não autorizado"}
```

or

```json
{"mensagem": "Sessão inválida"}
```

---

### 🔄 Error Handling & JSON Format

All errors in the system follow the structure:

```json
{"mensagem": "..."}
```

This is handled through message constants (`Message.java`) and centralized exception responses in the controller layer (`ItAuthEndpoint.java`, `ItUserEndpoint.java`).

---

### 📊 Persistence & Data Modeling

- The system uses **H2** as an in-memory database (`application.yml`).
- JPA + Hibernate handle the persistence layer (`User.java`, `Phone.java`).
- Audit fields like `created`, `modified`, and `last_login` are automatically updated (`Auditable.java`, `AuditorAwareImpl.java`).
- UUIDs are generated via `UUIDGen.java`.

---

### ⚖️ Security Architecture

- Passwords are stored with `BCrypt` (`SrvUser.java`, `SecurityConfig.java`)
- Tokens are JWTs signed with a secret key (`JwtConfig.java`)
- The Spring Security configuration defines filters and guards access to protected endpoints (`WebSecurity.java`, `SecurityConfig.java`)
- All access is stateless and based on headers

---

### ⚡ Build, Structure & Testing

- The project uses **Gradle** for build automation (`build.gradle`)
- Organized in layers: controller (`ItAuthEndpoint.java`, `ItUserEndpoint.java`), service (`SrvUser.java`, `SrvAuth.java`), repository (`UserRepository.java`), config (`JpaConfig.java`, `JwtConfig.java`, `SecurityConfig.java`), and model (`User.java`, `Phone.java`)
- Includes both **unit** and **integration tests** (`UtSuite.java`, `UtUUID.java`, `ItSrvUser.java`, `ItSrvAuth.java`)
- Utilities include JSON helpers (`Json.java`) and UUID generation (`UUIDGen.java`)

---

### ✨ Additional Improvements Beyond the Brief

- **JWT Authentication:**  
  Implemented using `JWTAuthenticationFilter.java` and `JWTAuthorizationFilter.java`. On successful login, a JWT is created and returned. On each request, the token is validated to ensure authenticity and access control.

- **Audit Support:**  
  Using `Auditable.java` and `AuditorAwareImpl.java`, the system automatically tracks and populates audit fields like `created`, `modified`, and `last_login`.

- **30-minute session validation:**  
  The logic inside `SrvUser.java` checks the time difference between `last_login` and the current timestamp. If it exceeds 30 minutes, the session is considered invalid and the request is rejected.

- **Layered, modular architecture:**  
  The code is separated into controllers, services, repositories, configs, and models. This ensures better maintainability and clarity.

- **Consistent error handling:**  
  All errors follow a uniform JSON format with localized messages defined in `Message.java`. Custom exceptions ensure predictable behavior on error scenarios.

- **Test coverage:**  
  The test suite includes unit and integration tests that validate service behavior and endpoint correctness under different scenarios.

---


### 🌐 Ready for Production Scenarios

While simple by nature, this showcase is designed to reflect real-world backend development standards, including stateless authentication, auditability, layered separation of concerns, and automated build/testing pipelines.

---

The repository is clean, well-documented, and easily extendable to support features like refresh tokens, email confirmation, or multi-role access in the future.
