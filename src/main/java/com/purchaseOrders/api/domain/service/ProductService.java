package com.purchaseOrders.api.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.purchaseOrders.api.domain.model.Product;
import com.purchaseOrders.api.domain.repository.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository repository;
	
	public ProductService(ProductRepository repository) {
		this.repository = repository;
	}

	public List<Product> listProducts(){
		return repository.findAll();
	}
	
	public Product searchProduct(Long productId) {
			Optional<Product> productCurrent = repository.findById(productId);
			return productCurrent.get();
	}
	
	public Product addProduct(Product product) {
		return repository.save(product);
	}
	
	public void removeProduct(Long productId) {
		Optional<Product> productCurrent = repository.findById(productId);
		
		repository.delete(productCurrent.get());
	}
	
	public Product updateProduct(Product product, Long productId) {
		Optional<Product> productCurrent = repository.findById(productId);
		
		if(productCurrent.isEmpty()) 
		  { 
			  throw new NoSuchElementException(); 
		  }
		  BeanUtils.copyProperties(product, productCurrent.get(), "id"); 
		  
		return repository.save(productCurrent.get());
	}
}
