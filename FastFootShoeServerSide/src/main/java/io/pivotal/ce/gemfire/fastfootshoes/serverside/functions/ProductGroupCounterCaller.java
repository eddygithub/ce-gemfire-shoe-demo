package io.pivotal.ce.gemfire.fastfootshoes.serverside.functions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;

@OnRegion(region="Product", id="productGroupCounterCaller")
public interface ProductGroupCounterCaller {
	
	@FunctionId("countByBrand")
	public List<Map<String, AtomicInteger>> countByBrand();
	
	@FunctionId("countByType")
	public List<Map<String, AtomicInteger>> countByType();

}
