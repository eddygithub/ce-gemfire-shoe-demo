package io.pivotal.ce.gemfire.fastfootshoes.serverside.functions;

import io.pivotal.ce.gemfire.fastfootshoes.model.Customer;

import java.util.List;

import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;

@OnRegion(region="Transaction", id="orderCounterCaller")
public interface OrderCounterCaller {
	
	@FunctionId("countTransactions")
	public List<Integer> countTransactions(Customer customer);

}
