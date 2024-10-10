FROM openjdk:21-jdk-slim

EXPOSE 8080

WORKDIR /app

COPY build/libs/security-service-1.0.jar security-service.jar

CMD ["java", "-jar", "security-service.jar"]