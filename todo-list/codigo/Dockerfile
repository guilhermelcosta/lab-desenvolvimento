FROM ubuntu:latest AS build

RUN apt-get update && apt-get install -y openjdk-11-jdk maven

WORKDIR /app
COPY . .

RUN mvn clean install

FROM openjdk:11-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/todo-list-0.0.1-SNAPSHOT/todo-list-0.0.1-SNAPSHOT.jar /app/app.jar

LABEL authors="Guilherme Costa"

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
