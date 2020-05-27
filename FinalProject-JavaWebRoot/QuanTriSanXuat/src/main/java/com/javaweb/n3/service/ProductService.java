/**
 * 
 */
package com.javaweb.n3.service;

import java.util.List;

import com.javaweb.n3.dto.MaterialProductDTO;
import com.javaweb.n3.entity.Product;

/**
 * @author HungDinh
 *
 */
public interface ProductService {

	List<Product> findAll();
	
	Product save(Product product);
	
	Product getOne(int id);
	
	List<Product> findByProductName(String productName);
	
	List<MaterialProductDTO> getMaterialOfProduct(int productId);
	
	void deleteProduct(int productId);
}
