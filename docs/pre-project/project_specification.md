# Project Description: Boutique Hotel Technikum Booking Application

## 1. Project Overview
This project involves the design and implementation of a full-stack web application for the **Boutique Hotel Technikum**. The goal is to develop a modern, sustainable, and user-friendly booking platform that allows guests to explore hotel information, view room details, check availability, and book rooms. The application is developed as part of the "Advanced Webtechnologies" course at FH Technikum Wien.

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

### U1: Hotel Website
* **Goal:** As a guest I want to be presented with the hotel in the form of a website in order to learn more about it.
* **Details:** In addition to the booking and management features, the Single Page Application also contains static pages that invite visitors to explore the hotel and provide them with the information they need to know what to expect before, during, and after a successful booking.
* **Recommended Approach:**
    * Define what information needs to be provided for the hotel presentation.
    * Prepare content, research and provide images.
    * Create a component for each page.
* **Definition of Done:**
    * HTML templates for the static pages have been created (at minimum: landing page, imprint, about).
    * Texts and content have been prepared and integrated.
    * The individual pages can be accessed and displayed correctly in the browser.
    * The content is well arranged and readable on both a smartphone and a desktop device.

### U2: Hotel Room Selection
* **Goal:** As a guest I want to see an overview of hotel rooms and their details in order to choose a room that suits my preferences.
* **Details:** An API endpoint is available to retrieve room information. Room images are located in the images/rooms folder; file names correspond to the respective room ID. Images may not be the same size; a suitable solution is required. Meaningful icons should be provided for extras. A maximum of 5 hotel rooms should be displayed on the first load, requiring a solution for displaying an odd number of rooms and navigating to further pages.
* **Recommended Approach:**
    * Familiarize yourself with the API endpoint and analyze response data.
    * Create a paper prototype for the display and pagination, considering required interactions.
    * Familiarize yourself with Bootstrap Icons or a different design system to select icons for extras.
    * Use button groups for the pagination buttons.
* **Definition of Done:**
    * Hotel rooms can be displayed with image, title, description, and extras.
    * Five hotel rooms are shown on page one.
    * It is possible to navigate to further pages via pagination.
    * The extras are accompanied by icons that represent them.
    * The feature has been checked into its own branch, pushed to Git, and merged into master after a code review.

### U3: Check Availability
* **Goal:** As a guest I want to check whether a specific room is available for my desired travel period in order to determine whether I will be able to book the room.
* **Details:** An API endpoint is available to retrieve room availability. The user should be able to define their booking period within the interface. Creative freedom is encouraged for innovative presentation.
* **Recommended Approach:**
    * Familiarize yourself with the API endpoint and analyze response data.
    * Develop two to three variants for how the user defines the period and how availability is displayed.
    * Think through user flow, use case scenarios, and error cases.
    * Consider which new components are needed or which existing ones need modification.
    * Build the store and API integration first, then the GUI.
* **Definition of Done:**
    * The user can define the booking period using a date dialog.
    * The user receives clear feedback about availability within the selected period.
    * Possible error cases are handled and the user is informed accordingly.
    * The feature has been checked into its own branch, pushed to Git, and merged into master after a code review.

### U4: Book a Hotel Room
* **Goal:** As a guest I want to book a selected room for a selected period in order to have accommodation during my holiday.
* **Details:** An API endpoint is available to create a booking. Once an available room is found, it can be booked by providing: First name, Last name, Valid email address, Confirm email address, and Breakfast (yes/no). Users must be able to review and change details before final submission. A confirmation must be shown after a successful booking.
* **Recommended Approach:**
    * Familiarize yourself with the API endpoint and analyze data to be transmitted.
    * Create a paper prototype including user flow and error handling.
    * Build the store and API integration first, then the GUI.
* **Definition of Done:**
    * The user can book a desired room for a desired period with complete and correct data, provided the room is available.
    * Possible error cases are handled and the user is informed accordingly.
    * The user can review their data before submitting.
    * The user receives a confirmation after a successful booking.
    * The feature has been checked into its own branch, pushed to Git, and merged into master after a code review.

### U5: Improve Booking Confirmation
* **Goal:** As a guest I want to be informed after the booking whether it was successful or failed in order to know whether I can complete my process or whether further steps are required.
* **Details:** The booking confirmation from U4 is extended to provide comprehensive information. Details include: booking period, room details (image, title, extras, description), submitted personal data, directions to the hotel (e.g. train connections), and contact options.
* **Recommended Approach:**
    * Consider what other information might be necessary.
    * Create a paper prototype of the output and check for component reuse.
    * Research integration of Google Maps or similar services for directions.
* **Definition of Done:**
    * The confirmation is extended with the information described in the Details section.
    * The confirmation is printable on A4 from the browser.
    * There is a clear outcome regarding the directions feature based on research results.
    * The feature has been checked into its own branch, pushed to Git, and merged into master after code review.
    * V1.0.0 (marking the first milestone/release) has been released via tagging.

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