package io.pivotal.ce.gemfire.fastfootshoes.serverside.test;

import static org.junit.Assert.assertTrue;
import io.pivotal.ce.gemfire.fastfootshoes.model.Alert;
import io.pivotal.ce.gemfire.fastfootshoes.model.Customer;
import io.pivotal.ce.gemfire.fastfootshoes.model.Product;
import io.pivotal.ce.gemfire.fastfootshoes.model.Transaction;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.AlertRepository;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.CustomerRepository;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.ProductRepository;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.TransactionRepository;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("/cache-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ListenerTests {

	private static final String id = "1";

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AlertRepository alertRepository;

	@Before
	public void setUp() {
		
		// create a customer
		Customer customer = new Customer();
		customer.setBirthday(new Date());
		customer.setCity("Toronto");
		customer.setEmailAddress("email@email.com");
		customer.setId(id);
		customer.setName("Biff Tannen");
		customerRepository.save(customer);

		// create some products
		Product product1 = new Product();
		product1.setId(id);
		product1.setStockOnHand(25);
		product1.setBrand("Nike");
		product1.setType("Soccer");

		//save the product
		productRepository.save(product1);

	}

	@Test
	public void testListener() {
		Product product = productRepository.findOne(id);
		assertTrue(product.getStockOnHand() == 25);
		// create a couple of valid transactions
		Transaction txn1 = new Transaction();
		txn1.setCustomerId(id);
		txn1.setId(id);
		txn1.setMarkUp(new Double(11));
		txn1.setOrderStatus(Transaction.ORDER_OPEN);
		txn1.setProductId(id);
		txn1.setRetailPrice(new Double(20));
		txn1.setQuantity(2);
		txn1.setTransactionDate(new Date());
		transactionRepository.save(txn1);
		
		//the listener should have removed the quantity of this transaction from the product count
		assertTrue(product.getStockOnHand() == 23);
	}
	
	@Test
	public void asycTest() {
		Alert alert = new Alert();
		alert.setId(id);
		alert.setMessage("Test Message");
		alertRepository.save(alert);
		//TODO - write an SQL look up to check if the value is there
		assertTrue(true);
	}

}
