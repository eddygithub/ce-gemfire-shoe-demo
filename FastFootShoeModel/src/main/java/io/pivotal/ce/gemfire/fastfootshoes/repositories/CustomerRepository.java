/**
 * 
 */
package io.pivotal.ce.gemfire.fastfootshoes.repositories;

import io.pivotal.ce.gemfire.fastfootshoes.model.Customer;
import io.pivotal.ce.gemfire.fastfootshoes.model.Transaction;

import java.util.Collection;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author lshannon
 *
 */
public interface CustomerRepository extends GemfireRepository<Customer, String> {

	Customer findByEmailAddress(String emailAddress);

	Customer findById(String id);

	@Query("SELECT * FROM /Transaction t WHERE t.customerId = $1")
	Collection<Transaction> findByCustomer(@Param("customerId") String id);

	@Query("SELECT * FROM /Customer t WHERE t.emailAddress LIKE $1")
	Collection<Customer> searchCustomers(String email);

}
