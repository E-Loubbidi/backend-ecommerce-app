package com.ecommerce.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.app.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.ecommerce.app.model.User user =  userRepository.findByEmail(email);
		return buildUserForAuthentication(user, new ArrayList<>());
	}
	
	private User buildUserForAuthentication(com.ecommerce.app.model.User user, List<GrantedAuthority> authorities) {
		return new User(user.getEmail(), user.getPassword(), authorities);
	}

}
