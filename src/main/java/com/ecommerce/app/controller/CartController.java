package com.ecommerce.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.cart.AddToCartDto;
import com.ecommerce.app.dto.cart.CartDto;
import com.ecommerce.app.model.User;
import com.ecommerce.app.service.AuthenticationService;
import com.ecommerce.app.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token) {
		
		authenticationService.authentiate(token);
		
		User user = authenticationService.getUser(token);
		
		cartService.addToCart(addToCartDto, user);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product added to cart"), HttpStatus.CREATED);
		
	}
	
	@GetMapping("/items")
	public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) {
		
		authenticationService.authentiate(token);
		
		User user = authenticationService.getUser(token);
		
		CartDto cartDto = cartService.listCartItems(user);
		
		return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer cartItemId, @RequestParam("token") String token) {
		
		authenticationService.authentiate(token);
		
		User user = authenticationService.getUser(token);
		
		cartService.deleteCartItem(cartItemId, user);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Produt deleted from the cart"), HttpStatus.OK);
	
	}

}
