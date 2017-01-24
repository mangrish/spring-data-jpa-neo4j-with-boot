package org.springframework.data.examples.boot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.examples.boot.jpa.domain.Customer;
import org.springframework.data.examples.boot.jpa.repository.CustomerRepository;
import org.springframework.data.examples.boot.neo4j.domain.Person;
import org.springframework.data.examples.boot.neo4j.repository.PersonRepository;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Mark Angrish
 */
@SpringBootApplication(exclude = Neo4jDataAutoConfiguration.class)
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepository, PlatformTransactionManager jpaTransactionManager, PersonRepository personRepository, Neo4jTransactionManager neo4jTransactionManager) {
		return (args) -> {

			LOGGER.info(jpaTransactionManager.getClass().getName());

			// save a couple of customers
			TransactionTemplate jpaTransactionTemplate = new TransactionTemplate(jpaTransactionManager);
			jpaTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					customerRepository.save(new Customer("Jack", "Bauer"));
					customerRepository.save(new Customer("Chloe", "O'Brian"));
					customerRepository.save(new Customer("Kim", "Bauer"));
					customerRepository.save(new Customer("David", "Palmer"));
					customerRepository.save(new Customer("Michelle", "Dessler"));
				}
			});

			TransactionTemplate neo4jTransactionTemplate = new TransactionTemplate(neo4jTransactionManager);
			neo4jTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					// also save them as people
					personRepository.save(new Person("Jack Bauer"));
					personRepository.save(new Person("Chloe O'Brian"));
					personRepository.save(new Person("Kim Bauer"));
					personRepository.save(new Person("David Palmer"));
					personRepository.save(new Person("Michelle Dessler"));
				}
			});

			// fetch all customers
			LOGGER.info("Customers found with findAll():");
			LOGGER.info("-------------------------------");
			Iterable<Customer> customers = jpaTransactionTemplate.execute(status -> customerRepository.findAll());
			for (Customer customer : customers) {
				LOGGER.info(customer.toString());
			}
			LOGGER.info("");

			// fetch all people
			LOGGER.info("People found with findAll():");
			LOGGER.info("-------------------------------");
			Iterable<Person> people = neo4jTransactionTemplate.execute(status -> personRepository.findAll());

			for (Person person : people) {
				LOGGER.info(person.toString());
			}
			LOGGER.info("");

			// fetch an individual customer by ID
			Customer customer = customerRepository.findOne(1L);
			LOGGER.info("Customer found with findOne(1L):");
			LOGGER.info("--------------------------------");
			LOGGER.info(customer.toString());
			LOGGER.info("");

			// fetch an individual person by ID
			Person person = personRepository.findOne(1L);
			LOGGER.info("Person found with findOne(1L):");
			LOGGER.info("--------------------------------");
			LOGGER.info(customer.toString());
			LOGGER.info("");

			// fetch customers by last name
			LOGGER.info("Customer found with findByLastName('Bauer'):");
			LOGGER.info("--------------------------------------------");
			for (Customer bauer : customerRepository.findByLastName("Bauer")) {
				LOGGER.info(bauer.toString());
			}
			LOGGER.info("");

			// fetch person by their name
			LOGGER.info("Customer found with findByLastName('Bauer'):");
			LOGGER.info("--------------------------------------------");
			Person jackBauer = personRepository.findByName("Jack Bauer");
			LOGGER.info(jackBauer.toString());
			LOGGER.info("");
		};
	}
}
