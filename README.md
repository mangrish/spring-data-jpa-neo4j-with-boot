# spring-data-jpa-neo4j-with-boot
Example Spring Boot App with Spring Data JPA and Spring Data Neo4j

This example show you how to wire up a Spring Boot application with Spring Data JPA and Spring Data Neo4j running side by side.

This uses the Spring Data Ingalls release train (BUILD-SNAPSHOT) and Spring Boot 1.4.1.

This simple example starts up both the H2 in memory Database (for JPA) and the Embedded Neo4j in memory Database (for SDN).

When you run the Application it will load some data into both databases and query them.
