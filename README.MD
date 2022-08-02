# Readme
## Description
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
4. run a console inside *{projectFolder}/diabetecare-compose/*
5. run command `docker compose up --build`

## Endpoint
1. `clint-ui` : http://localhost:8100
2. `eureka-server` : http://localhost:9102
3. `microservice-patienthistory` : http://localhost:8082
4. `microservice-patients` : http://localhost:8081
5. `microservice-report` : http://localhost:8080


## Info
### Database
- There are 2 databases embedded inside the project.
  - _MySQL_ for `microservice-patients`
  - _NoSQL (MongoDB)_ for `microservice-patienthistory`
- Each database is exposed through docker : <br>

| Database name |   Host    | Exposed port |      User      |         Password         |
|:-------------:|:---------:|:------------:|:--------------:|:------------------------:|
|     MySQL     | localhost |     3307     |      root      | MysqlDockerRootP4ssW0rd! |
|               |           |              | diabetecareusr |      diabetecare123      |
|     NoSQL     | localhost |    27018     |   _no user_    |      _no password_       |

- Initialisation :
  - The NoSQL database is not initialised through docker compose.
  - Whereas the MySQL one is initialised with these two files :
    - `microservice-patients/src/main/resources/schema.sql` which initialise DB scheme.
    - `microservice-patients/src/main/resources/data.sql` which add/update data to the DB.
    - It is recommended not to use these files. They are intended to test the app. Nothing more.
  - You can initialise NoSQL database by using a provided file located here : `microservice-patienthistory/src/main/resources/diabetecare`
    - you can choose between `patientHistory.bson` (to be imported with a software like **Studio3T**).
    - or you can choose `patientHistory.json` (to be imported in CLI with `mongo-dump`).


### Config server (NOT USED BY DEFAULT)
- The project has a module called `config-server`. This module communicates with a git repo to provides properties to each other modules in the app.
- The module is not used anymore.

### Spring boot properties
- A maximum of four property files have been created :
  - `application-ide.yml` is used to be run when launching app through the ide.
  - `application-compose.yml` is used to be run by the app when launched through docker-compose.
  - `application-test.yml` is used when tests are run.
  - (NOT USED BY DEFAULT) `bootstrap.yml` is used to provide to the app the address of remote repo where to find config files. 