/**
 * 
 */
package com.javaweb.n3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.javaweb.common.utils.ViewNameUtils;


/**
 * @author HungDinh
 *
 */

@Controller
public class N3Controller {

	@GetMapping("/production")
	public String getProductionPage() {
		return ViewNameUtils.VIEW_PRODUCTION_PAGE;
	}
	
	@GetMapping("/material")
	public String getMaterialPage() {
		return  ViewNameUtils.VIEW_MATERIAL_PAGE;
	}
	
	@GetMapping("/product")
	public String getItemPage() {
		return ViewNameUtils.VIEW_PRODUCT_PAGE;
	}
	
	@GetMapping("/plan")
	public String getPlanProductionPage() {
		return ViewNameUtils.VIEW_PLAN_PRODUCTION_PAGE;
	}
 	
}
