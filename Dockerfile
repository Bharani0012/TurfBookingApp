# Use the official Oracle JDK 17 image
FROM openjdk:17-oracle

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your local machine to the /app directory in the container
COPY target/TurfBookingApplication-0.0.1-SNAPSHOT.jar /app/TurfBookingApplication.jar

# Make port 8443 available to the world outside this container
EXPOSE 8443

# Run the application when the container launches
ENTRYPOINT ["java", "-jar", "TurfBookingApplication.jar"]
