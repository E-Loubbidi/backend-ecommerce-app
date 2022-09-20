package com.ecommerce.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.User;
import com.ecommerce.app.model.Wishlist;
import com.ecommerce.app.repository.WishlistRepository;

@Service
public class WishlistService {
	
	@Autowired
	WishlistRepository wishlistRepository;
	
	@Autowired
	ProductService productService;
	
	public void createWishList(Wishlist wishlist) {
		wishlistRepository.save(wishlist);
	}

	public boolean isProductPresent(Product product) {
		return Objects.nonNull(wishlistRepository.findByProduct(product));
	}

	public List<ProductDto> getWishListForUser(User user) {
		final List<Wishlist> wishlists = wishlistRepository.findAllByUserOrderByCreatedDateDesc(user);
		List<ProductDto> productDtos = new ArrayList<>();
		for(Wishlist wishlist : wishlists) {
			productDtos.add(productService.getProductDto(wishlist.getProduct()));
		}
		return productDtos;
	}

}
