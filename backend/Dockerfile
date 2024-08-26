FROM openjdk:17-jdk-slim
WORKDIR /app
COPY demo/build/libs/app-0.0.1-SNAPSHOT.jar /app/spring-app.jar
ENTRYPOINT ["java", "-jar", "/app/spring-app.jar"]
