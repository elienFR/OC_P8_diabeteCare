FROM openjdk:11-jdk-bullseye
VOLUME /main-app
ADD target/microservice-patients-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar","/app.jar"]