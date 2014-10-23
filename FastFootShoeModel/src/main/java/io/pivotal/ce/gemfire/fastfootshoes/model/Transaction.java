/**
 * 
 */
package io.pivotal.ce.gemfire.fastfootshoes.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;

/**
 * For each item sold a transaction object is generated to store the transaction
 * @author lshannon
 */
@Region("Transaction")
public class Transaction {

	private String customerId;
	private Date transactionDate;
	private String productId;
	private int quantity;
	private double retailPrice;
	private String orderStatus;
	@Id
	private String id;
	private double markUp;
	public static String ORDER_COMPLETED = "shipped";
	public static String ORDER_CANCELLED = "cancelled";
	public static String ORDER_OPEN = "open";
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	public double getRetailPrice() {
		return retailPrice;
	}
	
	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}
	
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getMarkUp() {
		return markUp;
	}

	public void setMarkUp(double markUp) {
		this.markUp = markUp;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Transaction [customerId=" + customerId + ", transactionDate="
				+ transactionDate + ", productId=" + productId + ", quantity="
				+ quantity + ", retailPrice=" + retailPrice + ", orderStatus="
				+ orderStatus + ", id=" + id + ", markUp=" + markUp + "]";
	}
	
}
