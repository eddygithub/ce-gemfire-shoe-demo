/**
 * 
 */
package io.pivotal.ce.gemfire.fastfootshoes.repositories;

import io.pivotal.ce.gemfire.fastfootshoes.model.Transaction;

import java.util.Collection;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;

/**
 * @author lshannon
 *
 */
public interface TransactionRepository extends GemfireRepository<Transaction, String> {
	
	Transaction findById(String id);
	
	@Query("SELECT * FROM /Transaction t WHERE t.customerId = $1")
	Collection<Transaction> findByCustomer(String id);
	
	@Query("SELECT * FROM /Transaction t where t.orderStatus = 'open'")
	Collection<Transaction> findOpenOrders();
	
	@Query("SELECT * FROM /Transaction t where (t.orderStatus = 'open' or t.orderStatus = 'shipped') and t.customerId = $1")
	Collection<Transaction> findCompletedOrders(String id);
	
	@Query("SELECT count(*) FROM /Transaction t where t.orderStatus = 'open'")
	Integer getCount();


}
