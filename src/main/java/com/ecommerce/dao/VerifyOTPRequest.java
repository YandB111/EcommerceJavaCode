package com.ecommerce.dao;

import lombok.Data;

@Data
public class VerifyOTPRequest {
	 private String email;
	    private String enteredOTP;
}
