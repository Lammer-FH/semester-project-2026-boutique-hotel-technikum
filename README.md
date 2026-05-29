# Boutique Hotel Technikum ⭐⭐⭐⭐⭐
A modern, full-stack hotel booking application designed for the Boutique Hotel Technikum. This project was developed as part of the *Advanced Webtechnologies* course at FHTW (Fachhochschule Technikum Wien).

**Lecturer:** Helmuth Lammer, MSc

### Key Features
- **Hotel Website & Presentation:** A fully responsive user interface featuring static content pages to introduce guests to the hotel.
- **Dynamic Room Overview:** A component-based room catalog showcasing details, imagery, and structured navigation.
- **Availability Management:** An interactive date-selection feature providing clear user feedback on room availability.
- **Full-Stack Booking Process:** A secure multi-step validation form allowing users to submit reservations and review data.
- **Comprehensive Confirmation:** A dedicated booking summary layout containing registration details, contact options, and practical info.

>The initial project specification is documented in [docs/pre-project/project_specification.md](docs/pre-project/project_specification.md).

## Tech Stack

- **⚙️ Backend**
  - Java 21
  - Spring Boot 3.4.0
- **💻 Frontend**
  - Ionic 7.8.6
  - Vue.js 3.5.33
  - Pinia 3.0.4
  - Vue Router 4.6.4
  - Axios 1.16.0
- **🗄️ Database**
  - MySQL 8.1 (via Docker)

## 🚀 How to Run & Quick Start

> Requires JDK 21, Docker, and Node.js & npm. See [Prerequisites](#prerequisites).

```bash
npm start    # start database, backend and frontend
npm stop     # stop all services
```

Alternative with `quick-start.bat`:

| Command | Description |
|---------|-------------|
| `quick-start.bat start` | Start database, backend and frontend |
| `quick-start.bat stop` | Stop all services |

## Prerequisites

- `JDK 21`
- `Docker Desktop` or Docker CLI
- `Node.js` and `npm`

1. Install a [JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) distribution.

2. Install [Docker Desktop](https://www.docker.com/products/docker-desktop/) for Windows or use your preferred Docker installation.

3. Install [Node.js](https://nodejs.org/) (LTS version recommended) which includes npm.

## Manual Setup

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

3. Open the frontend app at `http://localhost:5000`

> **Notes:**
> - The backend reads DB credentials from `backend/.env` and connects to MySQL on `localhost:3306`.
> - Spring Boot Docker Compose integration is disabled by default (`spring.docker.compose.enabled=false`). Enable it if you have the `docker` CLI available.
> - No separate Maven installation is needed because the project uses the Maven wrapper.

## Project Specification

- The project specification is documented in [docs/pre-project/project_specification.md](docs/pre-project/project_specification.md).
