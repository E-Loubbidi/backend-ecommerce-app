package com.ecommerce.app.repository;

import org.springframework.stereotype.Repository;

import com.ecommerce.app.model.Cart;
import com.ecommerce.app.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
	
}
