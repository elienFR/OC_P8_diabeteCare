# Readme
## Description
This app is a POF for doctors. It enables them to writes notes about patients.<br>
Then the apps analyses notes and produce a report to alarm doctors about their patients' potential sensitivity to diabetes.

## Prerequisite
- Docker-compose v2.6.1
- Docker (desktop) v20.10.17
- Ide : IntelliJ or Eclipse


## How to run from ide
1. First of all, make sure you created a Database named `diabetecare`, and that a user called `diabetecareusr` with the password as shown in the table below has all the permissions on that database.
2. Make also sure that your local `mysql port` is `3306`.
3. No need to create a Mongo DB but still you hve to run a local server running on port 27017. 
4. You can initialise your MongoDB and MySQL DB by following bellow info.
5. From you IDE execute each app with ***a specific VM option*** that is <br> `-Dspring.profiles.active=ide`. Execute in that order :
   1. eureka-server
   2. api-gateway 
   3. microservice-patients
   4. microservice-patienthistory
   5. microservice-report
   6. client-ui
6. Go to http://localhost:8100 to get into the app.

## How to run WITH DOCKER COMPOSE
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
6. Inject data inside your Mongo DB by following info below.
7. (No need to do so for mysql db)
8. Go to http://localhost:8100 to get into the app. 

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

| Database name |   Host    | Local port (for IDE or inside docker container) | Exposed port for docker compose |      User      |         Password         |
|:-------------:|:---------:|:-----------------------------------------------:|:-------------------------------:|:--------------:|:------------------------:|
|     MySQL     | localhost |                      3306                       |              3307               |      root      | MysqlDockerRootP4ssW0rd! |
|               |           |                                                 |                                 | diabetecareusr |      diabetecare123      |
|     NoSQL     | localhost |                      27017                      |              27018              |   _no user_    |      _no password_       |

- <u>**Initialisation :**</u>
  - The NoSQL database is created but not initialised through docker compose.
  - Whereas the MySQL one is created and initialised through docker compose with these two files (**BUT YOU HAVE TO CREATE** the database and allow full access to `diabetecareusr` **if you run app through an IDE**) :
    - `microservice-patients/src/main/resources/schema.sql` which initialise DB scheme.
    - `microservice-patients/src/main/resources/data.sql` which add/update data to the DB.
    - It is recommended not to `data.sql`. It is intended to test the app. Nothing more.
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