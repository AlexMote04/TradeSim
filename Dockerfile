# Build the application
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src
# Package the application (skip tests for faster builds during deployment setup)
RUN mvn clean package -DskipTests

# Run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy ONLY the built jar file from the previous stage
COPY --from=build /app/target/*.jar app.jar
# Expose the web port
EXPOSE 8080
# Boot up the application
ENTRYPOINT ["java", "-jar", "app.jar"]