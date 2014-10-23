package io.pivotal.ce.gemfire.fastfootshoes.repository.test;

import io.pivotal.ce.gemfire.fastfootshoes.model.Alert;
import io.pivotal.ce.gemfire.fastfootshoes.model.Customer;
import io.pivotal.ce.gemfire.fastfootshoes.model.MarkUp;
import io.pivotal.ce.gemfire.fastfootshoes.model.Product;
import io.pivotal.ce.gemfire.fastfootshoes.model.Transaction;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.AlertRepository;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.CustomerRepository;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.MarkUpRepository;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.ProductRepository;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.TransactionRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("/cache-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryTests {

	@Autowired
	private AlertRepository alertRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MarkUpRepository markUpRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	private String key = "1";
	
	@Test
	public void testAlertRepo() {
		Alert alert = new Alert();
		alert.setId(key);
		alertRepository.save(alert);
		assert(alertRepository.count() == 1);
		assert(alertRepository.findOne(key).equals(alert));
	}
	
	@Test
	public void testCustomerRepo() {
		Customer customer = new Customer();
		customer.setId(key);
		customerRepository.save(customer);
		assert(customerRepository.count() == 1);
		assert(customerRepository.findOne(key).equals(customer));
	}
	
	@Test
	public void testMarkUpRepo() {
		MarkUp markUp = new MarkUp();
		markUp.setId(key);
		markUpRepository.save(markUp);
		assert(markUpRepository.count() == 1);
		assert(markUpRepository.findOne(key).equals(markUp));
	}
	
	@Test
	public void testProductRepo() {
		Product product = new Product();
		product.setId(key);
		product.setBrand("Nike");
		product.setType("Running");
		product.setGender("Male");
		product.setStockOnHand(12);
		productRepository.save(product);
		assert(productRepository.count() == 1);
		assert(productRepository.findOne(key).equals(product));
		assert(productRepository.findAllWithStockByBrandTypeGender("Nike", "Running", "F").size() == 0);
		assert(productRepository.findAllWithStockByBrandTypeGender("Nike", "Running", "M").size() == 1);
	}
	
	@Test
	public void testTransactionRepo() {
		Transaction txn = new Transaction();
		txn.setId(key);
		transactionRepository.save(txn);
		assert(transactionRepository.count() == 1);
		assert(transactionRepository.findOne(key).equals(txn));
	}
	
}
