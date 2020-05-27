/**
 * 
 */
package com.javaweb.n3.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.n3.dto.PlanDTO;
import com.javaweb.n3.entity.Plan;
import com.javaweb.n3.entity.Product;
import com.javaweb.n3.entity.Worker;
import com.javaweb.n3.repository.PlanRepository;
import com.javaweb.n3.repository.ProductRepository;

/**
 * @author HungDinh
 *
 */
@Service
@Transactional
public class PlanServiceImpl implements PlanService {

	@Autowired
	PlanRepository planRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	WorkerService workerService;

	@Override
	public List<Plan> findAllPlan() {
		List<Plan> plans = planRepository.findAll();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

		List<Plan> planResult = new ArrayList<Plan>();
		for (Plan plan : plans) {
			Plan newPlan = new Plan();
			newPlan.setId(plan.getId());
			newPlan.setPlanName(plan.getPlanName());
			newPlan.setCreatedDate(plan.getCreatedDate());
			newPlan.setStartDate(plan.getStartDate());
			newPlan.setEndDate(plan.getEndDate());
			newPlan.setPlanDescription(plan.getPlanDescription());
			newPlan.setPlanAddress(plan.getPlanAddress());
			newPlan.setPlanStatus(plan.getPlanStatus());
			planResult.add(newPlan);
		}

		return planResult;
	}

	@Override
	public void savePlan(Plan planData) {
		System.out.println(planData.getId());
		Plan planExisted = planRepository.findById(planData.getId()).orElse(null);
		if (planExisted != null) {
			planExisted.setPlanName(planData.getPlanName());
			planExisted.setPlanDescription(planData.getPlanDescription());
			planExisted.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
			planExisted.setStartDate(planData.getStartDate());
			planExisted.setEndDate(planData.getEndDate());
			planExisted.setPlanAddress(planData.getPlanAddress());
			planExisted.setPlanCustomer(planData.getPlanCustomer());

			Product product = new Product();
			product.setId(planData.getProduct().getId());
			planExisted.setProduct(product);

			List<Integer> workerIds = new ArrayList<Integer>();
			for (Worker worker : planData.getWorker()) {
				workerIds.add(worker.getId());
			}

			Plan planSaved = planRepository.save(planExisted);

			workerService.editWorker(workerIds, planSaved.getId());
		} else {
			Plan plan = new Plan();
			plan.setPlanName(planData.getPlanName());
			plan.setPlanDescription(planData.getPlanDescription());
			plan.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
			plan.setStartDate(planData.getStartDate());
			plan.setEndDate(planData.getEndDate());
			plan.setPlanStatus("CHUA_THUC_HIEN");
			plan.setPlanAddress(planData.getPlanAddress());
			plan.setPlanCustomer(planData.getPlanCustomer());

			Product product = new Product();
			product.setId(planData.getProduct().getId());
			plan.setProduct(product);

			List<Integer> workerIds = new ArrayList<Integer>();
			for (Worker worker : planData.getWorker()) {
				workerIds.add(worker.getId());
			}

			Plan planSaved = planRepository.save(plan);

			workerService.editWorker(workerIds, planSaved.getId());
		}

	}

	@Override
	public void changePlanStatus(int planId, String planStatus) {

		Plan plan = planRepository.findById(planId).orElse(null);
		plan.setPlanStatus(planStatus);

		planRepository.save(plan);

	}

	@Override
	public PlanDTO findById(int planId) {
		PlanDTO planData = new PlanDTO();

		Plan plan = planRepository.findById(planId).orElse(null);
		planData.setId(plan.getId());
		planData.setPlanName(plan.getPlanName());
		planData.setCreatedDate(plan.getCreatedDate());
		planData.setStartDate(plan.getStartDate());
		planData.setEndDate(plan.getEndDate());
		planData.setPlanDescription(plan.getPlanDescription());
		planData.setPlanAddress(plan.getPlanAddress());
		planData.setPlanCustomer(plan.getPlanCustomer());
		planData.setPlanStatus(plan.getPlanStatus());

		Product productData = new Product();
		productData = plan.getProduct();

		List<Worker> workerData = new ArrayList<Worker>(plan.getWorker());

		planData.setProduct(productData);
		planData.setWorker(workerData);

		return planData;
	}

	@Override
	public void deletePlan(int id) {
		workerService.removePlan(id);
		planRepository.deleteById(id);
	}

}
