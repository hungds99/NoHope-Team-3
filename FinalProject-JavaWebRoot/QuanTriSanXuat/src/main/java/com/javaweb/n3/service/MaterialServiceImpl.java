/**
 * 
 */
package com.javaweb.n3.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.n3.entity.Material;
import com.javaweb.n3.repository.MaterialRepository;

/**
 * @author HungDinh
 *
 */
@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	MaterialRepository materialRepository;
	
	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Override
	public boolean saveOrEdit(Material material) {
		Material materialExisted = null;
		try {
			materialExisted = this.findById(material.getId());
			if (materialExisted == null) {
				materialRepository.save(material);
				return true;
			} else {
				materialExisted.setMaterialName(material.getMaterialName());
				materialExisted.setMaterialPrice(material.getMaterialPrice());
				materialExisted.setMaterialAmount(material.getMaterialAmount());
				materialExisted.setMaterialUnit(material.getMaterialUnit());
				materialRepository.save(materialExisted);
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Material> findAll() {
		return materialRepository.findAll();
	}

	@Override
	public Material findById(int id) {
		return materialRepository.findById(id).orElse(null);
	}

	@Override
	public boolean deleteById(int id) {
		try {
			materialRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Material> findByMaterialName(String term) {
		return materialRepository.findByMaterialName(term);
	}

	@Override
	public List<Material> findAllByProductId(int productId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		List<Material> material = entityManager.createQuery(
				"SELECT m FROM Material m Where m.product.id = :productId",
				Material.class).setParameter("productId", productId).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		return material;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Material> getMaterials(int page, int offset) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		List<Material> materials = entityManager.createNativeQuery("SELECT * FROM Material w LIMIT :page, :offset", Material.class)
				.setParameter("page", page)
				.setParameter("offset", offset).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		return materials;
	}
	
}
