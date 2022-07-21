FROM openjdk:11-jdk-bullseye
VOLUME /main-app
ADD ./microservice-patients-0.0.3-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.profiles.active=compose", "-jar","/app.jar"]