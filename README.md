# Score User Management API

Spring Boot 3 MVP for managing users, matches and predictions.

Quick start (requires PostgreSQL):

```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-DDB_HOST=localhost -DDB_PORT=5432 -DDB_NAME=scoredb -DDB_USER=postgres -DDB_PASSWORD=secret -DJWT_SECRET=verysecretverylong"
```

Endpoints:
- POST /api/auth/register
- POST /api/auth/login
- POST /api/matches
- GET  /api/matches
- PUT  /api/matches/{id}
- POST /api/predictions
- GET  /api/predictions/me
- PUT  /api/predictions/{id}

Swagger UI:
- Visit `http://localhost:8080/swagger-ui/index.html` after starting the app to view full API documentation and try endpoints. Use the "Authorize" button to provide a `Bearer <token>` for secured endpoints.
