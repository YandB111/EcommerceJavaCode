package com.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.ecommerce.dao.RegisterationDTO;
import com.ecommerce.entity.LoginDTO;
import com.ecommerce.entity.User;

public interface UserService {

	void registerUser(RegisterationDTO registrationDTO);

	boolean verifyOTP(String email, String enteredOTP);

	ResponseEntity<String> loginUser(LoginDTO loginDTO);

	

	ResponseEntity<?> getUserById(Long id);

	User getUserByEmail(String email);

}
