# Dockerfile para autenticacionApp (Spring Boot)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY autenticacionApp/target/autenticacionApp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9099

ENTRYPOINT ["java", "-jar", "app.jar"]
