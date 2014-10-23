/**
 * 
 */
package io.pivotal.ce.gemfire.fastfootshoes.repositories;

import io.pivotal.ce.gemfire.fastfootshoes.model.Product;

import java.util.Collection;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;

/**
 * @author lshannon
 * @author Josh Long
 */
public interface ProductRepository extends GemfireRepository<Product, String> {

	Collection<Product> findAll();

	@Query("SELECT * FROM /Product p WHERE p.stockOnHand > 0")
	Collection<Product> findAllWithStock();

	@Query("SELECT * FROM /Product p WHERE p.brand = $1 and p.\"type\"= $2 and p.gender = $3")
	Collection<Product> findAllByBrandTypeGender(String brand, String type,
			String gender);

	@Query("SELECT * FROM /Product p   WHERE  p.brand = $1 and  p.\"type\" = $2 and  p.gender = $3 and  p.stockOnHand > 0")
	Collection<Product> findAllWithStockByBrandTypeGender(String brand,
			String type, String gender);

    @Query("SELECT * FROM /Product p WHERE p.\"type\" LIKE $1 and p.stockOnHand > 0")
    Collection<Product> findAllWithStockByBrand( String brand) ;

	Product findById(String id);

}
