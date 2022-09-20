package com.ecommerce.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.model.Wishlist;
import com.ecommerce.app.service.AuthenticationService;
import com.ecommerce.app.service.WishlistService;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
	
	@Autowired
	WishlistService wishlistService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToWishlist(@RequestBody Product product, @RequestParam("token") String token) {
		
		authenticationService.authentiate(token);
		
		User user  = authenticationService.getUser(token);
		
		if(wishlistService.isProductPresent(product)) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Product already added to the wishlist"), HttpStatus.BAD_REQUEST);
		}
		
		Wishlist wishlist = new Wishlist(user, product);
		
		wishlistService.createWishList(wishlist);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product added to the wishlist"), HttpStatus.CREATED);
	}
	
	@GetMapping("/{token}")
	public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {
		
		authenticationService.authentiate(token);
		
		User user = authenticationService.getUser(token);
		
		List<ProductDto> productDtos =  wishlistService.getWishListForUser(user);
		
		return new ResponseEntity<List<ProductDto>>(productDtos, HttpStatus.OK);
	} 
}
