# Hexagonal-Seminar

This project was developed in order to showcase how hexagonal architecture can helps developers to deeply test their code.
The main objective of this app is to provide seminar destination suggestions based on a few constraints :
- the departure airport
- the max CO2 consumption allowed
- the number of passengers

It is also possible to create a seminar based on these suggestions, then other suggestions will not be part of a country you already visited during the last 5 years.

The suggestion always maximizes the CO2 consumption. Of course, it is only a showcase project and does not represent our ecological values,
as we do not recommand to take planes.

## Technical details

This app was developed using JAVA 17 and Spring Boot 3.0.6. 
It uses a postgres database to store the data.

## Running the app

First of all, you'll need to build the project using maven : 

```
mvn clean package
```

To run this application, you'll need a postgres database running. In order to ease the 
whole process, a docker-compose was provided that does exactly that. To run it, simply run 
the following command : 

```
docker-compose up -d
```

You can then easily start the application using the following command : 

```
java -jar hexagonal-seminar-application/target/hexagonal-seminar-application-1.0-SNAPSHOT.jar
```

## Swagger

You can find a swagger documentation of the API at the following url : 

http://localhost:8080/webjars/swagger-ui/index.html#/