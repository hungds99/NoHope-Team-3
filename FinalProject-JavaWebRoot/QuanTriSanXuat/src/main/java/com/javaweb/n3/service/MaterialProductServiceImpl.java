/**
 * 
 */
package com.javaweb.n3.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.n3.entity.Material;
import com.javaweb.n3.entity.MaterialProduct;
import com.javaweb.n3.entity.Product;
import com.javaweb.n3.repository.MaterialProductRepository;
import com.javaweb.n3.repository.MaterialRepository;

/**
 * @author HungDinh
 *
 */
@Service
@Transactional
public class MaterialProductServiceImpl implements MaterialProductService {

	@Autowired
	MaterialProductRepository materialProductRepository;
	
	@Autowired
	MaterialRepository materialRepository;
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@Override
	public boolean saveMaterialProduct(int productId, int materialId, int amount) {
		// Check material existed
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		MaterialProduct materialProductExisted = entityManager.createQuery("Select m From MaterialProduct m Where product_Id = :productId AND material_Id = :materialId", MaterialProduct.class)
						.setParameter("productId", productId)
						.setParameter("materialId", materialId).getResultList().stream().findFirst().orElse(null);
		
		if (materialProductExisted != null) {
			materialProductExisted.setAmount(materialProductExisted.getAmount() + amount);
			materialProductRepository.save(materialProductExisted);
		} else {
			Product product = new Product();
			product.setId(productId);
			
			Material material = new Material();
			material.setId(materialId);
			
			MaterialProduct materialProduct = new MaterialProduct();
			materialProduct.setProduct(product);
			materialProduct.setMaterial(material);
			materialProduct.setAmount(amount);
			
			materialProductRepository.save(materialProduct);
		}
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return true;
		
	}

	@Override
	public void deleteMaterialProduct(int materialId, int productId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		entityManager.createNativeQuery("Delete From Material_Product Where product_Id = :productId AND material_Id = :materialId")
						.setParameter("productId", productId)
						.setParameter("materialId", materialId)
						.executeUpdate();
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void deleteProduct(int productId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		entityManager.createNativeQuery("Delete From Material_Product Where product_Id = :productId")
						.setParameter("productId", productId)
						.executeUpdate();
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
	}
	
}
