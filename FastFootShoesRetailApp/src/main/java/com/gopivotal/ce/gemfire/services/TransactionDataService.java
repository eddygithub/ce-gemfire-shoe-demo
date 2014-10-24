package com.gopivotal.ce.gemfire.services;

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
import io.pivotal.ce.gemfire.fastfootshoes.serverside.functions.OrderCounterCaller;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransactionDataService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private AlertRepository alertRespository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private MarkUpRepository markUpRepo;
	
	@Autowired
	@Qualifier("orderCounterCaller")
	private OrderCounterCaller orderCounterCaller;
	

	public boolean placeOrder(String customerId, String productId, int quantity) {
				// check if there is enough quantity
				Product product = productRepository.findById(productId);
				System.out.println("Got the product: " + product);
				Customer customer = customerRepository.findById(customerId);
				System.out.println("Got the customer: " + customer);
				if (product != null && product.getStockOnHand() - quantity > 0) {
					//get the valid transaction count using the function
					long[] results = getTransactionCount(customer);
					long txn_count = results[1];
					//determine the markup
					double markUpValue = getMarkUp(txn_count);
					writeTransaction(customerId, productId, quantity, product, markUpValue);
					return true;
				}
				//was not enough stock - log it in an alert
				else 
				{
					Alert alert = new Alert();
					alert.setId("Lack of stock: " +  new Date().getTime());
					alert.setMessage("Code:Lack of Stock; ProductId:"+ productId + "; Quantity:" + quantity + "; CustomerId: " + customerId);
					alert.setMessageDate(new Date());
					alertRespository.save(alert);
					return false;
				}

	}
	
	/**
	 * Takes a customer and returns the number of valid transactions in the year. This is
	 * more than just the number of transactions per day. Different transaction patterns
	 * increase the count
	 * @param customer
	 * @return
	 */
	public long[] getTransactionCount(Customer customer) {
		System.out.println("Counting Transactions for: " + customer);
		long[] results = new long[2];
		long start = new Date().getTime();
		List<Integer> txn_count_result = orderCounterCaller.countTransactions(customer);
		int txn_count = 0;
		//tally up the counts from each server
		for (Integer i : txn_count_result) {
			txn_count = txn_count + i.intValue();
		}
		long end = new Date().getTime();
		long difference = end - start;
		results[0] = difference;
		results[1] = txn_count;
		return results;
		
	}
	
	/**
	 * Takes the number of transactions and find the Mark Up to match it
	 * @param txn_count
	 * @return
	 */
	public double getMarkUp(long txn_count) {
		double txn_markup = 0;
		for (MarkUp markup : markUpRepo.findAll()) {
			if (txn_count >= markup.getQualifyingTransactionCountFloor() && txn_count <= markup.getQualifyingTransactionCountCeiling()) {
				txn_markup = markup.getRate();
			}
		}
		return txn_markup;
	}
	
	/**
	 * Takes the number of transactions and find the Mark Up to match it
	 * @param txn_count
	 * @return
	 */
	public String getMarkUpName(long txn_count) {
		String name = "None";
		for (MarkUp markup : markUpRepo.findAll()) {
			if (txn_count >= markup.getQualifyingTransactionCountFloor() && txn_count <= markup.getQualifyingTransactionCountCeiling()) {
				name = markup.getLevelName();
			}
		}
		return name;
	}


	@Transactional
	private void writeTransaction(String customerId, String productId,
			int quantity, Product product, double markupValue) {
		//create the transaction
		Transaction txn = new Transaction();
		txn.setCustomerId(customerId);
		txn.setId(customerId + "|" + new Date().getTime());
		txn.setMarkUp(markupValue);
		txn.setOrderStatus("open");
		txn.setProductId(productId);
		txn.setQuantity(quantity);
		if (markupValue > 0) {
			txn.setRetailPrice(product.getWholeSalePrice() * markupValue);
		}
		else {
			txn.setRetailPrice(product.getWholeSalePrice());
		}
		txn.setTransactionDate(new Date());
		//commit the transaction
		transactionRepository.save(txn);
	}
	
	public Collection<Transaction> getOpenOrder() { 
		return transactionRepository.findOpenOrders();
	}
	
	public Iterable<Transaction> getAllOrder() { 
		return transactionRepository.findAll();
	}
	
}
