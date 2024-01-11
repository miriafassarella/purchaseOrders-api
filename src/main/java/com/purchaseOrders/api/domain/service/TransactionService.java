package com.purchaseOrders.api.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.purchaseOrders.api.domain.exception.BusinessException;
import com.purchaseOrders.api.domain.exception.DirectionNotFoundException;
import com.purchaseOrders.api.domain.exception.EntityNoFoundException;
import com.purchaseOrders.api.domain.exception.ProductNotFoundException;
import com.purchaseOrders.api.domain.exception.TransactionNotFoundException;
import com.purchaseOrders.api.domain.model.Direction;
import com.purchaseOrders.api.domain.model.Product;
import com.purchaseOrders.api.domain.model.Transaction;
import com.purchaseOrders.api.domain.repository.DirectionRepository;
import com.purchaseOrders.api.domain.repository.ProductRepository;
import com.purchaseOrders.api.domain.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
		
	public List<Transaction> listTransaction(){
		return repository.findAll();
	}
	
	public Transaction searchTransaction(Long transactionId) {
		try {
			Optional<Transaction> transactionCurrent = repository.findById(transactionId);
			return transactionCurrent.get();
		}catch (NoSuchElementException ex) {
			throw new TransactionNotFoundException(transactionId);
		}
		
	}
	/*--------------------------------------------------------------------------------------------*/
	public Transaction addTransaction(Transaction transaction) {
		Long productId = transaction.getProduct().getId();
		Long directionId = transaction.getDirection().getId();
		
		Optional<Product> product = productRepository.findById(transaction.getProduct().getId());
		
		try {
			if(product.isPresent()) {
				return repository.save(transaction);
			}else {
				throw new BusinessException(String.format("Product code %s does not exist!", productId));
			}
			
		}catch(DataIntegrityViolationException ex) {
			
			throw new BusinessException(String.format("Code direction %s does not exist!", directionId));
			
		}
	}
	/*----------------------------------------------------------------------------------------------*/
	public void removeTransaction(Long transactionId) {
		try {
			Optional<Transaction> transactionCurrent = repository.findById(transactionId);
			repository.delete(transactionCurrent.get());
		}catch (NoSuchElementException ex) {
			throw new TransactionNotFoundException(transactionId);
		}
	}
	
	public Transaction updateTransaction(Transaction transaction, Long transactionId) {
		Optional<Transaction> transactionCurrent = repository.findById(transactionId);
		
		if(transactionCurrent.isEmpty()) 
		  { 
			  throw new NoSuchElementException(); 
		  }
		  BeanUtils.copyProperties(transaction, transactionCurrent.get(), "id"); 
		  
		return repository.save(transactionCurrent.get());
	}
}
