FROM gradle:7.6.0-jdk17 AS builder
WORKDIR /app
COPY . /app
RUN gradle build --no-daemon
FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
