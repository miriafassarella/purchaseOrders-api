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


import com.purchaseOrders.api.domain.exception.TransactionNotFoundException;
import com.purchaseOrders.api.domain.model.Transaction;
import com.purchaseOrders.api.domain.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	public TransactionService service;
	
	public TransactionController(TransactionService service) {
		this.service = service;
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<Transaction> listTransaction(){
		return service.listTransaction();
	}
	
	@GetMapping("/{transactionId}")
	public ResponseEntity<Transaction> searchTransaction(@PathVariable Long transactionId){
		
		try {
			Transaction transactionCurrent = service.searchTransaction(transactionId);
			return ResponseEntity.status(HttpStatus.FOUND).body(transactionCurrent);
		}catch (NoSuchElementException ex) {
			throw new TransactionNotFoundException(transactionId);
		}
	}
	
	@PostMapping
	public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction){
		Transaction transactionSave = service.addTransaction(transaction);
		return ResponseEntity.status(HttpStatus.CREATED).body(transactionSave);
	}
	
	@DeleteMapping("/{transactionId}")
	public ResponseEntity<Transaction> removeTransaction(@PathVariable Long transactionId){

		try {
			service.removeTransaction(transactionId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}catch (NoSuchElementException ex) {
			throw new TransactionNotFoundException(transactionId);
		}
	}
	
	@PutMapping("/{transactionId}")
	public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction, @PathVariable Long transactionId){
		try {
			Transaction transactionSave  = service.updateTransaction(transaction, transactionId);
			return ResponseEntity.status(HttpStatus.OK).body(transactionSave);
		}catch (NoSuchElementException ex) {
			throw new TransactionNotFoundException(transactionId);
		}
	}
	
}