package com.ecommerce.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class RegisterationDTO {
	 private String name;
	    private String email;
	    private LocalDate dob;
	    private String password;
	    private String role;
	    private Date createDate;
}
