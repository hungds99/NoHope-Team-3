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

import com.javaweb.n3.dto.MaterialProductDTO;
import com.javaweb.n3.entity.Product;
import com.javaweb.n3.service.MaterialProductService;
import com.javaweb.n3.service.ProductService;

/**
 * @author HungDinh
 *
 */

@RestController
@CrossOrigin
@RequestMapping("/api/n3/product")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	MaterialProductService materialProductService;

	@GetMapping
	public List<Product> getProducts() {
		return productService.findAll();
	}
	
	@PostMapping
	public Product saveProduct(@RequestBody Product product) {
		Product p = productService.save(product);
		return p;
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable(name = "id") int id) {
		return productService.getOne(id);
	}
	
	@CrossOrigin
	@GetMapping("/insertMaterial")
	public boolean insertMaterialToProduct(@RequestParam("productId") int productId, @RequestParam("materialId") int materialId, @RequestParam("materialAmount") int materialAmount) {
		
		materialProductService.saveMaterialProduct(productId, materialId, materialAmount);
		
		return true;
	}
	
	@CrossOrigin
	@GetMapping("/search")
	public List<Product> search(@RequestParam("term") String term) {
		System.out.println(term);
		return productService.findByProductName(term);
	}
	
	@CrossOrigin
	@GetMapping("/getMaterial")
	public List<MaterialProductDTO> getMaterialOfProduct(@RequestParam("productId") int productId) {
		return productService.getMaterialOfProduct(productId);
	}
	
	
	@CrossOrigin
	@PostMapping("/getMaterial")
	public void deleteMaterialOfProduct(@RequestParam("materialId") int materialId, @RequestParam("productId") int productId) {
		materialProductService.deleteMaterialProduct(materialId, productId);
	}
	
	@CrossOrigin
	@GetMapping("/delete/{productId}")
	public void deleteProduct(@PathVariable("productId") int productId) {
		productService.deleteProduct(productId);
	}
 }
