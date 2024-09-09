FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ./auth-server/build/libs/auth-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8888 50051
ENTRYPOINT ["java", "-jar", "app.jar"]