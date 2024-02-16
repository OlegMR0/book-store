FROM openjdk:17-jdk-alpine as builder
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/application.jar
#RUN mvn clean install -DskipTests
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8080
