FROM openjdk:11-jdk-bullseye
VOLUME /main-app
ADD target/eureka-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9102
ENTRYPOINT ["java", "-jar","/app.jar"]