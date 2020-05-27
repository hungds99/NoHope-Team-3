/**
 * 
 */
package com.javaweb.n3.service;

import java.util.List;

import com.javaweb.n3.entity.Worker;

/**
 * @author HungDinh
 *
 */
public interface WorkerService {

	List<Worker> findAllWorker();

	Worker findById(int id);

	List<Worker> findByWorkerName(String term);

	void editWorker(List<Integer> workerIds, int planId);

	void saveOrEdit(Worker worker);

	void delete(int id);
	
	void changeWorkerStatus(int id, String status);
	
	void removePlan(int planId);

	List<Worker> getWorkers(int page, int offset);
}
