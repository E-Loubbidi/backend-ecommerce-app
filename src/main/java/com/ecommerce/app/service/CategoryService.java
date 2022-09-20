package com.ecommerce.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.app.model.Category;
import com.ecommerce.app.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public void createCategory(Category category) {
		categoryRepository.save(category);
	}
	
	public List<Category> listCategories() {
		return categoryRepository.findAll();
	}
	
	public void updateCategory(int categoryId, Category updatedCategory) {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		Category category = optionalCategory.get();
		category.setCategoryName(updatedCategory.getCategoryName());
		category.setDescription(updatedCategory.getDescription());
		category.setImageUrl(updatedCategory.getImageUrl());
		categoryRepository.save(category);
	}

	public boolean isCategoryPresent(int categoryId) {
		return categoryRepository.findById(categoryId).isPresent();
	}
	
	public Category findCategoryById(int categoryId) {
		return categoryRepository.findById(categoryId).get();
	}
}
