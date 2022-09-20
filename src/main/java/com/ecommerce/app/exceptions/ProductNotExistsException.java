package com.ecommerce.app.exceptions;

public class ProductNotExistsException extends IllegalArgumentException {
	
	public ProductNotExistsException(String msg) {
		super(msg);
	}
	
}
