package com.purchaseOrders.api.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
	
	private TransactionRepository repository;
	private ProductRepository productRepository;
	private DirectionRepository directionRepository;
	
	public TransactionService(TransactionRepository repository) {
		this.repository = repository;
	}

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
		
				return repository.save(transaction);	
		
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
