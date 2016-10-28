package org.neo4j.examples.sdjpasdn.jpa.repository;

import java.util.List;

import org.neo4j.examples.sdjpasdn.jpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by markangrish on 27/10/2016.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}
