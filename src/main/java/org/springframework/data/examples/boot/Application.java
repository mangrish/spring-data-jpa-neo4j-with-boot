package org.springframework.data.examples.boot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.examples.boot.jpa.repository.CustomerRepository;
import org.springframework.data.examples.boot.jpa.domain.Customer;
import org.springframework.data.examples.boot.neo4j.domain.Person;
import org.springframework.data.examples.boot.neo4j.repository.PersonRepository;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by markangrish on 27/10/2016.
 */
@SpringBootApplication(exclude = Neo4jDataAutoConfiguration.class)
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepository, PlatformTransactionManager jpaTransactionManager, PersonRepository personRepository, Neo4jTransactionManager neo4jTransactionManager) {
		return (args) -> {

			log.info(jpaTransactionManager.getClass().getName());

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
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			List<Customer> customers = jpaTransactionTemplate.execute(status -> customerRepository.findAll());
			for (Customer customer : customers) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch all people
			log.info("People found with findAll():");
			log.info("-------------------------------");
			Iterable<Person> people = neo4jTransactionTemplate.execute(status -> personRepository.findAll());

			for (Person person : people) {
				log.info(person.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Customer customer = customerRepository.findOne(1L);
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch an individual person by ID
			Person person = personRepository.findOne(1L);
			log.info("Person found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			for (Customer bauer : customerRepository.findByLastName("Bauer")) {
				log.info(bauer.toString());
			}
			log.info("");

			// fetch person by their name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			Person jackBauer = personRepository.findByName("Jack Bauer");
			log.info(jackBauer.toString());
			log.info("");
		};
	}
}
