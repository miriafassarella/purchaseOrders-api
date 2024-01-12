package com.purchaseOrders.api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.purchaseOrders.api.domain.exception.BusinessException;
import com.purchaseOrders.api.domain.exception.EntityInUseException;


import com.purchaseOrders.api.domain.exception.TransactionNotFoundException;
import com.purchaseOrders.api.domain.model.Transaction;
import com.purchaseOrders.api.domain.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	public TransactionService service;
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<Transaction> listTransaction(){
		return service.listTransaction();
	}
	
	@GetMapping("/{transactionId}")
	public ResponseEntity<Transaction> searchTransaction(@PathVariable Long transactionId){
		
		Transaction transactionCurrent = service.searchTransaction(transactionId);
		return ResponseEntity.status(HttpStatus.FOUND).body(transactionCurrent);
		
	}
	
	@PostMapping
	public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction){
		try {
			Transaction transactionSave = service.addTransaction(transaction);
			return ResponseEntity.status(HttpStatus.CREATED).body(transactionSave);
		}catch(BusinessException ex) {
			throw new EntityInUseException(ex.getMessage());
		}
		
		
	}
	
	@DeleteMapping("/{transactionId}")
	public ResponseEntity<Transaction> removeTransaction(@PathVariable Long transactionId){

		service.removeTransaction(transactionId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping("/{transactionId}")
	public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction, @PathVariable Long transactionId){
		try {
			Transaction transactionSave  = service.updateTransaction(transaction, transactionId);
			return ResponseEntity.status(HttpStatus.OK).body(transactionSave);
		}catch (NoSuchElementException ex) {
			throw new TransactionNotFoundException(transactionId);
		}catch(BusinessException ex) {
			throw new EntityInUseException(ex.getMessage());
		}
	}
	
}
