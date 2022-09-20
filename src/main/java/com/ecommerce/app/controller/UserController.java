package com.ecommerce.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.user.SignInDto;
import com.ecommerce.app.dto.user.SignInResponseDto;
import com.ecommerce.app.dto.user.UserDto;
import com.ecommerce.app.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> signup(@RequestBody UserDto userDto) {
		return userService.signup(userDto);
	}
	
	@PostMapping("/signin")
	public SignInResponseDto signin(@RequestBody SignInDto signInDto) {
		return userService.signin(signInDto);
	}


}
