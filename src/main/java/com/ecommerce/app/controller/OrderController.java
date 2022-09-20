package com.ecommerce.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.dto.order.CheckoutItemDto;
import com.ecommerce.app.dto.order.StripeResponse;
import com.ecommerce.app.service.AuthenticationService;
import com.ecommerce.app.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/create-checkout-session")
	public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtos) throws StripeException {
		
		Session session = orderService.createSession(checkoutItemDtos);
		
		return new ResponseEntity<StripeResponse>(new StripeResponse(session.getId()), HttpStatus.CREATED);
	}
	
}
