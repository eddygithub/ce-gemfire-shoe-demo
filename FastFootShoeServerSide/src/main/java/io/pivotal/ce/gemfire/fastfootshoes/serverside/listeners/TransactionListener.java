/**
 * 
 */
package io.pivotal.ce.gemfire.fastfootshoes.serverside.listeners;

import io.pivotal.ce.gemfire.fastfootshoes.model.Product;
import io.pivotal.ce.gemfire.fastfootshoes.model.Transaction;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.ProductRepository;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

/**
 * This will happen on the same thread as the put to the transaction region is using
 * @author lshannon
 *
 */
@Component
public class TransactionListener extends CacheListenerAdapter<String, Transaction> implements Declarable {
	
	@Autowired
	private ProductRepository productRepository;
	

	public void init(Properties props) {
	}
	
	@Override
	public void afterCreate(EntryEvent<String, Transaction> entryEvent) {
			Transaction transaction = entryEvent.getNewValue();
			if (transaction.getOrderStatus().equals(Transaction.ORDER_OPEN)) {
				Product product = productRepository.findOne(transaction.getProductId());
				product.setStockOnHand(product.getStockOnHand() - transaction.getQuantity());
				productRepository.save(product);
			}
	}
	
	

}
