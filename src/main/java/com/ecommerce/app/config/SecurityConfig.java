package com.ecommerce.app.config;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.app.exceptions.CustomException;
import com.ecommerce.app.filters.JwtRequestFilter;
import com.ecommerce.app.service.MyUserDetailsService;
import com.ecommerce.app.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
		.and().csrf().disable()
		.authorizeRequests()
		.antMatchers(
				"/v3/api-docs/**", "/swagger-ui/**", 
				"/user/authenticate",
				"/user/signin",
				"/user/signup",
				"/category/list",
				"/product/list",
				"/product/{productId}"
				).permitAll()
		.anyRequest().authenticated()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				boolean isEqual = false;
				try {
					isEqual = userService.hashPassword(rawPassword.toString()).equals(encodedPassword);
				} catch (NoSuchAlgorithmException e) {
					throw new CustomException(e.getMessage());
				}
				return isEqual;
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				String encodedPassword = null;
				try {
					 encodedPassword = userService.hashPassword(rawPassword.toString());
				} catch (NoSuchAlgorithmException e) {
					throw new CustomException(e.getMessage());
				}
				return encodedPassword;
			}
		};
	}
	
}
