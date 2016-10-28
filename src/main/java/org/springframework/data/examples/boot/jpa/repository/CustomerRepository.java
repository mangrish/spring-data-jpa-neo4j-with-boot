package org.springframework.data.examples.boot.jpa.repository;

import java.util.List;

import org.springframework.data.examples.boot.jpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mark Angrish
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByLastName(String lastName);
}
