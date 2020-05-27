/**
 * 
 */
package com.javaweb.n3.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author HungDinh
 *
 */
@Entity
public class Worker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String workerName;

	private Date workerBirthday;

	private int workerNumber;

	private String workerAddress;

	private String workerStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id")
	@JsonIgnore
	private Plan plan;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public Date getWorkerBirthday() {
		return workerBirthday;
	}

	public void setWorkerBirthday(Date workerBirthday) {
		this.workerBirthday = workerBirthday;
	}

	public int getWorkerNumber() {
		return workerNumber;
	}

	public void setWorkerNumber(int workerNumber) {
		this.workerNumber = workerNumber;
	}

	public String getWorkerAddress() {
		return workerAddress;
	}

	public void setWorkerAddress(String workerAddress) {
		this.workerAddress = workerAddress;
	}

	public String getWorkerStatus() {
		return workerStatus;
	}

	public void setWorkerStatus(String workerStatus) {
		this.workerStatus = workerStatus;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	@Override
	public String toString() {
		return "Worker [id=" + id + ", workerName=" + workerName + ", workerBirthday=" + workerBirthday
				+ ", workerNumber=" + workerNumber + ", workerAddress=" + workerAddress + ", workerStatus="
				+ workerStatus + "]";
	}
	
	

}
