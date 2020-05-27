/**
 * 
 */
package com.javaweb.n3.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.n3.dto.PlanDTO;
import com.javaweb.n3.entity.Plan;
import com.javaweb.n3.service.PlanService;

/**
 * @author HungDinh
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/n3/plan")
public class PlanController {

	@Autowired
	PlanService planService;
	
	@GetMapping
	public List<Plan> getPlans() {
		return planService.findAllPlan();
	}
	
	@CrossOrigin
	@PostMapping
	public void savePlan(@RequestBody Plan planData) {
		System.out.println(planData);
		planService.savePlan(planData);
	}
	
	@CrossOrigin
	@PostMapping("/changeStatus")
	public void changePlanStatus(@RequestParam("planId") int planId, @RequestParam("planStatus") String planStatus) {
		planService.changePlanStatus(planId, planStatus);
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public PlanDTO getPlanById(@PathVariable("id") int id) {
		return planService.findById(id);
	}
	
	@CrossOrigin
	@GetMapping("/delete/{id}")
	public void deletePlan(@PathVariable("id") int id) {
		planService.deletePlan(id);
	}
	
}
