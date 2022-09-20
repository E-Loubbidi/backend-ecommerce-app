package com.ecommerce.app.repository;

import org.springframework.stereotype.Repository;

import com.ecommerce.app.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository 
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
}
