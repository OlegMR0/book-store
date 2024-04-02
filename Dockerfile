FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
ARG JAR_FILE=/app/target/*.jar
COPY --from=builder ${JAR_FILE} ./application.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "application.jar"]

