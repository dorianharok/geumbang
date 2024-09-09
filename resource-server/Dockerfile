FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/resource-server-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/data.sql /app/data.sql
EXPOSE 9999 50052
ENTRYPOINT ["java", "-jar", "app.jar"]