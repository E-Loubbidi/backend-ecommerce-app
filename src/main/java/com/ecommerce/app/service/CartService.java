package com.ecommerce.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.cart.AddToCartDto;
import com.ecommerce.app.dto.cart.CartDto;
import com.ecommerce.app.dto.cart.CartItemDto;
import com.ecommerce.app.exceptions.CustomException;
import com.ecommerce.app.model.Cart;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
	CartRepository cartRepository;

	@Autowired
	ProductService productService;
	
	public void addToCart(AddToCartDto addToCartDto, User user) {		
		
		Product product = productService.findProductById(addToCartDto.getProductId());
		
		Cart cart = new Cart();
		cart.setProduct(product);
		cart.setUser(user);
		cart.setQuantity(addToCartDto.getQuantity());
		cart.setCreatedDate(new Date());
		
		cartRepository.save(cart);
	
	}

	public CartDto listCartItems(User user) {
		
		List<Cart> carts = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
		
		List<CartItemDto> cartItemDtos = new ArrayList<>();
		double totalCost = 0;

		for(Cart cart : carts) {
			CartItemDto cartItemDto = new CartItemDto(cart);
			totalCost += cart.getProduct().getPrice() * cart.getQuantity();
			cartItemDtos.add(cartItemDto);
		}
		
		CartDto cartDto = new CartDto();
		cartDto.setCartItemDtos(cartItemDtos);
		cartDto.setTotalCost(totalCost);
		
		return cartDto;
	}
	
	public void deleteCartItem(Integer cartItemId, User user) {
		
		Optional<Cart> optionalCart = cartRepository.findById(cartItemId);
		
		if(optionalCart.isEmpty()) {
			throw new CustomException("Cart item doesn't exist " + cartItemId);
		}
		
		Cart cart = optionalCart.get();
		
		if(cart.getUser() != user) {
			throw new CustomException("Cart item doesn't belong to the user");
		}
	
		cartRepository.delete(cart);
	
	}
	
}
