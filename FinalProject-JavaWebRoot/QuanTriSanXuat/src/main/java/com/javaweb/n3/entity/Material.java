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
public class Material implements Serializable {

	private static final long serialVersionUID = -2592245604053696185L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String materialName;

	private int materialAmount;

	private String materialUnit;

	private double materialPrice;

	@OneToMany(mappedBy = "material")
	@JsonIgnore
	private List<MaterialProduct> materialProduct;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public int getMaterialAmount() {
		return materialAmount;
	}

	public void setMaterialAmount(int materialAmount) {
		this.materialAmount = materialAmount;
	}

	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	public double getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(double materialPrice) {
		this.materialPrice = materialPrice;
	}

	public List<MaterialProduct> getMaterialProduct() {
		return materialProduct;
	}

	public void setMaterialProduct(List<MaterialProduct> materialProduct) {
		this.materialProduct = materialProduct;
	}

}
