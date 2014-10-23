/**
 * 
 */
package io.pivotal.ce.gemfire.fastfootshoes.serverside.functions;

import io.pivotal.ce.gemfire.fastfootshoes.model.Customer;
import io.pivotal.ce.gemfire.fastfootshoes.model.Transaction;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.TransactionRepository;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.function.annotation.GemfireFunction;
import org.springframework.stereotype.Component;

/**
 * @author lshannon
 *
 */
@Component
public class OrderCounter {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	private Calendar now = Calendar.getInstance();
	private Calendar txn_date = Calendar.getInstance();
	
	@GemfireFunction
	public int countTransactions(Customer customer) {
		System.out.println("Counting orders for: " + customer.toString());
		Collection<Transaction> completedTransactions = transactionRepository.findCompletedOrders(customer.getId());
		System.out.println("Completed Transactions: " + completedTransactions.size());
		Map<Integer, AtomicInteger> dayOfTheYearCount = new HashMap<Integer,AtomicInteger>();
		int count = 0;
		//DateTime now = new DateTime();
		//go through the orders and increment the count
		for (Transaction txn : completedTransactions) {
			//did it occur this year
			//Period periodDifference = new Period(now, new DateTime(txn.getTransactionDate().getTime()));
			now.setTime(new Date());
			txn_date.setTime(txn.getTransactionDate());
			if (now.get(Calendar.YEAR) == txn_date.get(Calendar.YEAR)) {
				System.out.println("Transaction Id: " + txn.getId() + " Transaction Date: " + txn.getTransactionDate());
				System.out.println("Less than a year increment: " + count);
				count++;
				//if its there birthday they get an extra count per order
				if (isBirthday(txn.getTransactionDate(),customer.getBirthday())) {
					System.out.println("Birthday add one: " + count);
					count++;
				}
				int currentDayOfYear = getDayOfYear(txn.getTransactionDate());
				//build a map of transactions by day of the year
				if (dayOfTheYearCount.containsKey(currentDayOfYear)) {
					dayOfTheYearCount.get(currentDayOfYear).incrementAndGet();
				}
				else {
					dayOfTheYearCount.put(currentDayOfYear, new AtomicInteger());
				}
			}
			
		}
		//go through the map, for days with 5 or more transactions add an extra count
		for (Integer day : dayOfTheYearCount.keySet()) {
			if (dayOfTheYearCount.get(day).intValue() >= 3) {
				System.out.println("Three on a day - add one: " + count);
				count++;
			}
		}
		return count;
	}

	public TransactionRepository getTransactionRepository() {
		return transactionRepository;
	}

	public void setTransactionRepository(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	public boolean isBirthday(Date orderDate, Date customerDOB) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(orderDate);
		cal2.setTime(customerDOB);
		return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
	}
	
	public int getDayOfYear(Date orderDate) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(orderDate);
		return cal1.get(Calendar.DAY_OF_YEAR);
	}
	
}
