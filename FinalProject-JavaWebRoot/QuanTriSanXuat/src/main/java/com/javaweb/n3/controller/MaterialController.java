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

import com.javaweb.n3.entity.Material;
import com.javaweb.n3.service.MaterialService;

/**
 * @author HungDinh
 *
 */

@RestController
@CrossOrigin
@RequestMapping("/api/n3/material")
public class MaterialController {
	
	@Autowired
	MaterialService materialService;
	

//	@GetMapping
//	public List<Material> findAll() {
//		return materialService.findAll();
//	}
	
	@GetMapping
	public List<Material> getMaterials(@RequestParam("currentPage") int page) {
		return materialService.getMaterials((page - 1)*2, 2);
	}
	
	@PostMapping
	public void addMaterial(@RequestBody Material material) {
		if (materialService.saveOrEdit(material)) {
			System.out.println("Thêm mới thành công !!");
			return ;
		}
		System.out.println("Thêm mới thất bại !!");
	}
	
	@GetMapping("/{id}")
	public Material getMaterial(@PathVariable(name = "id") int id) {
		return materialService.findById(id);
	}
	
	@GetMapping("/delete/{id}")
	public boolean deleteMaterial(@PathVariable(name = "id") int id) {
		return materialService.deleteById(id);
	}
	
	@GetMapping("/search")
	@CrossOrigin
	public List<Material> getMaterialByName(@RequestParam("term") String term) {
		return materialService.findByMaterialName(term);
	}
	
	
}
