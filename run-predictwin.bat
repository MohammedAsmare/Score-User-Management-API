@echo off
echo Starting Score User Management API with PredictWin...
echo.
echo To run the application, use one of these methods:
echo.
echo Method 1: Using the Maven wrapper
echo   ./mvnw spring-boot:run
echo.
echo Method 2: Using embedded Maven
echo   .mvn/apache-maven-3.9.6/apache-maven-3.9.6/bin/mvn.cmd spring-boot:run
echo.
echo Method 3: Compile and run JAR
echo   .mvn/apache-maven-3.9.6/apache-maven-3.9.6/bin/mvn.cmd clean package
echo   java -jar target/score-user-management-api-0.0.1-SNAPSHOT.jar
echo.
echo After starting, access:
echo   - API: http://localhost:8081/api/predictwin
echo   - Swagger UI: http://localhost:8081/swagger-ui/index.html
echo.
pause