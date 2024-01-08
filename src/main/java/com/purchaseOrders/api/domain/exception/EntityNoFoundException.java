package com.purchaseOrders.api.domain.exception;

public class EntityNoFoundException extends BusinessException{

	private static final long serialVersionUID = 1L;
	
	public EntityNoFoundException(String message) {
		super(message);
		
	}


}
