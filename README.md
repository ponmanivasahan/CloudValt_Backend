# CloudVault Backend

Java 17 + Spring Boot backend for the CloudVault SaaS.

## Requirements

- Java 17+
- Maven 3.9+
- PostgreSQL 15+

## Local Setup

```bash
# Copy and fill in environment variables
cp ../.env.example ../.env

# Run (dev profile active by default)
./mvnw spring-boot:run

# Backend starts at http://localhost:5000
```

## Endpoints

| Method | Path | Description |
|---|---|---|
| GET | `/api/health` | Health check |
| GET | `/swagger-ui.html` | Swagger UI |
| GET | `/api-docs` | OpenAPI JSON |

## Database Migrations

Flyway runs automatically on startup.  
Migration files: `src/main/resources/db/migration/`

> **Never** use `ddl-auto=create` or `create-drop` in production.

## Build

```bash
mvn package -DskipTests
java -jar target/cloud-storage-backend-*.jar
```

## Docker

```bash
docker build -t cloudstorage-backend .
```
