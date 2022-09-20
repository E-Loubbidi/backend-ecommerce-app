package com.ecommerce.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.app.common.ApiResponse;

@ControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(value = CustomException.class)
	public final ResponseEntity<ApiResponse> handleException(CustomException customException) {
		return new ResponseEntity<ApiResponse>(new ApiResponse(false, customException.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = AuthenticationFailException.class)
	public final ResponseEntity<ApiResponse> handleAuthenticationFailException(AuthenticationFailException exception) {
		return new ResponseEntity<ApiResponse>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = ProductNotExistsException.class)
	public final ResponseEntity<ApiResponse> handleProductNotExistException(ProductNotExistsException exception) {
		return new ResponseEntity<ApiResponse>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
	}

}
