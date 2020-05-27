/**
 * 
 */
package com.javaweb.n3.dto;

/**
 * @author HungDinh
 *
 */
public class MaterialProductDTO {

	private int id;

	private String materialName;

	private int materialProductAmount;

	private String materialUnit;

	private double materialPrice;

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

	public int getMaterialProductAmount() {
		return materialProductAmount;
	}

	public void setMaterialProductAmount(int materialProductAmount) {
		this.materialProductAmount = materialProductAmount;
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

}
