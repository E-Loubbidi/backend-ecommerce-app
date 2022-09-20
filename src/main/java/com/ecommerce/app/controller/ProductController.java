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
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.Category;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.service.CategoryService;
import com.ecommerce.app.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
		 if(!categoryService.isCategoryPresent(productDto.getCategoryId())) {
			 return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category doesn't exist"), HttpStatus.BAD_REQUEST);
		 }
		 Category category = categoryService.findCategoryById(productDto.getCategoryId());
		productService.createProduct(productDto, category);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product created"), HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<ProductDto>> listProducts() {
		return productService.listProducts();
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") int productId) {
		Product product = productService.findProductById(productId);
		ProductDto productDto = new ProductDto();
		productDto.setId(product.getId());
		productDto.setImageUrl(product.getImageUrl());
		productDto.setName(product.getName());
		productDto.setDescription(product.getDescription());
		productDto.setPrice(product.getPrice());
		productDto.setCategoryId(product.getCategory().getId());
		return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);
	}
	
	@PostMapping("/update/{productId}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") int productId, @RequestBody ProductDto updatedProductDto) {
		if(!productService.isProductPresent(productId)) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Product doesn't exist"), HttpStatus.NOT_FOUND);
		}
		if(!categoryService.isCategoryPresent(updatedProductDto.getCategoryId())) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category doesn't exist"), HttpStatus.NOT_FOUND);
		}
		 Category category = categoryService.findCategoryById(updatedProductDto.getCategoryId());
		productService.updateProduct(productId, updatedProductDto, category);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product updated"), HttpStatus.OK);
	}

}
