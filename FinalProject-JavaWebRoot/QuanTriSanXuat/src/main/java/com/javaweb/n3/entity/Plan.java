/**
 * 
 */
package com.javaweb.n3.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author HungDinh
 *
 */
@Entity
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String planName;

//	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

//	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

//	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	private String planAddress;

	private String planCustomer;

	private String planStatus;

	private String planDescription;

	@OneToMany(mappedBy = "plan")
	private List<Worker> worker;

	@ManyToOne
	@JoinColumn(name = "product_id")
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

	@Override
	public String toString() {
		return "Plan [id=" + id + ", planName=" + planName + ", createdDate=" + createdDate + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", planAddress=" + planAddress + ", planCustomer=" + planCustomer
				+ ", planStatus=" + planStatus + ", planDescription=" + planDescription + "]";
	}

}
