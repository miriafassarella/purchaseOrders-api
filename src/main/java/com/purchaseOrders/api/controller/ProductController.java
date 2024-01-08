package com.purchaseOrders.api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.purchaseOrders.api.domain.exception.ProductNotFoundException;
import com.purchaseOrders.api.domain.model.Product;
import com.purchaseOrders.api.domain.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	/*@Autowired*/
	public ProductService service;
	
	public ProductController(ProductService service) {
		this.service = service;
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<Product> listProducts(){
		return service.listProducts();
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Product> searchProduct(@PathVariable Long productId){
		try {
			Product productCurrent = service.searchProduct(productId);
			return ResponseEntity.status(HttpStatus.FOUND).body(productCurrent);
		}catch(NoSuchElementException ex) {
			throw new ProductNotFoundException(productId);
		}
		
	}
	
	@PostMapping
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		Product productSave = service.addProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productSave);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Product> removeProduct(@PathVariable Long productId){
		try {
			service.removeProduct(productId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}catch (NoSuchElementException ex) {
			throw new ProductNotFoundException(productId);
		}
		
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long productId){
		try {
			Product productSave  = service.updateProduct(product, productId);
			return ResponseEntity.status(HttpStatus.OK).body(productSave);
		}catch(NoSuchElementException ex) {
			throw new ProductNotFoundException(productId);
		}
		
	}
	
}
