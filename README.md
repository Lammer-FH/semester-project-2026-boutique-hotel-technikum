# Boutique Hotel Technikum

Full-stack booking application for the `Boutique Hotel Technikum` for FHTW course 'Advanced Webtechnologies'. The intial project specification is documented in [docs/pre-project/project_specification.md](docs/pre-project/project_specification.md).

## Tech-Stack

- Backend: Java 25, Spring Boot 4.0.6
- Frontend: Ionic 8.x, Vue.js 3.4.x, Pinia 2.1.x
- Database: MySQL LTS via Docker Image (v9.7.0-1.el9)

## Quick Install

### Backend on Windows

1. Install [JDK 25](https://www.oracle.com/java/technologies/javase/jdk25-archive-downloads.html) distribution 
2. Install Docker and launch the MySQL database from the backend compose file:

	```powershell
	cd backend/docker
	docker compose up -d
	```

3. Start the backend with the Maven wrapper:

	```powershell
	cd backend
	.\mvnw.cmd spring-boot:run -DskipTests
    # run specific tests
    # .\mvnw.cmd spring-boot:run -Dtest=HealthContrllerTest test
	```

4. Open the app on `http://localhost:8080`.

Spring Boot is configured to use `backend/docker/docker-compose.yaml`, so the backend can be started from `backend` without moving the compose file.

No separate Maven installation is needed because the project uses the Maven wrapper.

### Frontend on Windows

1. ...
2. ...
3. ...