package com.purchaseOrders.api.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.purchaseOrders.api.domain.exception.EntityInUseException;
import com.purchaseOrders.api.domain.exception.ProductNotFoundException;
import com.purchaseOrders.api.domain.model.Product;
import com.purchaseOrders.api.domain.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	

	public List<Product> listProducts(){
		return repository.findAll();
	}
	
	public Product searchProduct(Long productId) {
		try {
			Optional<Product> productCurrent = repository.findById(productId);
			return productCurrent.get();
		}catch(NoSuchElementException ex) {
			throw new ProductNotFoundException(productId);
		}
			
	}
	
	public Product addProduct(Product product) {
		return repository.save(product);
	}
	
	public void removeProduct(Long productId) {
		try {
			Optional<Product> productCurrent = repository.findById(productId);
			
			repository.delete(productCurrent.get());
		}catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format("The product code %s is in use.", productId));
		}catch (NoSuchElementException ex) {
			throw new ProductNotFoundException(productId);
		}
		
	}
	
	public Product updateProduct(Product product, Long productId) {
		Optional<Product> productCurrent = repository.findById(productId);
		
		try {
			if(productCurrent.isEmpty()) 
			  { 
				  throw new NoSuchElementException(); 
			  }
			  BeanUtils.copyProperties(product, productCurrent.get(), "id"); 
			  
			return repository.save(productCurrent.get());
			
		}catch(NoSuchElementException ex) {
			throw new ProductNotFoundException(productId);
		}
		
		
		
	}
}
