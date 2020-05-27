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

import com.javaweb.n3.entity.Plan;
import com.javaweb.n3.entity.Worker;
import com.javaweb.n3.repository.WorkerRepository;

/**
 * @author HungDinh
 *
 */
@Service
@Transactional
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	WorkerRepository workerRepository;

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Override
	public Worker findById(int id) {
		return workerRepository.findById(id).orElse(null);
	}

	@Override
	public List<Worker> findByWorkerName(String term) {
		return workerRepository.findByWorkerName(term);
	}

	@Override
	public void editWorker(List<Integer> workerIds, int planId) {

		List<Worker> workers = workerRepository.findAllById(workerIds);

		for (Worker worker : workers) {
			Plan plan = new Plan();
			plan.setId(planId);

			worker.setPlan(plan);
			worker.setWorkerStatus("DANG_LAM_VIEC");

		}

		workerRepository.saveAll(workers);
	}

	@Override
	public void saveOrEdit(Worker worker) {
		Worker workerExisted = null;
		try {
			workerExisted = workerRepository.findById(worker.getId()).orElse(null);
			if (workerExisted == null) {
				worker.setWorkerStatus("DANG_RANH");
				workerRepository.save(worker);
			} else {
				workerExisted.setWorkerName(worker.getWorkerName());
				workerExisted.setWorkerBirthday(worker.getWorkerBirthday());
				workerExisted.setWorkerNumber(worker.getWorkerNumber());
				workerExisted.setWorkerAddress(worker.getWorkerAddress());
				workerExisted.setWorkerStatus(worker.getWorkerStatus());
				workerRepository.save(workerExisted);
			}
		} catch (Exception e) {
		}

	}

	@Override
	public List<Worker> findAllWorker() {
		return workerRepository.findAll();
	}

	@Override
	public void delete(int id) {
		workerRepository.deleteById(id);
	}

	@Override
	public void changeWorkerStatus(int id, String status) {
		Worker worker = workerRepository.findById(id).orElse(null);
		worker.setWorkerStatus(status);
		workerRepository.save(worker);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removePlan(int planId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		List<Worker> workers = entityManager.createQuery("SELECT w FROM Worker w Where w.plan.id = :planId")
				.setParameter("planId", planId).getResultList();

		for (Worker worker : workers) {
			worker.setWorkerStatus("DANG_RANH");
			worker.setPlan(null);
		}

		workerRepository.saveAll(workers);

		entityManager.getTransaction().commit();
		entityManager.close();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Worker> getWorkers(int page, int offset) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		List<Worker> workers = entityManager.createNativeQuery("SELECT * FROM Worker w LIMIT :page, :offset", Worker.class)
				.setParameter("page", page)
				.setParameter("offset", offset).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		return workers;
	}

}
