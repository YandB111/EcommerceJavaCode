package com.ecommerce.Response;

import lombok.Data;

@Data
public class LoginResponse<T> {
	
	 private final boolean success;
	    private final String message;
	    private final T data;

	    public LoginResponse(boolean success, String message, T data) {
	        this.success = success;
	        this.message = message;
	        this.data = data;
	    }
	

}
