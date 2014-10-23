package com.gopivotal.ce.gemfire;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

@Component
@EnableScheduling
public class CacheLoader {
	
	private static final String RECORDS_ADDED_TO_GEM_FIRE = " Records Added To GemFire: ";
	private ICsvBeanReader beanReader;
	private List<String> errorLog = new ArrayList<String>();
	private List<String> activityLog = new ArrayList<String>();

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	MarkUpRepository markupRepository;
	
	@Autowired
	AlertRepository alertRepository;
	
	
	
	
	public boolean checkIfHistoricIsThere() {
		return false;
	}
	
	//need to load data
	public void loadData() {
		
		//load the customers
		String[] nameMapping = new String[]{"city","birthday","id","name", "emailAddress"};
		CellProcessor[] processors = new CellProcessor[] { 
        		new NotNull(), //city
        		new ParseDate("dd-MM-yyyy"), //birthday
                new NotNull(), //id
                new NotNull(), //name
                new NotNull() //email
        };
        loadCustomers("customer.csv",nameMapping,processors);
        
        //load the products
        nameMapping = new String[]{"stockOnHand","wholeSalePrice","brand","type", "color", "size", "gender", "id"};
        processors = new CellProcessor[] { 
        		new ParseInt(),//stockOnHand
        		new ParseDouble(),//wholeSalePrice
        		new NotNull(),//brand
        		new NotNull(),//type
        		new NotNull(),//color
        		new ParseDouble(),//size
        		new NotNull(),//gender
                new NotNull()//productId
        };
        loadProducts("products.csv",nameMapping,processors);
        
        //load the historic transactions - these are just randomly generated and do not respect stock quantity
        nameMapping = new String[]{"customerId","transactionDate","productId","quantity", "retailPrice", "id", "markUp", "orderStatus"};
        processors = new CellProcessor[] { 
        		new NotNull(),//customerId
        		new ParseDate("dd-MM-yyyy"),//transactionDate
        		new NotNull(),//productId
        		new ParseInt(),//quantity
        		new ParseDouble(),//retailsPrice
        		new NotNull(),//transactionId
        		new ParseDouble(),//markUp
        		new NotNull()//order status
        };
        loadTransactions("transactions.csv",nameMapping,processors);
        
        //load the mark ups
        nameMapping = new String[]{"id", "rate","levelName","qualifyingTransactionCountFloor","qualifyingTransactionCountCeiling"};
        processors = new CellProcessor[] {
        		new NotNull(),//id
        		new ParseDouble(),//rate
        		new NotNull(),//levelName
        		new ParseInt(),//qualifyingTransactionCountFloor
        		new ParseInt()//qualifyingTransactionCountCeiling
        };
        loadMarkUps("markUps.csv",nameMapping,processors);
        
        //clean out the alerts
        for (Alert alert : alertRepository.findAll()) {
        	alertRepository.delete(alert);
        }
        closeBeanReader();
		writeOutLogs();
	}
	
	private void writeOutLogs() {
		//Data Loading
		System.out.println("*************************************************************");
		System.out.println("********************DATA LOADING SUMMARY*********************");
		System.out.println("*************************************************************");
		for (String message : activityLog) {
			System.out.println(message);
		}
		System.out.println("*************************************************************");
		//Error during loading
		System.out.println("********************ERROR LOGS*******************************");
		System.out.println("*************************************************************");
		if (errorLog.size() <= 0) {
			System.out.println("No errors were recorded");
		}
		else {
			for (String error : errorLog) {
				System.out.println(error);
			}
		}
		System.out.println("*************************************************************");
	}


	private void loadCustomers(String file, String[] nameMapping, CellProcessor[] processors) {
			System.out.println("Started loading the customers");
        	initalizeBeanReader(file); 
            Customer cust;
            int custCount = 0;
            try {
				while ((cust = beanReader.read(Customer.class, nameMapping,processors)) != null) {
					customerRepository.save(cust);
					custCount++;
				}
			} catch (IOException e) {
				errorLog.add(e.toString());
			}
            activityLog.add("Customers: Records Read: " + custCount + RECORDS_ADDED_TO_GEM_FIRE + customerRepository.count() );
	}

	
	
	private void loadProducts(String file, String[] nameMapping, CellProcessor[] processors) {
			System.out.println("Started loading the products");
        	initalizeBeanReader(file);
            Product prod;
            int prodCount = 0;
            try {
				while ((prod = beanReader.read(Product.class, nameMapping,processors)) != null) {
					productRepository.save(prod);
					prodCount++;
				}
			} catch (IOException e) {
				errorLog.add(e.toString());
			}
            activityLog.add("Products: Records Read: " + prodCount + RECORDS_ADDED_TO_GEM_FIRE + productRepository.count() );
	}
	
	private void loadTransactions(String file, String[] nameMapping, CellProcessor[] processors) {
				System.out.println("Started loading the transactions");
	            initalizeBeanReader(file);
	            Transaction txn;
	            int txnCount = 0;
	            try {
					while ((txn = beanReader.read(Transaction.class, nameMapping,processors)) != null) {
						transactionRepository.save(txn);
						txnCount++;
					}
				} catch (IOException e) {
					errorLog.add(e.toString());
				}
	            activityLog.add("Transactions: Records Read: " + txnCount + RECORDS_ADDED_TO_GEM_FIRE + transactionRepository.count());
	}
	
	private void loadMarkUps(String file,String[] nameMapping, CellProcessor[] processors) {
			System.out.println("Started loading the mark ups");
            initalizeBeanReader(file);
            MarkUp markUp;
            int markUpCount = 0;
            try {
				while ((markUp = beanReader.read(MarkUp.class, nameMapping,processors)) != null) {
					markupRepository.save(markUp);
					markUpCount++;
				}
			} catch (IOException e) {
				errorLog.add(e.toString());
			}
            activityLog.add("Mark Ups: Records Read: " + markUpCount + RECORDS_ADDED_TO_GEM_FIRE + markupRepository.count());
	}

	private void initalizeBeanReader(String file) {
		beanReader = new CsvBeanReader(new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(file))),CsvPreference.STANDARD_PREFERENCE);
		try {
			@SuppressWarnings("unused")
			final String[] header = beanReader.getHeader(true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void closeBeanReader() {
		if (beanReader != null) {
		    try {
				beanReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
