package com.ecommerce.Controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.ecommerce.API.ApiController;
import com.ecommerce.Response.APIResponse;
import com.ecommerce.dao.RegisterationDTO;
import com.ecommerce.dao.VerifyOTPRequest;
import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin
@Api(value = "USER CONTROLLER", basePath = "/api/yB/ecommerce")
public class UserController extends ApiController {

	@Autowired
	private UserService userService;

	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	        .allowedOrigins("*")
	        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	        .allowedHeaders("*")
	        .allowCredentials(true)
	        .maxAge(3600);
	}
	
	@CrossOrigin
	@PostMapping(value = SIGNUP_USER, produces = "application/json")
	public ResponseEntity<APIResponse> registerUser(@RequestBody RegisterationDTO registrationDTO) {
		registrationDTO.setCreateDate(new Date());
		try {
			userService.registerUser(registrationDTO);
			return new ResponseEntity<>(new APIResponse("User registered successfully. Check your email for OTP."),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new APIResponse("Failed to register user: " + e.getMessage()),
					HttpStatus.CONFLICT);
		}
	}

	@CrossOrigin
	@PostMapping(value = VERIFY_OTP, produces = "application/json")
	public ResponseEntity<APIResponse> verifyOTP(@RequestBody VerifyOTPRequest verifyOtpRequest) {
		try {
			String email = verifyOtpRequest.getEmail();
			String enteredOTP = verifyOtpRequest.getEnteredOTP();

			if (userService.verifyOTP(email, enteredOTP)) {
				return new ResponseEntity<>(new APIResponse("OTP verified successfully. User details saved."),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new APIResponse("Invalid OTP. User details not saved."),
						HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new APIResponse("Failed to verify OTP: " + e.getMessage()),
					HttpStatus.CONFLICT);
		}
	}

	

	@CrossOrigin
	@GetMapping(value = USER_BY_ID, produces = "application/json" )
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		ResponseEntity<?> user = userService.getUserById(id);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User not found", HttpStatus.CONFLICT);
		}
	}

	// API to get user by email
	@CrossOrigin
	@GetMapping(value = USER_BY_EMAIL,produces = "application/json" )
	public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
		User user = userService.getUserByEmail(email);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User not found", HttpStatus.CONFLICT);
		}
	}

}