# Stage 1: Build the application using Maven
FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

COPY ./reservation-api ./reservation-api


# Build the application
RUN mvn clean package

# Stage 2: Execute the application using JRE
FROM openjdk:17-jre-slim

WORKDIR /app

# Copy only the necessary files from the builder stage
COPY --from=builder /app/target/reservation-api-0.0.1-SNAPSHOT.jar /app

# Expose port 8080
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "reservation-api-0.0.1-SNAPSHOT.jar"]
