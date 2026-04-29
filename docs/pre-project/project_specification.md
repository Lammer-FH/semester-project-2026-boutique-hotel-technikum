# Project Description: Boutique Hotel Technikum Booking Application

## 1. Project Overview
This project involves the design and implementation of a full-stack web application for the **Boutique Hotel Technikum**. The goal is to develop a modern, sustainable, and user-friendly booking platform that allows guests to explore hotel information, view room details, check availability, and book rooms. The application is developed as part of the "Advanced Web Frameworks 2026" course at FH Technikum Wien.

## 2. Technical Stack
The application must be built using the following technologies:
* **Backend:** Java 21 (LTS) with **Spring Boot 3.4.x** (Requirement: Version >= 3).
* **Frontend:** **Ionic 8.x** with **Vue.js 3.4.x**.
* **Database:** **MySQL 8.4 (LTS)**.
* **State Management:** **Pinia 2.1.x**.
* **API Calls:** **JS Fetch API** (Native browser standard; follows the WHATWG Fetch Standard living standard).

## 3. Engineering Standards & Best Practices
The following principles should be used for the project implementation:
* **Clean Architecture:** Apply the principles of Clean Architecture as defined by R. Martin.
* **Clean Code:** Maintain high-quality, readable, and professional code.
* **Atomic Design:** Structure the frontend using modular design patterns like Atomic Design.
* **Richardson Maturity Model:** The REST API must meet Level 2 (resource-based).
* **Mobile-First Design:** The UI must be developed with a mobile-first approach but remain fully usable on desktops.

Those are documented in [`project_specification.md`](../pre-project/project_specification.md)

## 4. User Stories
The project is divided into five core user stories:
* **U1: Hotel Website:** Static pages (Landing, Imprint, About) to introduce the hotel to guests.
* **U2: Hotel Room Selection:** An overview of rooms with images, descriptions, extras (with icons), and pagination (5 rooms per page).
* **U3: Check Availability:** A feature allowing users to define a travel period via a date dialog and receive clear feedback on room availability.
* **U4: Book a Hotel Room:** A booking form collecting personal data (name, email) and preferences (breakfast), including a data review step before final submission.
* **U5: Improve Booking Confirmation:** An extended, printable confirmation page providing comprehensive booking details, room info, and travel directions.

## 5. Project Constraints & Requirements
* **Team Setup:** Teams of 4 students, documented in [`teams.md`](../../team.md)
* **Scalability:** The app should handle up to 100 visitors per day.
* **User Management:** Guests should be able to register to view their personal details and bookings.
* **Error Handling:** Proper validation and error handling must be implemented for all user inputs and API interactions.
* **Project Management:** A Kanban board (GitHub Projects) must be used to track progress and manage the backlog.

## 6. Milestones & Deliverables
The project follows a structured timeline with three main delivery points:
* **Milestone 1 (Planning & Specification):** Delivery of the REST API spec, Database ER diagram, and the initial Kanban backlog.
* **Milestone 2 (Partial Implementation):** Full-stack implementation of User Stories 1-3, including working frontend and backend modules.
* **Final Delivery (Completion & Quality):** Full implementation of User Stories 4-5, UI/UX refinement, final README, and a class presentation.

## 7. AI Usage Policy
* AI tools are documented in a [`AI_USAGE.md`](../../AI_USAGE.md) file.