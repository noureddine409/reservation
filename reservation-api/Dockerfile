# Stage 1: Build the application using Maven
FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

# Copy only the necessary files for the Maven build
COPY ./pom.xml ./reservation-api/
COPY ./src ./reservation-api/src/

# Build the application
RUN mvn -f ./pom.xml clean package

# Stage 2: Execute the application using JRE
FROM openjdk:17-jre-slim

WORKDIR /app

# Copy only the necessary files from the builder stage
COPY --from=builder /app/target/reservation-api-0.0.1-SNAPSHOT.jar .

# Expose port 8080 (if your application uses this port)
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "reservation-api-0.0.1-SNAPSHOT.jar"]
