package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.model.AuthenticationToken;
import com.ecommerce.app.model.User;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
	
	AuthenticationToken findByUser(User user);
	AuthenticationToken findByToken(String token);
	
}
