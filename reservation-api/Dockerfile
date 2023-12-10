# Fetching latest version of Java
FROM openjdk:17-jdk-slim

# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY ./target/product-api-0.0.1-SNAPSHOT.jar /app

# Exposing port 8080
EXPOSE 8080

# Starting the application
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar reservation-api-0.0.1-SNAPSHOT.jar"]
