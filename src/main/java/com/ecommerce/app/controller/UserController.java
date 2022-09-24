package com.ecommerce.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.user.SignInDto;
import com.ecommerce.app.dto.user.SignInResponseDto;
import com.ecommerce.app.dto.user.UserDto;
import com.ecommerce.app.service.UserService;
import com.ecommerce.app.utils.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> signup(@RequestBody UserDto userDto) {
		return userService.signup(userDto);
	}
	
	@PostMapping("/signin")
	public SignInResponseDto signin(@RequestBody SignInDto signInDto) {
		return userService.signin(signInDto);
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<SignInResponseDto> createAuthenticationToken(@RequestBody SignInDto signInDto) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword())
					);
		} catch(BadCredentialsException e) {
			throw new Exception("Incorect username or password", e);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(signInDto.getEmail());
		
		final String jwt = jwtUtil.generateToken(userDetails);

		return new ResponseEntity<SignInResponseDto>(new SignInResponseDto("success", jwt), HttpStatus.OK);
	}


}
