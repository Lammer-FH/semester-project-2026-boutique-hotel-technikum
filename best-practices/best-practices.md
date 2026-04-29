# Engineering Standards & Implementation Rules (best_practices.md)

The following best-practices should be used when working on this project:

## 1. Clean Architecture (R. Martin)
Apply Clean Architecture as defined by R. Martin, with strict dependency rules:
* **Dependency Rule:** Source code dependencies must point inward, toward the domain.
* **Framework Independence:** Business rules must not depend on Spring or JPA.
* **Testability:** Core business logic must be testable without the web or database.

## 2. Clean Architecture Layers (Backend)
To comply with the dependency rule, the backend must be structured into the following layers:
* **Web/API Layer:** Controllers and DTOs. Handles REST requests and status codes.
* **Domain Layer:** Core business logic and interfaces. This layer must have **zero** dependencies on Spring or JPA.
* **Infrastructure Layer:** Repositories, database configurations, and external API implementations.

## 3. Clean Code
Apply clean code practices consistently across backend and frontend:
* **Readability:** Prefer small, focused methods and clear naming.
* **Simplicity:** Avoid duplicated logic and keep functions single-purpose.
* **Structure:** Keep files and modules cohesive, with minimal public surface area.

## 4. REST API & Richardson Level 2
The API must be resource-oriented and meet Richardson Level 2 by using proper HTTP semantics:
* **Naming:** Use plural nouns for resources (e.g., `/api/rooms`, `/api/bookings`).
* **HTTP Verbs:** Use GET, POST, PUT/PATCH, DELETE appropriately for resource operations.
* **Status Codes:**
    * `201 Created`: Return after successful POST (creation).
    * `204 No Content`: Return after successful DELETE.
    * `400/404`: Use for validation or missing resource errors.
* **Resource-Based:** Operations must target specific resource IDs.
* **No Tunneling:** Do not use RPC-style endpoints or action names in URLs.

## 5. Frontend Architecture (Atomic Design)
The frontend must be organized using Atomic Design:
* **Atoms:** Smallest units (Buttons, Icons for extras, Inputs).
* **Molecules:** Groups of atoms (Search bar, Form fields for booking).
* **Organisms:** Complex UI sections (Room Cards, Header, Pagination groups).
* **Templates/Pages:** High-level layouts for specific views like the Landing Page or Room Selection.

## 6. State Management & Data Flow (Pinia)
Pinia must be used for cross-component state, as discussed in class:
* **Scope:** Use Pinia for cross-component state, specifically for:
    * Selected hotel rooms and booking periods.
    * Modal states and form data persistence.
    * User registration details.
* **Logic:** Business logic and API calls should reside in Pinia **actions** rather than directly in components.

## 7. UI Implementation Standards
* **Pagination:** Exactly **5 rooms** must be displayed per page. Use button groups for navigation.
* **Icons:** Every room extra (e.g., WiFi, Breakfast) must be accompanied by a meaningful icon.
* **Forms:**
    * Implement a **date dialog** for travel period selection.
    * Provide a **Review** step before final submission.
* **Printability:** The booking confirmation (U5) must be optimized for A4 printing directly from the browser.

## 8. Git Workflow & Branching Strategy
* **Branch Structure:**
    * `main`: Contains only stable, production-ready code. Final project releases are tagged here.
    * `development`: The primary integration branch. All feature branches merge here first.
    * `feature/<name>`: Specific task branches (e.g., `feature/u3-availability-check`). Created from `development`.
* **Workflow:**
    * Every user story must be developed in its own `feature/` branch.
    * Merging into `main` or `development` is only permitted via **Pull Requests** after a documented code review.
* **Versioning:** Tag the repository with `V1.0.0` upon completion of Milestone 1.

## 9. API Design Best Practices
* **Consistency:** Use a single base path (e.g., `/api`) and keep resource naming consistent across endpoints.
* **Pagination:** Use stable query parameters (`page`, `size`) and document defaults.
* **Errors:** Return a consistent JSON error format with a short message and field-level details.
* **Validation:** Validate inputs at the API boundary and return clear, user-focused messages.

## 10. Backend Quality Guidelines
* **Separation of Concerns:** Keep controllers thin and push logic into services and domain models.
* **DTO Mapping:** Avoid exposing domain models directly over the wire.
* **Exceptions:** Map domain errors to HTTP status codes in one central place.

## 11. Database & Persistence Guidelines
* **Naming:** Use consistent table and column naming (snake_case recommended).
* **Indexes:** Add indexes for columns used in filters or joins.
* **Seed Data:** Provide minimal seed data for rooms and extras to support UI development.

## 12. Frontend UX & UI Guidelines
* **Accessibility:** Use semantic HTML, label form fields, and ensure focus states are visible.
* **Responsiveness:** Design mobile-first, then enhance for tablet and desktop layouts.
* **Icons:** Use a single icon set and keep icon semantics consistent across pages.

## 13. State Management Guidelines
* **Pinia Stores:** Keep API calls in actions and expose state via getters where helpful.
* **Loading/Error States:** Always provide loading and error states for async calls.
* **Data Lifecycles:** Clear stale state when leaving booking flows.

## 14. Testing & Quality Assurance
* **Minimum Coverage:** Each user story must include basic unit tests and at least one integration test.
* **Definition of Done:** Code reviewed, tests pass, and UX verified on mobile and desktop.
