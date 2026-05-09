# Boutique Hotel Technikum

Full-stack booking application for the Boutique Hotel Technikum, built for the FHTW course Advanced Web Technologies.

## Overview

This repository contains the backend, frontend, and project documentation for the hotel booking application. The initial project specification is documented in [docs/pre-project/project_specification.md](docs/pre-project/project_specification.md).

## Tech Stack

- Backend: Java 25, Spring Boot 4.0.6
- Frontend: Ionic 8.x, Vue.js 3.4.x, Pinia 2.1.x
- Database: MySQL LTS via Docker Image (v9.7.0-1.el9)

## Prerequisites

- JDK 25
- Docker Desktop or another Docker installation
- Node.js and npm for the frontend

## Setup

### Backend

1. Install a [JDK 25](https://www.oracle.com/java/technologies/javase/jdk25-archive-downloads.html) distribution.
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
