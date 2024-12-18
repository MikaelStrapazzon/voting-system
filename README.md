# Voting System API

## Overview

The Voting System API is a Spring Boot-based application designed to manage voting sessions. The system uses a
integrates with Oracle Database and Kafka for message processing.

## Architecture

- **Spring Boot**: Main framework for building the application.
- **Spring Data JPA**: For ORM and database interactions.
- **Spring Cloud Stream**: For Kafka messaging.
- **Oracle Database**: To persist data.
- **Redis**: Used as auxiliary storage, for topic management purposes.
- **Flyway**: For database versioning and migrations.
- **MapStruct**: For object mapping.
- **Lombok**: To reduce boilerplate code.
- **SpringDoc OpenAPI**: For API documentation and Swagger UI.
- **Testcontainers**: For integrated testing in isolated environments (Oracle Database and Kafka) using containers.

## Development

### Requirements

- Docker
- Docker Compose
- IntelliJ IDEA (Optional, for development and debugging)

### Docker Setup

This project uses Docker and Docker Compose for environment setup. Ensure Docker and Docker Compose are installed on
your machine.

### 1. Clone the repository

```sh
git clone https://github.com/your-username/voting-system.git
cd voting-system
```

### 2. Start the services

Use Docker Compose to start the Oracle Database, Kafka, and the Spring Boot application.

**In the project root folder**

```sh
docker-compose -f ./container/development/docker-compose.dev.yml up
```

### 3. (Optional) Debug the code in IntelliJ IDEA

Stop the "spring-boot-app" service in Docker and use the file `.run/VotingSystemApplication.run.xml` in IntelliJ IDEA to
run the API in debug mode.

### 4. Access the API

You can access the API at `http://localhost:8080` or use Swagger UI at `http://localhost:8080/swagger-ui/index.html`.

### Ports

- **8080**: API
- **1521**: Oracle Database
- **9092**: Kafka
- **6379**: Redis

## Production

The production environment setup is under construction.