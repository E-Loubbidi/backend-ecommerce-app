package com.ecommerce.app.exceptions;

public class AuthenticationFailException extends IllegalArgumentException {
	
	public AuthenticationFailException(String msg) {
		super(msg);
	}

}
