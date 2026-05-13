# Boutique Hotel Technikum

Full-stack booking application for the Boutique Hotel Technikum, built for the FHTW course Advanced Web Technologies.

## Overview

This repository contains the backend, frontend, and project documentation for the hotel booking application. The initial project specification is documented in [docs/pre-project/project_specification.md](docs/pre-project/project_specification.md).

## Architecture Notes

- Folder structure: keep backend code under `backend/src/main/java` with layers split by web, domain, and infrastructure; keep frontend UI and routing under `frontend/src`, and keep docs under `docs/`.
- Frontend structure: use Atomic Design in `frontend/src/components` (atoms, molecules, organisms, layout), keep pages in `frontend/src/pages`, app data in `frontend/src/data`, and route config in `frontend/src/router`.
- Atomic Design summary: atoms are basic UI elements; molecules combine atoms into small functional units; organisms are larger sections composed of molecules; pages assemble organisms into full views.
- Backend structure: keep controllers/DTOs in web layer, business rules in domain layer without framework dependencies, and database/external integrations in infrastructure.

## CSS File Separation (Frontend)

- Global tokens live in `frontend/src/assets/styles/variables.css` (colors, spacing, typography), and base elements live in `frontend/src/assets/styles/base.css`.
- App-wide composition lives in `frontend/src/assets/styles/main.css`, which imports variables and base styles.
- Component-specific styles stay inside each Vue SFC as scoped blocks; avoid adding feature styles to global CSS unless multiple pages share them.

## Tech Stack

- Backend: Java 21, Spring Boot 3.4.0
- Frontend: Ionic 8.x, Vue.js 3.4.x, Pinia 2.1.x
- Database: MySQL LTS via Docker Image (v9.7.0-1.el9)

## Prerequisites

- JDK 21
- Docker Desktop or another Docker installation
- Node.js and npm for the frontend

## Setup

### Backend

1. Install a [JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) distribution.
2. Start the MySQL database from the backend compose file:

	```powershell
	cd backend/docker
	docker compose up -d
	```

### Frontend

1. Install dependencies:

	```bash
	cd frontend
	npm install
	```

## Run the Application

### Frontend

1. Start the development server:

	```bash
	cd frontend
	npm run dev
	```

2. Open the frontend app at `http://localhost:5173` unless you changed the default Vite configuration.

### Backend

1. Start the Java App with the Maven wrapper:

	```powershell
	cd backend
	.\mvnw.cmd spring-boot:run -DskipTests
	```

2. To run a specific test class instead, use:

	```powershell
	cd backend
	.\mvnw.cmd -Dtest=HealthControllerTest test
	```

3. Open the backend at `http://localhost:8080`.

> **Notes:**
> - Spring Boot is configured to use [backend/docker/docker-compose.yaml](backend/docker/docker-compose.yaml), so the backend can be started from `backend` without moving the compose file.
> - No separate Maven installation is needed because the project uses the Maven wrapper.

## Project Specification

- The project specification is documented in [docs/pre-project/project_specification.md](docs/pre-project/project_specification.md).
