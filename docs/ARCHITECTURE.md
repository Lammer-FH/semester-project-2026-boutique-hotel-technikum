# Architecture Overview
This document serves as a critical, living template designed to equip agents with a rapid and comprehensive understanding of the codebase's architecture, enabling efficient navigation and effective contribution from day one. Update this document as the codebase evolves.

## 1. Project Structure
This section provides a high-level overview of the project's directory and file structure, categorised by architectural layer or major functional area. It is essential for quickly navigating the codebase, locating relevant files, and understanding the overall organization and separation of concerns. The structure below is the **target architecture** to implement the backend against; the codebase may still be in transition.

```
[Project Root]/
├── backend/                    # Server-side code (Spring Boot 3.4 + Java 21)
│   ├── pom.xml                 # Parent POM (multi-module aggregator)
│   ├── mvnw / mvnw.cmd         # Maven wrapper scripts
│   ├── .mvn/maven.config       # Default Maven flags (-pl hotel-main -am)
│   ├── .env / .env-example     # Local database credentials
│   │
│   ├── hotel-main/             # Runnable Spring Boot application module
│   │   ├── pom.xml             # Dependencies: web, JPA, Flyway, MySQL, validation, Lombok
│   │   └── src/
│   │       ├── main/
│   │       │   ├── java/at/fhtw/hotel/
│   │       │   │   ├── HotelApplication.java              # @SpringBootApplication entry point
│   │       │   │   ├── config/
│   │       │   │   │   ├── HotelProperties.java            # @ConfigurationProperties (app.hotel.*: name, email, phone, address{street,city,postalCode,country}, directions{byTrain,byCar,parking}, breakfastPricePerPerson)
│   │       │   │   │   ├── WebConfig.java                  # CORS, static resource mapping
│   │       │   │   │   └── ExceptionHandler.java           # @ControllerAdvice, central error handling
│   │       │   │   ├── controller/                         # Web/API layer controllers (REST endpoints)
│   │       │   │   │   ├── RoomController.java             # GET /rooms, GET /rooms/{id}
│   │       │   │   │   ├── BookingController.java          # POST /bookings, GET /bookings/{id}
│   │       │   │   │   ├── AvailabilityController.java     # GET /rooms/{id}/availability
│   │       │   │   │   ├── ExtraController.java            # GET /extras
│   │       │   │   │   └── HealthController.java           # GET /, GET /health
│   │       │   │   ├── dto/                                # Request/response DTOs
│   │       │   │   │   ├── request/                        # Inbound DTOs with Bean Validation
│   │       │   │   │   │   ├── BookingRequest.java
│   │       │   │   │   │   └── AvailabilityRequest.java
│   │       │   │   │   └── response/                       # Outbound DTOs
│   │       │   │   │       ├── RoomResponse.java
│   │       │   │   │       ├── BookingResponse.java
│   │       │   │   │       ├── AvailabilityResponse.java
│   │       │   │   │       ├── ExtraResponse.java
│   │       │   │   │       ├── PaginatedResponse.java
│   │       │   │   │       └── ErrorResponse.java
│   │       │   │   ├── service/                            # Application services (Spring-managed use cases)
│   │       │   │   │   ├── RoomService.java
│   │       │   │   │   ├── BookingService.java
│   │       │   │   │   ├── AvailabilityService.java
│   │       │   │   │   └── ExtraService.java
│   │       │   │   ├── domain/                             # Domain rules, policies, and exceptions (no Spring)
│   │       │   │   ├── model/                              # Domain models (no persistence annotations)
│   │       │   │   │   ├── Room.java
│   │       │   │   │   ├── Booking.java
│   │       │   │   │   ├── Extra.java
│   │       │   │   │   └── RoomImage.java
│   │       │   │   ├── persistence/entity/                 # JPA entities (persistence models)
│   │       │   │   │   ├── RoomEntity.java
│   │       │   │   │   ├── BookingEntity.java
│   │       │   │   │   ├── ExtraEntity.java
│   │       │   │   │   ├── RoomImageEntity.java
│   │       │   │   │   └── RoomExtraEntity.java
│   │       │   │   ├── repository/                         # Domain-owned repository interfaces
│   │       │   │   │   ├── RoomRepository.java
│   │       │   │   │   ├── BookingRepository.java
│   │       │   │   │   └── ExtraRepository.java
│   │       │   │   ├── persistence/repository/             # Spring Data JPA repository implementations
│   │       │   │   │   ├── JpaRoomRepository.java
│   │       │   │   │   ├── JpaBookingRepository.java
│   │       │   │   │   └── JpaExtraRepository.java
│   │       │   │   ├── mapper/                             # Entity <-> Domain model mappers
│   │       │   │   │   ├── RoomMapper.java
│   │       │   │   │   ├── BookingMapper.java
│   │       │   │   │   └── ExtraMapper.java
│   │       │   │   └── config/
│   │       │   │       └── JpaConfig.java
│   │       │   └── resources/
│   │       │       ├── application.yaml                    # Main config (datasource, Flyway, custom props)
│   │       │       ├── application-local.example.yaml      # Local dev override template
│   │       │       └── logback-spring.xml                  # Logging config (SLF4J + Logback)
│   │       └── test/
│   │           └── java/at/fhtw/hotel/
│   │               ├── HotelApplicationTests.java          # Context load smoke test
│   │               ├── controller/                         # Controller unit tests (MockMvc)
│   │               │   ├── HealthControllerTest.java
│   │               │   ├── RoomControllerTest.java
│   │               │   ├── BookingControllerTest.java
│   │               │   ├── AvailabilityControllerTest.java
│   │               │   └── ExtraControllerTest.java
│   │               ├── service/                            # Service/use-case unit tests
│   │               │   ├── RoomServiceTest.java
│   │               │   ├── BookingServiceTest.java
│   │               │   ├── AvailabilityServiceTest.java
│   │               │   └── ExtraServiceTest.java
│   │               └── persistence/repository/             # Repository integration tests
│   │                   ├── JpaRoomRepositoryTest.java
│   │                   ├── JpaBookingRepositoryTest.java
│   │                   └── JpaExtraRepositoryTest.java
│   │
│   ├── hotel-util/              # Shared utility library (plain JAR)
│   │   ├── pom.xml              # Dependencies: SLF4J, Lombok, JUnit5
│   │   └── src/main/java/at/fhtw/hotel/util/
│   │       ├── Log.java         # Static convenience logging utility
│   │       └── Logger.java      # Instance-based SLF4J wrapper
│   │
│   ├── db/migration/            # Flyway database migrations (versioned)
│   │   ├── V1__init_schema.sql  # Schema: rooms, room_images, extras, room_extras, bookings
│   │   └── V2__seed_data.sql    # Seed data: 2 rooms, 2 extras, images
│   │
│   └── docker/
│       └── docker-compose.yaml  # MySQL 8.1 service for local development
│
├── frontend/                    # Client-side code (Ionic 8 + Vue.js 3.4)
│   ├── src/                     # Main source code
│   │   ├── components/          # Atomic Design components
│   │   │   ├── atoms/           # Smallest units (Button, Icon, Input)
│   │   │   ├── molecules/       # Groups of atoms (SearchBar, FormField)
│   │   │   └── organisms/       # Complex UI sections (RoomCard, Header, Pagination)
│   │   ├── pages/               # Page-level views (HomePage, RoomsPage, BookingPage, ConfirmationPage)
│   │   ├── router/              # Vue Router configuration
│   │   ├── stores/              # Pinia state management stores
│   │   │   ├── roomStore.js     # Room data, pagination state
│   │   │   ├── bookingStore.js  # Booking flow state, selected dates, guest data
│   │   │   └── availabilityStore.js  # Availability check state
│   │   ├── services/            # API interaction layer (Fetch API)
│   │   │   ├── api.js           # Base fetch wrapper with error handling
│   │   │   ├── roomService.js   # Room API calls
│   │   │   ├── bookingService.js# Booking API calls
│   │   │   └── extraService.js  # Extras API calls
│   │   ├── assets/              # Images, fonts, static assets
│   │   └── utils/               # Shared utility functions
│   ├── public/                  # Publicly accessible assets (index.html, static images)
│   ├── package.json             # Frontend dependencies and scripts
│   └── tests/                   # Frontend unit and E2E tests
│
├── docs/                        # Project documentation
│   ├── ARCHITECTURE.md          # This document
│   ├── API_SPECIFICATION.md     # REST API specification
│   ├── DB_DESIGN.md             # Database schema documentation
│   ├── Paperprototype.pdf       # Paper prototype mockups
│   └── pre-project/             # Pre-project specification documents
│
├── best-practices/              # Engineering standards and implementation rules
│   └── best-practices.md        # Clean Architecture, Clean Code, Atomic Design, etc.
│
├── team.md                      # Team member assignments
├── AI_USAGE.md                  # AI tool usage documentation
├── AGENTS.md                    # AI agent instructions
├── README.md                    # Project overview and quick start guide
└── .gitignore                   # Specifies intentionally untracked files to ignore
```

## 2. Architectural Principles and Responsibilities

This project applies Clean Architecture with strict dependency rules:

- **Web/API layer** depends only on the Domain layer.
- **Domain layer** is pure Java (no framework annotations or dependencies).
- **Infrastructure layer** depends on the Domain layer and implements repository interfaces.

**Layer responsibilities:**
- **Web/API**
  - Request/response mapping, HTTP status codes, and error payloads
  - Input format validation (Bean Validation, date format)
  - Pagination defaults and max size enforcement
  - Delegation to domain services
- **Application**
  - Orchestrates use cases and transactions (Spring-managed services)
  - Depends on domain models and domain-owned repository interfaces
- **Domain**
  - Business rules and invariants (date range validity, email confirmation match, guest count limits)
  - Availability overlap rule and booking creation orchestration
  - Pricing calculation (room rate + optional breakfast rate)
  - Exception types that map to API error codes
- **Infrastructure**
  - JPA entities, repositories, and database mappings
  - Spring Data repository implementations (persistence layer)
  - Flyway migrations and DB constraints

## 3. High-Level System Diagram

```
┌──────────────┐     ┌─────────────────────────────────────────────┐     ┌──────────────┐
│              │     │              Frontend (Ionic 8 + Vue 3)      │     │              │
│              │     │                                              │     │   MySQL 8.1  │
│   Guest      │────>│  ┌─────────┐  ┌──────────┐  ┌────────────┐ │────>│   Database   │
│  (Browser)   │     │  │  Pages  │  │  Stores  │  │  Services  │ │     │              │
│              │     │  │(Vue SFC)│  │ (Pinia)  │  │(Fetch API) │ │     │  ┌────────┐  │
│  (Mobile /   │<────│  └─────────┘  └──────────┘  └─────┬──────┘ │<────│  │ rooms  │  │
│   Desktop)   │     │              │         │           │         │     │  ├────────┤  │
│              │     │              │         │           │         │     │  │room_img│  │
└──────────────┘     └──────────────┼─────────┼───────────┼─────────┘     │  ├────────┤  │
                                    │         │           │               │  │ extras │  │
                                    │         ▼           ▼               │  ├────────┤  │
                                    │    ┌──────────────────────┐         │  │booking │  │
                                    │    │  Fetch HTTP Clients  │         │  └────────┘  │
                                    │    └──────────┬───────────┘         └──────────────┘
                                    │               │
                                    └───────────────┼─────────────────────┘
                                                    │
                                                    ▼
                              ┌─────────────────────────────────────────────┐
                              │         Backend (Spring Boot 3.4)           │
                              │                                              │
                              │  ┌──────────────────────────────────────┐   │
                              │  │         Web / API Layer             │   │
                              │  │  Controllers  │  DTOs               │   │
                              │  └──────────┬───────────────────────────┘   │
                              │             │                               │
                              │  ┌──────────▼───────────────────────────┐   │
                              │  │         Domain Layer                 │   │
                              │  │  Services (Use Cases)  │  Models     │   │
                              │  │  Repository Interfaces               │   │
                              │  └──────────┬───────────────────────────┘   │
                              │             │                               │
                              │  ┌──────────▼───────────────────────────┐   │
                              │  │     Infrastructure Layer             │   │
                              │  │  JPA Entities  │  Mappers  │  Repos  │   │
                              │  │  DB Config    │  Flyway              │   │
                              │  └──────────────────────────────────────┘   │
                              └─────────────────────────────────────────────┘
```

## 4. Core Components

### 4.1. Frontend

**Name:** Ionic 8 + Vue.js 3.4 SPA

**Description:** A mobile-first Single Page Application that provides the guest-facing hotel website. Built with Atomic Design methodology, it handles room browsing, availability checking, booking with review step, and detailed booking confirmation. State management uses Pinia stores, and all API communication uses the native Fetch API.

**Technologies:** Ionic 8.x, Vue.js 3.4.x, Pinia 2.1.x, Vue Router, JS Fetch API, Bootstrap Icons

**Deployment:** Static hosting (e.g., Netlify, Vercel, or served via Spring Boot's static resources)

### 4.2. Backend Services

#### 4.2.1. Boutique Hotel Booking API

**Name:** Hotel Booking REST API

**Description:** A monolithic Spring Boot 3.4 backend following Clean Architecture principles. Provides a RESTful API (Richardson Level 2) for room listing, availability checks, booking creation, and booking confirmation retrieval. The API is versioned under `/api/v1`. The architecture enforces strict separation between web, domain, and infrastructure layers per Clean Architecture dependency rules.

**Technologies:** Java 21, Spring Boot 3.4.x, Spring Data JPA (Hibernate), Spring Validation, Flyway, MySQL Connector, Lombok, Maven

**Deployment:** Executable JAR (`java -jar`) via Maven build. MySQL runs via Docker Compose during development.

**Internal Modules:**

- **`hotel-main`** — The runnable Spring Boot application containing all three Clean Architecture layers:
  - *Web/API Layer* — `@RestController` controllers, request/response DTOs with Bean Validation, and a central `@ControllerAdvice` exception handler for consistent error payloads.
  - *Domain Layer* — Pure Java domain models (no framework annotations), service classes implementing use cases, and repository interfaces defining the persistence contract.
  - *Infrastructure Layer* — JPA entity classes, mapper classes (domain <-> entity), and JPA repository implementations. Also includes database configuration and Flyway migration integration.

- **`hotel-util`** — A shared utility JAR providing SLF4J logging wrappers (`Log` and `Logger` classes). Has no Spring Boot dependency.

**API Rules (alignment with API specification):**
- **Pagination:** default `page=1`, `size=5`, max `size=5`; enforced in web layer before calling services.
- **Date handling:** use `LocalDate`; `check_in_date` inclusive, `check_out_date` exclusive; `check_out_date` must be after `check_in_date`.
- **Availability rule:** overlap when `existing.check_in_date < requested.check_out_date && existing.check_out_date > requested.check_in_date`.
- **Booking validation:** `confirm_email` must match `guest_email`; `guest_count` must be between 1 and `room.max_guests`.
- **Pricing:** `total_price = (room_rate * nights) + (breakfast_rate)` where `breakfast_rate = breakfast_per_person_per_day * guest_count * nights`.
- **Hotel properties:** booking confirmation response includes contact and directions from `HotelProperties` (`app.hotel.*`).

**JSON conventions:** Response and request JSON use `snake_case` per API spec. Configure Jackson naming strategy (e.g., `PropertyNamingStrategies.SNAKE_CASE`) to keep DTOs in Java camelCase while matching the API contract.

**Transactions:** Application services orchestrating booking creation and availability checks are the transaction boundary (`@Transactional`). Controllers remain thin and non-transactional.

## 5. Data Stores

### 5.1. Primary Database

**Name:** Boutique Hotel Database

**Type:** MySQL 8.1

**Purpose:** Stores all persistent data for the hotel booking application — room catalog, room images with ordering, bookable extras, and booking records with guest details.

**Schemas/Tables:**
  - `rooms` — Core room catalog (title, description, max_guests, base_price_per_night)
  - `room_images` — Ordered image gallery per room (file_name, alt_text, sort_order)
  - `extras` — Selectable extras catalog (code, title, description, icon_name)
  - `room_extras` — Many-to-many relationship joining rooms to extras
  - `bookings` — Reservation records with guest contact data, guest count, date range, breakfast preference

**Key Indexes:** Composite index `bookings(room_id, check_in_date, check_out_date)` for efficient availability overlap queries. Unique index `room_images(room_id, sort_order)` for deterministic image ordering.

**Migration Strategy:** Database schema and seed data are managed via Flyway versioned migrations located at `backend/db/migration/`. Migrations use MySQL-compatible SQL with `snake_case` naming.

## 6. External Integrations / APIs

No external third-party services are integrated. All hotel contact, address, directions, and breakfast pricing data (for booking confirmation) is configured as static application properties via `HotelProperties` (`app.hotel.*`). A potential future integration with Google Maps (for directions) is mentioned in the project specification but is decided against based on the post-MVP research for U5, which uses static text directions.

The required `app.hotel.*` properties are:
- `name`, `email`, `phone` — hotel contact info
- `address.street`, `address.city`, `address.postal-code`, `address.country` — hotel address for booking confirmation (U5)
- `directions.by-train`, `directions.by-car`, `directions.parking` — static text directions (U5)
- `breakfast-price-per-person` — per-person per-night breakfast rate for price calculation

These properties are injected into the booking confirmation response via `HotelProperties` in the domain service or response assembler.

## 7. Deployment & Infrastructure

**Cloud Provider:** Not yet determined (local development only)

**Key Services Used:**
  - **Application:** Spring Boot executable JAR (embedded Tomcat)
  - **Database:** MySQL 8.1 via Docker Compose (`backend/docker/docker-compose.yaml`)
  - **Frontend:** Static files (S3, Netlify, or served by Spring Boot)

**CI/CD Pipeline:** Not yet implemented (planned for future setup)

**Monitoring & Logging:** SLF4J + Logback with a custom `logback-spring.xml` configuration. Console appender with color-highlighted patterns. Logger `at.fhtw.hotel` at INFO level by default.

## 8. Security Considerations

**Authentication:** None (no user accounts or login required per project specification).

**Authorization:** None (all endpoints are public; booking requires only guest contact data).

**Data Encryption:** TLS/HTTPS in transit should be configured in production. No sensitive data at rest (no passwords/PII beyond guest name/email for booking). The `guest_email` field is booking-contact data only, not an account identifier.

**Key Security Practices:**
  - Bean Validation on all request DTOs for input sanitization.
  - Prepared statements via JPA/Hibernate (SQL injection prevention).
  - CORS configuration via Spring (`WebConfig.java`) restricted to allowed origins.
  - `open-in-view: false` in JPA config to prevent lazy loading issues and unintended session access.
  - No exposure of domain entities directly over the wire (DTO boundary).

## 9. Development & Testing Environment

**Local Setup:** See `README.md` for setup steps. Prerequisites: JDK 21, Maven (via wrapper), Docker Desktop.
  - Start MySQL: `docker compose -f backend/docker/docker-compose.yaml up -d`
  - Run backend: `cd backend && ./mvnw spring-boot:run`
  - Run frontend: `cd frontend && npm run dev`

**Testing Frameworks:** JUnit 5 (Jupiter), Spring MockMvc for controller tests, `@SpringBootTest` for integration tests.

**Code Quality Tools:** None configured yet (intellij-idea is the IDE; code style conventions documented in `best-practices/best-practices.md`).

**Testing Approach (TDD per AGENTS.md):**
  - Unit tests for domain services (pure Java, no Spring context needed) covering availability overlap, email confirmation mismatch, and pricing calculation
  - MockMvc tests for controllers (slice-testing the web layer)
  - `@SpringBootTest` integration tests for repository layer
  - Each user story must include basic unit tests + at least one integration test

## 10. Project Identification

**Project Name:** Boutique Hotel Technikum Booking Application

**Repository URL:** [https://github.com/technikum-wien/semester-project-2026-boutique-hotel-technikum](https://github.com/technikum-wien/semester-project-2026-boutique-hotel-technikum) (placeholder — use actual URL)

**Primary Contact/Team:** [See `team.md` for team member assignments] — FH Technikum Wien, Advanced Webtechnologies Course

**Date of Last Update:** 2026-05-12

## 11. Glossary / Acronyms

- **SPA:** Single Page Application — a web app that loads a single HTML page and dynamically updates content via JavaScript.
- **Clean Architecture:** A software design philosophy by Robert C. Martin emphasizing separation of concerns, dependency inversion, and framework-independence of business logic.
- **Atomic Design:** A methodology for creating design systems by breaking UI into hierarchical levels: atoms, molecules, organisms, templates, and pages.
- **Richardson Maturity Model (Level 2):** A REST maturity model where APIs use proper HTTP verbs (GET, POST, etc.) and resources are identified by URIs.
- **Pinia:** A state management library for Vue.js, the official replacement for Vuex.
- **Flyway:** A database migration tool that applies versioned SQL scripts in order.
- **DTO:** Data Transfer Object — an object that carries data between processes/layers, used at API boundaries to decouple external representation from internal models.
- **MockMvc:** A Spring testing utility for testing controllers without starting a full HTTP server.
- **JPA:** Jakarta Persistence API (formerly Java Persistence API) — the standard ORM specification for Java.
- **SLF4J:** Simple Logging Facade for Java — a logging abstraction layer.
