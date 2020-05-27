/**
 * 
 */
package com.javaweb.n3.service;

import java.util.List;

import com.javaweb.n3.entity.Material;

/**
 * @author HungDinh
 *
 */
public interface MaterialService {

	boolean saveOrEdit(Material material);
	
	List<Material> findAll();
	
	Material findById(int id);
	
	boolean deleteById(int id);
	
	List<Material> findByMaterialName(String term);
	
	List<Material> findAllByProductId(int productId);
	
	List<Material> getMaterials(int page, int offset);
}
