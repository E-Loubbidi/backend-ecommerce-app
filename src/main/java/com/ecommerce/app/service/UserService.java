package com.ecommerce.app.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.user.SignInDto;
import com.ecommerce.app.dto.user.SignInResponseDto;
import com.ecommerce.app.dto.user.UserDto;
import com.ecommerce.app.exceptions.AuthenticationFailException;
import com.ecommerce.app.exceptions.CustomException;
import com.ecommerce.app.model.AuthenticationToken;
import com.ecommerce.app.model.User;
import com.ecommerce.app.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationService authenticationService;

	@Transactional
	public ResponseEntity<ApiResponse> signup(UserDto userDto) {

		if(Objects.nonNull(userRepository.findByEmail(userDto.getEmail()))) {
			throw new CustomException("Email already used");
		}

		String encryptedPassword = userDto.getPassword();

		try {
			encryptedPassword = hashPassword(userDto.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new CustomException(e.getMessage());
		}

		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(encryptedPassword);
		userRepository.save(user);
		
		final AuthenticationToken authenticationToken = new AuthenticationToken(user);
 
		authenticationService.saveConfirmationToken(authenticationToken);

		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User created"), HttpStatus.CREATED);
	}

	public String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
		byte[] digest = messageDigest.digest();
		String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return hash;
	}

	public SignInResponseDto signin(SignInDto signInDto) {

		User user = userRepository.findByEmail(signInDto.getEmail());
		
		if(Objects.isNull(user)) {
			throw new AuthenticationFailException("Email doesn't exist");
		}
		
		try {
			if(!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
				throw new AuthenticationFailException("Wrong password");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		AuthenticationToken authenticationToken = authenticationService.getToken(user);
		
		if(Objects.isNull(authenticationToken)) {
			throw new AuthenticationFailException("Token is not present");
		}
		
		return new SignInResponseDto("success", authenticationToken.getToken());
	}

}
