# Boutique Hotel Technikum

Full-stack booking application for the Boutique Hotel Technikum, built for the FHTW course Advanced Web Technologies.

## Overview

This repository contains the backend, frontend, and project documentation for the hotel booking application. The initial project specification is documented in [docs/pre-project/project_specification.md](docs/pre-project/project_specification.md).

## Tech Stack

- Backend: Java 21, Spring Boot 3.4.0
- Frontend: Ionic 8.x, Vue.js 3.4.x, Pinia 2.1.x
- Database: MySQL 8.1 via Docker Image

## Prerequisites

- JDK 21
- Docker Desktop or another Docker installation
- Node.js and npm for the frontend

1. Install a [JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) distribution.

2. Install [Docker Desktop](https://www.docker.com/products/docker-desktop/) for Windows or use your preferred Docker installation.

3. Install [Node.js](https://nodejs.org/) (LTS version recommended) which includes npm.

## Setup

### Backend


1. Start the MySQL database from the backend compose file:

	```powershell
	cd backend/docker
	docker compose up -d
	```

2. Start the Java backend application using the Maven wrapper:

	```powershell
	cd backend
	.\mvnw.cmd clean install -DskipTests
	.\mvnw.cmd spring-boot:run
	```

3. To run all tests, use:

	```powershell
	cd backend
	.\mvnw.cmd test
	```

4. Open the backend at `http://localhost:8080`.

5. (Optional) Check out the Swagger OpenAPI Specification: `http://localhost:8080/swagger-ui/index.html`

### Frontend

1. Install dependencies:

	```bash
	cd frontend
	npm install
	```

2. Run the frontend application via development server:

	```bash
	cd frontend
	npm run dev
	```

2. Open the frontend app at `http://localhost:5173` unless you changed the default Vite configuration.

> **Notes:**
> - The backend reads DB credentials from `backend/.env` and connects to MySQL on `localhost:3306`.
> - Spring Boot Docker Compose integration is disabled by default (`spring.docker.compose.enabled=false`). Enable it if you have the `docker` CLI available.
> - No separate Maven installation is needed because the project uses the Maven wrapper.

## Project Specification

- The project specification is documented in [docs/pre-project/project_specification.md](docs/pre-project/project_specification.md).
