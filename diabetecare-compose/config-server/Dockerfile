FROM openjdk:11-jdk-bullseye
VOLUME /main-app
ADD ./config-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9101
ENTRYPOINT ["java", "-Dspring.profiles.active=compose", "-jar","/app.jar"]