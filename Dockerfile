FROM openjdk:21-jdk-slim

EXPOSE 8080

WORKDIR /app

RUN apt-get update && apt-get install -y wget curl

RUN curl -s https://api.github.com/repos/Event-Space/security-service/releases/latest | grep security-service.jar | tail -n 1 | cut -d : -f 2,3 | tr -d \" | wget -qi -

CMD ["java", "-jar", "security-service.jar"]