package com.purchaseOrders.api.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.purchaseOrders.api.domain.model.Transaction;
import com.purchaseOrders.api.domain.repository.TransactionRepository;

@Service
public class TransactionService {
	
	private TransactionRepository repository;
	
	public TransactionService(TransactionRepository repository) {
		this.repository = repository;
	}

	public List<Transaction> listTransaction(){
		return repository.findAll();
	}
	
	public Transaction searchTransaction(Long transactionId) {
		Optional<Transaction> transactionCurrent = repository.findById(transactionId);
		
		return transactionCurrent.get();
	}
	
	public Transaction addTransaction(Transaction transaction) {
		return repository.save(transaction);
	}
	
	public void removeTransaction(Long transactionId) {
		Optional<Transaction> transactionCurrent = repository.findById(transactionId);
		
		repository.delete(transactionCurrent.get());
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
