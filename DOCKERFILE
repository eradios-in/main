# Use a multi-stage build to reduce final image size

# --- Stage 1: Build the application ---
FROM maven:4.0.0-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- Stage 2: Create minimal runtime image ---
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /app/target/radioweb-0.0.1.jar app.jar

# Expose the port your Spring Boot app runs on (default: 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
