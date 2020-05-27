/**
 * 
 */
package com.javaweb.n3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaweb.n3.entity.Product;

/**
 * @author HungDinh
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("Select p from Product p where p.productName like %:term% order by p.id ASC")
	List<Product> findByProductName(String term);
	
}
