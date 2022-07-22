# Readme

This app is a POF for doctors. It enables them to writes notes about patients.<br>
Then the apps analyses notes and produce a report to alarm doctors about their patients' potential sensitivity to diabetes.

## How to run
1. Download sources
2. package jar files for :
   1. api-gateway
   2. client-ui
   3. eureka-server
   4. microservice-patienthistory
   5. microservice-patients
   6. microservice-report
3. put each jar file in its respective folder inside <b>diabetecre-compose</b>
4. run a console inside ./diabetecare-compose
5. run command "docker compose up --build"

## Endpoint
1. clint-ui : http://localhost:8100
2. eureka-server : http://localhost:9102
3. microservice-patienthistory : http://localhost:8082
4. microservice-patients : http://localhost:8081
5. microservice-report : http://localhost:8080

