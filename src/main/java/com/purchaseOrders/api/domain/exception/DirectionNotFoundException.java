package com.purchaseOrders.api.domain.exception;

public class DirectionNotFoundException extends EntityNoFoundException {

	
	private static final long serialVersionUID = 1L;

	public DirectionNotFoundException(String message) {
		super(message);
		
	}

	public DirectionNotFoundException(Long directionId) {
		this(String.format("There is no direction record with the code %d", directionId));
	}
}
