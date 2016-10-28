package org.springframework.data.examples.boot.neo4j.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author Mark Angrish
 */
@NodeEntity
public class Person {

	@GraphId
	private Long id;

	private String name;

	@Relationship(type = "TEAM_MATE", direction = Relationship.OUTGOING)
	public Set<Person> teammates;

	private Person() {
	}

	public Person(String name) {
		this.name = name;
	}


	public void worksWith(Person person) {
		if (teammates == null) {
			teammates = new HashSet<>();
		}
		teammates.add(person);
	}

	public String toString() {

		return this.name + "'s teammates => "
				+ Optional.ofNullable(this.teammates).orElse(
				Collections.emptySet()).stream().map(
				Person::getName).collect(Collectors.toList());
	}

	public String getName() {
		return name;
	}
}
