package com.ecommerce.Response;

public class APIResponse {
	private String message;

	public APIResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
