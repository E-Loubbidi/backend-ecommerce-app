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
import com.ecommerce.app.model.Category;
import com.ecommerce.app.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
		categoryService.createCategory(category);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category created"), HttpStatus.CREATED) ;
		
	}
	
	@GetMapping("/list")
	public List<Category> listCategory() {
		return categoryService.listCategories();
	}
	
	@PostMapping("/update/{categoryId}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category) {
		if(!categoryService.isCategoryPresent(categoryId)) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category doesn't exist"), HttpStatus.NOT_FOUND);
		}
		categoryService.updateCategory(categoryId, category);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category updated"), HttpStatus.OK);
	}

}
