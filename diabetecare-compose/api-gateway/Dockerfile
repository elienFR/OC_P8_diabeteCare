FROM openjdk:11-jdk-bullseye
VOLUME /main-app
ADD ./api-gateway-0.0.3-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-Dspring.profiles.active=compose", "-jar","/app.jar"]