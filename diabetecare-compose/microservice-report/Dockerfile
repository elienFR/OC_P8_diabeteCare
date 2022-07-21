FROM openjdk:11-jdk-bullseye
VOLUME /main-app
ADD ./microservice-report-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=compose", "-jar","/app.jar"]