FROM openjdk:11-jdk-bullseye
VOLUME /main-app
ADD ./eureka-server-0.0.3-SNAPSHOT.jar app.jar
EXPOSE 9102
ENTRYPOINT ["java", "-Dspring.profiles.active=compose", "-jar","/app.jar"]