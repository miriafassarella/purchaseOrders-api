package com.purchaseOrders.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {

	ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
	ENTITY_IN_USE("/Entity-in-use", "Entity in use"),
	MESSAGE_INCOMPREHENSIBLE("Message-incomprehesible", "Message incomprehensible");
	
	private String title;
	private String uri;
	
	ErrorType(String path, String title){
		this.uri = "https//algafood.com.br/" + path;
		this.title = title;
	}
	
	
			
}
