package com.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
	        .antMatchers("/").permitAll() // Allow access to /login without authentication
	        .antMatchers("/signup", "/api/yB/ecommerce/signup", "/api/yB/ecommerce/verify-otp", "/api/yB/ecommerce/userbyEmail","/api/yB/ecommerce/login").permitAll() // Allow access to specific endpoints without authentication
	        .antMatchers("/api/yB/ecommerce/**").authenticated() // Secure other API endpoints and require authentication

	        // Configure access based on roles
	        .antMatchers("/api/admin/**").hasRole("ADMIN") // Only users with ADMIN role can access /api/admin/**
	        .antMatchers("/api/user/**").hasRole("USER") // Only users with USER role can access /api/user/**
	        .and().httpBasic(); // Use Basic Authentication

	    // Disable CSRF for simplicity (not recommended in production)
	    http.csrf().disable();

	    // Enable Swagger UI for all users
	    http.authorizeRequests().antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs").permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
