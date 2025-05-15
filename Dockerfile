# Description: Dockerfile for the Force application
# Author: Hudson Schumaker

# Base image for the application
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and build files
COPY . .

# Ensure correct line endings (if needed)
RUN apt-get update && apt-get install -y dos2unix && dos2unix gradlew

# Set permissions
RUN chmod +x gradlew

# Build the app
RUN ./gradlew clean build -x test --no-daemon

# Set environment variables
ENV JWT_SECRET=testSecret

# Expose ports, 80 for the application and 5005 for debugging
EXPOSE 80 5005

# Run the application
CMD ["java", "-jar", "build/libs/Force-1.0.0.jar", "-env=local"]
