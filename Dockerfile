# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-oracle

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper files
COPY . .

# Grant execute permission to the Maven wrapper
RUN chmod +x mvnw

# Build the application using Maven
RUN ./mvnw clean install

# Copy the built JAR file into the container
COPY target/TurfBookingApplication-0.0.1-SNAPSHOT.jar /app/TurfBookingApplication.jar

# Expose port 8080
EXPOSE 8443

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/TurfBookingApplication.jar"]
