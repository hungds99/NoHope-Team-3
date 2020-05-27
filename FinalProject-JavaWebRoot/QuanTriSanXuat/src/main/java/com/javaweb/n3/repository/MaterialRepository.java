/**
 * 
 */
package com.javaweb.n3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaweb.n3.entity.Material;

/**
 * @author HungDinh
 *
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

	@Query("Select m from Material m where m.materialName like %:term% order by m.id ASC")
	List<Material> findByMaterialName(String term);
	
}
