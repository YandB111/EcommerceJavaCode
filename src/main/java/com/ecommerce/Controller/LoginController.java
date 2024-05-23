package com.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.API.ApiController;
import com.ecommerce.Response.LoginResponse;
import com.ecommerce.entity.LoginDTO;
import com.ecommerce.service.UserService;
import com.ecommerce.serviceImpl.MyUserDetailsService;
import com.ecommerce.utility.JwtUtil;

import io.swagger.annotations.Api;

@RestController
@Api(value = "USER CONTROLLER")
public class LoginController extends ApiController {

	@Autowired
	private UserService userService;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	// Login
	/*
	 * @CrossOrigin
	 * 
	 * @PostMapping(value = USER_LOGIN , produces = "application/json") public
	 * ResponseEntity<LoginResponse<String>> loginUser(@RequestBody LoginDTO
	 * loginDTO) { try { ResponseEntity<String> loginResult =
	 * userService.loginUser(loginDTO);
	 * 
	 * boolean success = loginResult.getStatusCode() == HttpStatus.OK; String
	 * message = success ? "Login successful. Welcome to Y & B Ecommerce!" :
	 * loginResult.getBody();
	 * 
	 * return ResponseEntity.ok(new LoginResponse<>(success, message, null)); }
	 * catch (Exception e) { return ResponseEntity.status(HttpStatus.CONFLICT)
	 * .body(new LoginResponse<>(false, "Failed to process login request: " +
	 * e.getMessage(), null)); } }
	 */

	@CrossOrigin
	@PostMapping(value = USER_LOGIN, produces = "application/json")
	public ResponseEntity<LoginResponse<String>> loginUser(@RequestBody LoginDTO loginDTO) {
	    try {
	        ResponseEntity<String> loginResult = userService.loginUser(loginDTO);

	        if (loginResult.getStatusCode() == HttpStatus.OK) {
	            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
	            String jwtToken = jwtUtil.generateToken(userDetails);

	            HttpHeaders headers = new HttpHeaders();
	            String cacheControlValue = "no-store";
	            headers.add("Cache-Control", cacheControlValue);

	            // Print the Cache-Control value to the console
	            System.out.println("Cache-Control: " + cacheControlValue);

	            // Include the JWT token in the response body with the Cache-Control header
	            LoginResponse<String> response = new LoginResponse<>(true,
	                    "Login successful. Welcome to Y & B Ecommerce!", jwtToken);
	            return ResponseEntity.ok().headers(headers).body(response);
	        } else {
	            boolean success = false;
	            String message = loginResult.getBody();

	            return ResponseEntity.ok(new LoginResponse<>(success, message, null));
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                .body(new LoginResponse<>(false, "Failed to process login request: " + e.getMessage(), null));
	    }
	}
}