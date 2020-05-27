/**
 * 
 */
package com.javaweb.n3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaweb.n3.entity.Plan;

/**
 * @author HungDinh
 *
 */
public interface PlanRepository extends JpaRepository<Plan, Integer>{

//	@Query("SELECT p.id, p.planName, p.createdDate, p.startDate, p.endDate, p.planDescription FROM Plan p")
//	List<Plan> findAll();
	
}
