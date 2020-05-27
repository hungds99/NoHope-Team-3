/**
 * 
 */
package com.javaweb.n3.service;

/**
 * @author HungDinh
 *
 */
public interface MaterialProductService {

	public boolean saveMaterialProduct(int productId, int materialId, int amount);
	
	public void deleteMaterialProduct(int materialId, int productId);
	
	public void deleteProduct(int productId);
	
}
	