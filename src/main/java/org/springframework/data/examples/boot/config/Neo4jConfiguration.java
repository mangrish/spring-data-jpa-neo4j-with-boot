package org.springframework.data.examples.boot.config;

import javax.persistence.EntityManagerFactory;

import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by markangrish on 27/10/2016.
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "org.springframework.data.examples.boot.neo4j.repository", transactionManagerRef = "neo4jTransactionManager")
@EnableJpaRepositories(basePackages = "org.springframework.data.examples.boot.jpa.repository", transactionManagerRef = "jpaTransactionManager")
@EnableTransactionManagement
public class Neo4jConfiguration {

	@Bean
	public org.neo4j.ogm.config.Configuration configuration() {
		org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration();
		configuration.driverConfiguration().setDriverClassName(EmbeddedDriver.class.getName());
		return configuration;
	}

	@Bean
	public SessionFactory sessionFactory(org.neo4j.ogm.config.Configuration configuration) {
		return new SessionFactory(configuration, "org.springframework.data.examples.boot.neo4j.domain");
	}

	@Bean
	public Neo4jTransactionManager neo4jTransactionManager(SessionFactory sessionFactory) {
		return new Neo4jTransactionManager(sessionFactory);
	}

	@Bean
	public JpaTransactionManager jpaTransactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
}
