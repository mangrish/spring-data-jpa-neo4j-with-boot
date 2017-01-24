package org.springframework.data.examples.boot.neo4j.repository;

import org.springframework.data.examples.boot.neo4j.domain.Person;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Mark Angrish
 */
public interface PersonRepository extends CrudRepository<Person, Long> {

	Person findByName(String name);
}
