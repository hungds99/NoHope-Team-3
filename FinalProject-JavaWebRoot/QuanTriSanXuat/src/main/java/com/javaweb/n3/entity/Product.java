/**
 * 
 */
package com.javaweb.n3.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author HungDinh
 *
 */

@Entity
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5919388091799017894L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String productName;

	private int productAmount;

	private String productUnit;

	private String productDescription;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<MaterialProduct> materialProduct;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<Plan> plan;

	public List<Plan> getPlan() {
		return plan;
	}

	public void setPlan(List<Plan> plan) {
		this.plan = plan;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public List<MaterialProduct> getMaterialProduct() {
		return materialProduct;
	}

	public void setMaterialProduct(List<MaterialProduct> materialProduct) {
		this.materialProduct = materialProduct;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", productAmount=" + productAmount
				+ ", productUnit=" + productUnit + ", productDescription=" + productDescription + ", materialProduct="
				+ materialProduct + ", plan=" + plan + "]";
	}

}
