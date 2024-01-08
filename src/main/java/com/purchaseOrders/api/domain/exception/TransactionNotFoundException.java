package com.purchaseOrders.api.domain.exception;

public class TransactionNotFoundException extends EntityNoFoundException{

	private static final long serialVersionUID = 1L;

	public TransactionNotFoundException(String message) {
		super(message);
		
	}

	public TransactionNotFoundException(Long transactionId) {
		this(String.format("There is no transaction record with the code %d", transactionId));
	}
}
