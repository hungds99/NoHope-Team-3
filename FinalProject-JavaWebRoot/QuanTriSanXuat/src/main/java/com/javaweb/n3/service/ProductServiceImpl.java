/**
 * 
 */
package com.javaweb.n3.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.n3.dto.MaterialProductDTO;
import com.javaweb.n3.entity.Product;
import com.javaweb.n3.repository.ProductRepository;

/**
 * @author HungDinh
 *
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	MaterialService materialService;

	@Autowired
	MaterialProductService materialProductService;
	
	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product save(Product product) {
		Product p = productRepository.save(product);
		return p;
	}

	@Override
	public Product getOne(int id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public List<Product> findByProductName(String productName) {
		return productRepository.findByProductName(productName);
	}

	@Override
	public List<MaterialProductDTO> getMaterialOfProduct(int productId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		List<Object[]> material = entityManager.createQuery(
				"SELECT m.id, m.materialName, m.materialUnit, m.materialPrice, mp.amount "
				+ "FROM Material m LEFT JOIN m.materialProduct mp ON m.id = mp.material.id "
				+ "WHERE mp.product.id = :param", Object[].class
				).setParameter("param", productId).getResultList();

		List<MaterialProductDTO> dto = new ArrayList<MaterialProductDTO>();
		
		for (Object[] objects : material) {
			MaterialProductDTO n = new MaterialProductDTO();
			n.setId((int)objects[0]);
			n.setMaterialName(objects[1].toString());
			n.setMaterialUnit(objects[2].toString());
			n.setMaterialPrice((double) objects[3]);
			n.setMaterialProductAmount((int) objects[4]);
			dto.add(n);
		}
		
		entityManager.getTransaction().commit();
		entityManager.close();
		return dto;
	}

	@Override
	public void deleteProduct(int productId) {
		materialProductService.deleteProduct(productId);
		productRepository.deleteById(productId);
	}

}
