/**
 * 
 */
package com.javaweb.n3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javaweb.n3.entity.Worker;

/**
 * @author HungDinh
 *
 */
public interface WorkerRepository extends JpaRepository<Worker, Integer> {

	@Query("Select w from Worker w where w.workerStatus = 'DANG_RANH' AND w.workerName like %:term% order by w.id ASC")
	List<Worker> findByWorkerName(String term);
}
