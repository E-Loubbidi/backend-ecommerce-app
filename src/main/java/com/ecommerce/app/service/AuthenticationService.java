package com.ecommerce.app.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.app.exceptions.AuthenticationFailException;
import com.ecommerce.app.model.AuthenticationToken;
import com.ecommerce.app.model.User;
import com.ecommerce.app.repository.TokenRepository;

@Service
public class AuthenticationService {

	@Autowired
	TokenRepository tokenRepository;
	
	public void saveConfirmationToken(AuthenticationToken authenticationToken) {
		tokenRepository.save(authenticationToken);
	}

	public AuthenticationToken getToken(User user) {
		return tokenRepository.findByUser(user);
	}
	
	public User getUser(String token) {
		final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
		if(Objects.isNull(authenticationToken)) {
			return null;
		}
		return authenticationToken.getUser();
	}
	
	public void authentiate(String token) {
		if(Objects.isNull(token)) {
			throw new AuthenticationFailException("Token is not present");
		}
		if(Objects.isNull(getUser(token))) {
			throw new AuthenticationFailException("Token is not valid");
		}
	}

}
