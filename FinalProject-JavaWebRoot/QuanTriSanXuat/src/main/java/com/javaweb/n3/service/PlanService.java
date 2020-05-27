/**
 * 
 */
package com.javaweb.n3.service;

import java.util.List;

import com.javaweb.n3.dto.PlanDTO;
import com.javaweb.n3.entity.Plan;

/**
 * @author HungDinh
 *
 */
public interface PlanService {

	public List<Plan> findAllPlan();
	
	public void savePlan(Plan planData);
	
	void changePlanStatus(int planId, String planStatus);
	
	PlanDTO findById(int id);
	
	void deletePlan(int id);
	
}
