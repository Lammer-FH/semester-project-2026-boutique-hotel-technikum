# Architecture Overview

Boutique Hotel Technikum — a full-stack hotel booking SPA built with **Ionic + Vue 3** (frontend) and **Spring Boot 3.4 + Java 21** (backend), connected via a REST API. Both sides follow **Clean Architecture** with strict inward-facing dependency rules.

---

## High-Level System Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                          Frontend (Ionic + Vue 3)                       │
│                                                                         │
│   Pages ──▶ Components ──▶ Stores (Pinia) ──▶ API Clients ──▶ Models   │
│   (views)   (Atomic Design)  (state + actions)  (Axios)     (core TS)  │
│                                                                         │
│   Dependencies point inward: Presentation → Application → Infra → Core  │
└───────────────────────────────┬─────────────────────────────────────────┘
                                │  HTTP / REST (JSON, snake_case)
                                ▼
┌───────────────────────────────┬─────────────────────────────────────────┐
│                          Backend (Spring Boot)                          │
│                                                                         │
│   Controllers ──▶ Services ──▶ Domain Models ──▶ JPA Entities ──▶ MySQL │
│   (Web/API)       (Use Cases)   (pure Java)     (Persistence)           │
│                                                                         │
│   Dependencies point inward: Web → Domain ← Infrastructure              │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Backend — Spring Boot 3.4 / Java 21

### Layered Architecture

```
controller/        Web/API layer — HTTP, DTOs, validation, error handling
    │
    ▼
service/           Application layer — use cases, transaction boundaries
    │
    ▼
domain/            Domain layer — models, business rules, exceptions
    │
    ▼
persistence/       Infrastructure layer — JPA entities, repositories, Flyway
```

**Dependency rule:** Outer layers depend on inner layers. The **domain layer** has zero Spring/framework dependencies (pure Java records). The **persistence layer** depends on the domain, not the other way around.

### Request Flow (e.g. POST /bookings)

1. **Controller** receives `BookingRequest` DTO, validates with `@Valid` + custom `@EmailMatch` constraint
2. **Controller** maps DTO → `CreateBookingCommand` (application DTO)
3. **Service** executes use case: validates dates, checks guest count, detects overlapping bookings via JPQL, calculates price
4. **Service** calls `JpaBookingRepository` (Spring Data JPA)
5. **Repository** persists `BookingEntity` to MySQL
6. **Mapper** converts `BookingEntity` → domain `Booking` → `BookingResponse` DTO
7. **Controller** returns JSON (snake_case via Jackson config)

### Key Components

| Component | Role |
|-----------|------|
| `RoomController` / `BookingController` / `AvailabilityController` / `ExtraController` | REST endpoints under `/api/v1/` |
| `ExceptionHandler` (`@ControllerAdvice`) | Centralized error handling, maps `ErrorCode` → HTTP status |
| `BookingService` | Business logic: overlap detection, price calculation, validation |
| `AvailabilityService` | Date range validation + overlap query |
| `WebConfig` | CORS configuration, static image serving |
| `JacksonConfig` | Global snake_case JSON serialization |
| `HotelProperties` | `@ConfigurationProperties` for hotel contact/directions/breakfast price |

### Database (MySQL 8.1)

Managed by **Flyway** versioned migrations:
- `V1` — Schema: `rooms`, `room_images`, `extras`, `room_extras`, `bookings`
- `V2` — Seed data: 9 rooms, 8 extras, 27 room-extras, 9 images
- `V3` — Price breakdown columns on `bookings`

Key index: `idx_bookings_room_dates(room_id, check_in_date, check_out_date)` for overlap queries.

### API Endpoints

| Method | Endpoint | Purpose |
|--------|----------|---------|
| `GET` | `/api/v1/rooms?page=1&size=5` | Paginated room listing |
| `GET` | `/api/v1/rooms/{id}` | Single room details |
| `GET` | `/api/v1/rooms/{id}/availability?check_in=...&check_out=...` | Availability check |
| `POST` | `/api/v1/bookings` | Create booking |
| `GET` | `/api/v1/bookings/{id}` | Booking confirmation |
| `GET` | `/api/v1/extras` | List bookable extras |

---

## Frontend — Ionic + Vue 3.5 / TypeScript / Pinia

### Layered Architecture

```
pages/             Presentation layer — routed views, compose components
    │
    ▼
components/        Presentation layer — Atomic Design (atoms → molecules → organisms)
    │
    ▼
application/       Application layer — Pinia stores (state, actions, business logic)
    │
    ▼
infrastructure/    Infrastructure layer — Axios API clients + mappers
    │
    ▼
core/              Domain layer — TypeScript interfaces, constants, utilities
```

**Dependency rule:** Presentation depends on Application, which depends on Infrastructure, which depends on Core. Components **never** call API functions directly — they dispatch store actions.

### Request Flow (e.g. Room Listing)

1. **Page** (`RoomsPage.vue`) mounts → calls `roomStore.fetchRooms(page, size)`
2. **Store** (`roomStore.ts`) checks TTL cache → if miss, calls `roomApi.getRooms()`
3. **API client** (`roomApi.ts`) sends `GET /api/v1/rooms` via Axios
4. **Mapper** (`roomMapper.ts`) converts snake_case response → camelCase domain models
5. **Store** updates reactive state (`rooms`, `pagination`, `loading`)
6. **Page** renders `RoomCard` organisms via `v-for`

### Request Flow (e.g. Booking Creation)

1. **Availability dialog** → user selects dates → `availabilityStore.checkRoomAvailability()`
2. If available → **BookingDialog** opens with multi-step flow (details → review → confirmation)
3. User fills form → fields accumulate into `draft` (`Partial<BookingRequest>`) in `bookingStore`
4. On submit → `bookingStore.submitBooking()` validates → calls `bookingApi.createBooking()`
5. **API client** generates idempotency key from payload hash → sends `POST /bookings`
6. On success → navigates to `/bookings/:id/confirmation` → `BookingConfirmationPage` fetches and displays confirmation

### Atomic Design Components

| Level | Purpose | Examples |
|-------|---------|----------|
| **Atoms** | Base primitives | `BasePopup`, `BaseDatePicker`, `BaseErrorBanner`, `BaseSectionTitle` |
| **Molecules** | Composed blocks | `HeroBanner`, `BookingDialog`, `RoomAvailabilityFlow`, `DialogHeader` |
| **Organisms** | Complex sections | `RoomCard`, `FeatureGrid`, `StorySection`, `ContactStrip`, `ExtrasStrip` |
| **Layout** | Page scaffolding | `PageLayout`, `TheHeader`, `TheFooter` |
| **Pages** | Route views | `LandingPage`, `RoomsPage`, `BookingConfirmationPage`, `AboutPage`, `ImprintPage` |

### State Management (Pinia Stores)

| Store | Responsibility |
|-------|---------------|
| `roomStore` | Room list with TTL cache, prefetching, `AbortController` cancellation |
| `bookingStore` | Multi-step draft state, field validation, submit flow |
| `availabilityStore` | Per-room loading states, deduplication, cancellation |
| `extraStore` | Simple cache-once pattern |

### Styling

- **Design tokens** via CSS custom properties (`variables.css`): colors, typography, spacing, shadows
- **Scoped CSS** per component with BEM-like naming
- **Shared CSS files** for related components (`room-card.shared.css`, `booking-dialog.shared.css`)
- Responsive breakpoints: 640px, 768px, 1100px
- Print styles for booking confirmation

### Routing

| Path | Page | Lazy-loaded |
|------|------|-------------|
| `/` | `LandingPage` | Yes |
| `/about` | `AboutPage` | Yes |
| `/rooms` | `RoomsPage` | Yes |
| `/bookings/:bookingId/confirmation` | `BookingConfirmationPage` | Yes |
| `/imprint` | `ImprintPage` | Yes |

Dynamic page titles set via `router.afterEach`.

---

## Cross-Cutting Concerns

### Data Transformation (Mapper Pattern)

Both sides use **dedicated mapper classes** to translate between layers:

- **Backend:** `RoomMapper` (Entity→Domain), `RoomResponseMapper` (Domain→DTO) — keeps JPA annotations out of the domain
- **Frontend:** `roomMapper.ts`, `bookingMapper.ts`, `apiMapper.ts` — converts `snake_case` API responses to `camelCase` domain models

### Error Handling

- **Backend:** `@ControllerAdvice` with `DomainException` + `ErrorCode` enum → standardized `ErrorResponse` JSON
- **Frontend:** Axios response interceptor normalizes all errors to `ApiError` with `code`, `details[]`, `status`; stores expose `error` state to components

### Validation

- **Backend:** Jakarta Bean Validation (`@NotNull`, `@Email`, `@FutureOrPresent`, `@Min`, `@Max`) + custom `@EmailMatch` annotation
- **Frontend:** Manual validation in `bookingStore.validateRequest()` — no external validation library

### Content Separation

All UI text/labels live in `data/content/*.ts` — zero hardcoded strings in Vue templates.
