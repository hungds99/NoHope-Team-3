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

import com.javaweb.n3.entity.Worker;
import com.javaweb.n3.service.WorkerService;

/**
 * @author HungDinh
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/n3/worker")
public class WorkerController {

	@Autowired
	WorkerService workerService;
	
//	@GetMapping
//	public List<Worker> getAllWorker() {
//		return workerService.findAllWorker();
//	}
	
	@GetMapping
	public List<Worker> getWorkers(@RequestParam("currentPage") int page) {
		return workerService.getWorkers((page - 1)*2, 2);
	}
	
	@PostMapping
	public void saveOrEditWorker(@RequestBody Worker worker) {
		System.out.println(worker);
		workerService.saveOrEdit(worker);
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public Worker getWorker(@PathVariable("id") int workerId) {
		return workerService.findById(workerId);
	}
	
	@CrossOrigin
	@GetMapping("/delete/{id}")
	public void deleteWorker(@PathVariable("id") int workerId) {
		workerService.delete(workerId);
	}
	
	@CrossOrigin
	@GetMapping("/search")
	public List<Worker> search(@RequestParam("term") String term) {
		return workerService.findByWorkerName(term);
	}
	
	@CrossOrigin
	@PostMapping("/changeStatus")
	public void changeWorkerStatus(@RequestParam("workerId") int workerId, @RequestParam("workerStatus") String workerStatus) {
		workerService.changeWorkerStatus(workerId, workerStatus);
	}
	
}
