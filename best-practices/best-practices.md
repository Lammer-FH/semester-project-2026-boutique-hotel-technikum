# Engineering Standards & Implementation Rules (best_practices.md)

The following best-practices should be used when working on this project:

## General Guidelines

### Git Workflow & Branching Strategy

* **Branch Structure:**
  * `main`: Contains only stable, production-ready code. Final project releases are tagged here.
  * `development`: The primary integration branch. All feature branches merge here first.
  * `feature/<name>`: Specific task branches (e.g., `feature/u3-availability-check`). Created from `development`.
* **Workflow:**
  * Every user story must be developed in its own `feature/` branch.
  * Merging into `main` or `development` is only permitted via **Pull Requests** after a documented code review.
* **Versioning:** Tag the repository with `V1.0.0` upon completion of Milestone 1.

---

## Backend Guidelines

### 1. Clean Architecture (R. Martin)

Apply Clean Architecture as defined by R. Martin, with strict dependency rules:

* **Dependency Rule:** Source code dependencies must point inward, toward the domain.
* **Framework Independence:** Business rules must not depend on Spring or JPA.
* **Testability:** Core business logic must be testable without the web or database.
* **Domain Purity:** Keep the domain layer free of framework annotations and persistence concerns, including Spring, JPA, and Lombok. Lombok is permitted in DTOs, JPA entities, and config classes, but **not** in domain models (the `model/` and `domain/` packages).

### 2. Clean Architecture Layers (Backend)

To comply with the dependency rule, the backend must be structured into the following layers:

* **Web/API Layer:** Controllers and DTOs. Handles REST requests and status codes.
* **Domain Layer:** Core business logic and interfaces. This layer must have **zero** dependencies on Spring or JPA.
* **Infrastructure Layer:** Repositories, database configurations, and external API implementations.
* **Use Cases:** Put application and business workflows into services or interactors; keep controllers focused on orchestration only.
* **Persistence Boundary:** Keep JPA entities, repositories, and external integrations in the infrastructure layer.

### 3. Clean Code

Apply clean code practices consistently across backend and frontend:

* **Readability:** Prefer small, focused methods and clear naming.
* **Simplicity:** Avoid duplicated logic and keep functions single-purpose.
* **Structure:** Keep files and modules cohesive, with minimal public surface area.

### 4. REST API & Richardson Level 2

The API must be resource-oriented and meet Richardson Level 2 by using proper HTTP semantics:

* **Naming:** Use plural nouns for resources (e.g., `/api/rooms`, `/api/bookings`).
* **HTTP Verbs:** Use GET, POST, PUT/PATCH, DELETE appropriately for resource operations.
* **Status Codes:**
  * `201 Created`: Return after successful POST (creation).
  * `204 No Content`: Return after successful DELETE.
  * `400/404`: Use for validation or missing resource errors.
* **Resource-Based:** Operations must target specific resource IDs.
* **No Tunneling:** Do not use RPC-style endpoints or action names in URLs.
* **Versioning:** Version public API packages and endpoints when needed (for example `/api/v1/...`), but keep domain and persistence code unversioned.

### 5. State Management & Data Flow (Pinia)

Pinia must be used for cross-component state, as discussed in class:

* **Scope:** Use Pinia for cross-component state, specifically for:
  * Selected hotel rooms and booking periods.
  * Modal states and form data persistence.
* **Logic:** Business logic and API calls should reside in Pinia **actions** rather than directly in components.

### 6. API Design Best Practices

* **Consistency:** Use a single base path (e.g., `/api`) and keep resource naming consistent across endpoints.
* **Pagination:** Use stable query parameters (`page`, `size`) and document defaults.
* **Errors:** Return a consistent JSON error format with a short message and field-level details.
* **Validation:** Validate inputs at the API boundary and return clear, user-focused messages.
* **Bean Validation:** Use declarative validation on request DTOs and translate validation failures centrally into the same error response format.

### 7. Backend Quality Guidelines

* **Separation of Concerns:** Keep controllers thin and push logic into services and domain models.
* **DTO Mapping:** Avoid exposing domain models directly over the wire.
* **Exceptions:** Map domain errors to HTTP status codes in one central place.
* **Central Handling:** Use a dedicated exception handler for consistent error payloads and status mapping.
* **Transactions:** Define transaction boundaries in the service layer, not in controllers or repositories.
* **Package Structure:** Organize code by feature or bounded context where practical, while still keeping web, domain, and infrastructure responsibilities separated.

### 8. Database & Persistence Guidelines

* **Naming:** Use consistent table and column naming (snake_case recommended).
* **Mapping:** Make entity-to-table mapping explicit when names differ.
* **Indexes:** Add indexes for columns used in filters or joins.
* **Seed Data:** Provide minimal seed data for rooms and extras to support UI development.

### 9. Testing & Quality Assurance

* **Minimum Coverage:** Each user story must include basic unit tests and at least one integration test.
* **Definition of Done:** Code reviewed, tests pass, and UX verified on mobile and desktop.

---

## Frontend

### 1. Frontend UX & UI Guidelines

* **Accessibility:** Use semantic HTML, label form fields, and ensure focus states are visible.
* **Responsiveness:** Design mobile-first, then enhance for tablet and desktop layouts.
* **Icons:** Use a single icon set and keep icon semantics consistent across pages.
* **Usability (ISO 9241):** Design for effectiveness, efficiency, and satisfaction; provide clear feedback, consistent navigation, and prevent/recover from errors.

### 2. Frontend Architecture (Atomic Design)

The frontend must be organized using Atomic Design:

* **Atoms:** Smallest units (Buttons, Icons for extras, Inputs).
* **Molecules:** Groups of atoms (Search bar, Form fields for booking).
* **Organisms:** Complex UI sections (Room Cards, Header, Pagination groups).
* **Templates/Pages:** High-level layouts for specific views like the Landing Page or Room Selection.

### 3. State Management Guidelines

* **Pinia Stores:** Keep API calls in actions and expose state via getters where helpful.
* **Loading/Error States:** Always provide loading and error states for async calls.
* **Data Lifecycles:** Clear stale state when leaving booking flows.

### 4. Vue.js Implementation Guidelines

* **Predictability:** Prefer conventions over cleverness; code should be easy to locate from a feature request or bug report.
* **De-facto Standards:** Default to these standards unless there is a clear reason not to:
  * Vue.js style guide (naming, SFC conventions, and recommended patterns).
  * The scaffolding generated by Vue CLI / `create-vue` (predictable folder layout).
  * Official Vue.js libraries (Vue Router, Pinia, etc.).
  * Popular component frameworks (for example Vuetify, Quasar, Ionic): follow their conventions consistently and avoid mixing patterns.
* **Component Files:** Use one component per file (SFC) and name components in `PascalCase`.
* **Component Naming Conventions:**
  * Multi-word names only (avoid collisions with native HTML elements).
  * Prefix reusable app-wide base components consistently (for example `BaseButton`, `BaseModal`).
  * Prefix single-instance layout components with `The` (for example `TheHeader`, `TheFooter`).
  * Prefix tightly coupled child components with the parent name (for example `RoomCardExtras`, `RoomCardPrice`).
  * Name components from general to specific (for example `BookingFormDateDialog`, `BookingFormReviewStep`).
* **Page/Route Naming:** Use named routes and reference routes by `name` (not hardcoded paths) from `<router-link>` and `router.push` where practical.
* **CRUD Page Convention:** For resource pages, use a consistent convention like `RoomsIndex`, `RoomsShow`, `RoomsCreate`, `RoomsEdit` (adapt as needed).
* **Component Structure:** Keep components small and reusable; pass external data via `props` and treat props as read-only.
* **Composition API:** Prefer Vue 3 Composition API with `<script setup>` for new code; use Options API only when it stays simpler and consistent.
* **Template Safety:** Avoid `v-html` for untrusted content; prefer text interpolation and sanitize if HTML rendering is required.
* **Data Binding:** Use `v-model` for form inputs and keep form state close to where it is used (or in Pinia when it must survive navigation).
* **Conditional Rendering:** Use `v-if` for conditional rendering and `v-show` for frequent visibility toggles.
* **Lists:** Always use a stable `:key` on `v-for`; avoid using array indexes as keys when lists can reorder.
* **Reactivity:** Use `computed` for derived UI state and `watch` for side effects or async reactions.
* **Lifecycle:** Load data in `onMounted` / `created` and cleanup in `onBeforeUnmount` / `beforeUnmount` when needed.
* **Component Communication:** Emit events to parents (`$emit`) and use consistent event names (for example `verb-subject`) with typed payloads.
* **Routing:** Use Vue Router for navigation and prefer `<router-link>` / `router.push` over hardcoded anchors.

### 5. UI Implementation Standards

* **Pagination:** Exactly **5 rooms** must be displayed per page. Use button groups for navigation.
* **Icons:** Every room extra (e.g., WiFi, Breakfast) must be accompanied by a meaningful icon.
* **Forms:**
  * Implement a **date dialog** for travel period selection.
  * Provide a **Review** step before final submission.
* **Printability:** The booking confirmation (U5) must be optimized for A4 printing directly from the browser.
