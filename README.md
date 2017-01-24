# Spring Data JPA, Spring Data Neo4J (SDN) with Spring Boot.


This example show you how to wire up a Spring Boot application with Spring Data JPA and Spring Data Neo4j running side by side.

## Dependencies

* Spring Data JPA 1.11.0.BUILD-SNAPSHOT (Ingalls Release Train)
* Spring Data Neo4j 4.2.0.BUILD-SNAPSHOT (Ingalls Release Train)
* Spring Boot 1.5.0
* H2 SQL Database 1.4.x
* Neo4j Graph Database 3.1.x (will also work with 2.3.x/3.0.x releases)

## Running the Application

Simply do:

```
mvn spring-boot:run
```

or run in your favourite IDE.

### Expected behaviour

When you run the Application it will load some data into both databases and query them. As the databases are in memory you won't have to configure a database to run it.
