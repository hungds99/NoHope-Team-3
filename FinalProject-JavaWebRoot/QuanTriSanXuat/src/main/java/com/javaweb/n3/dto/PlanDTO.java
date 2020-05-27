package com.javaweb.n3.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.javaweb.n3.entity.Product;
import com.javaweb.n3.entity.Worker;

/**
 * @author HungDinh
 *
 */
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class PlanDTO implements Serializable{

	private static final long serialVersionUID = -3305632654291304663L;

	private int id;

	private String planName;

	private Date createdDate;

	private Date startDate;

	private Date endDate;

	private String planAddress;

	private String planCustomer;

	private String planStatus;

	private String planDescription;

//	@JsonIgnore
	private List<Worker> worker;

//	@JsonIgnore
	private Product product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPlanAddress() {
		return planAddress;
	}

	public void setPlanAddress(String planAddress) {
		this.planAddress = planAddress;
	}

	public String getPlanCustomer() {
		return planCustomer;
	}

	public void setPlanCustomer(String planCustomer) {
		this.planCustomer = planCustomer;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getPlanDescription() {
		return planDescription;
	}

	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}

	public List<Worker> getWorker() {
		return worker;
	}

	public void setWorker(List<Worker> worker) {
		this.worker = worker;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "PlanDTO [id=" + id + ", planName=" + planName + ", createdDate=" + createdDate + ", startDate="
				+ startDate + ", endDate=" + endDate + ", planAddress=" + planAddress + ", planCustomer=" + planCustomer
				+ ", planStatus=" + planStatus + ", planDescription=" + planDescription + "]";
	}

}
