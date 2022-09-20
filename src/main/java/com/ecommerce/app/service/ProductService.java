package com.ecommerce.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.exceptions.ProductNotExistsException;
import com.ecommerce.app.model.Category;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public void createProduct(ProductDto productDto, Category category) {
		Product product = new Product();
		product.setName(productDto.getName());
		product.setImageUrl(productDto.getImageUrl());
		product.setPrice(productDto.getPrice());
		product.setDescription(productDto.getDescription());
		product.setCategory(category);
		productRepository.save(product);
	}
	
	public ProductDto getProductDto(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setId(product.getId());
		productDto.setName(product.getName());
		productDto.setImageUrl(product.getImageUrl());
		productDto.setPrice(product.getPrice());
		productDto.setDescription(product.getDescription());
		productDto.setCategoryId(product.getCategory().getId());
		return productDto;
	}
	
	public ResponseEntity<List<ProductDto>> listProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductDto> productDtos = new ArrayList<ProductDto>();
		for(Product product : products) {
			productDtos.add(getProductDto(product));
		}
		return new ResponseEntity<List<ProductDto>>(productDtos, HttpStatus.OK);
	}
	
	public void updateProduct(int productId, ProductDto updatedProductDto, Category category) {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		Product product = optionalProduct.get();
		product.setName(updatedProductDto.getName());
		product.setImageUrl(updatedProductDto.getImageUrl());
		product.setPrice(updatedProductDto.getPrice());
		product.setDescription(updatedProductDto.getDescription());
		product.setCategory(category);
		productRepository.save(product);
	}
	
	public boolean isProductPresent(int productId) {
		return productRepository.findById(productId).isPresent();
	}

	public Product findProductById(Integer productId) {

		Optional<Product> optionalProduct = productRepository.findById(productId);

		if(optionalProduct.isEmpty()) {
			throw new ProductNotExistsException("product doesn't exist " + productId);
		}
		
		return optionalProduct.get();
				
	}

}
