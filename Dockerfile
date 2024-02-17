FROM openjdk:17-jdk-alpine as builder
WORKDIR /app
ARG JAR_FILE=target/*.jar
#ADD target/*.jar app/application.jar
COPY ${JAR_FILE} ./application.jar
#RUN mvn clean install -DskipTests
#ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "application.jar"]
#RUN java -Djarmode=layertools -jar app/application.jar extract

#FROM openjdk:17-jdk-alpine
#WORKDIR /app
#COPY --from=builder dependencies/ ./
#COPY --from=builder spring-boot-loader/ ./
#COPY --from=builder asnapshot-dependencies/ ./
#COPY --from=builder app/ ./
#EXPOSE 8081
#ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

