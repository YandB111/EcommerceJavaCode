package com.ecommerce.API;


public abstract class ApiController {
	
	protected static final String API_PATH = "/api/yB/ecommerce";
	
	// save Api SignUp

	protected static final  String  SIGNUP_USER = API_PATH +  "/signup";
	protected static final  String	VERIFY_OTP = API_PATH + "/verify-otp";
	protected static final  String	USER_BY_ID = API_PATH + "/findbyUser/{id}";
	protected static final  String  USER_LOGIN = API_PATH + "/login";
	protected static final  String USER_BY_EMAIL = API_PATH +  "/userbyEmail";
	
	protected static final  String  POST_USE = API_PATH + "/postUse";
	protected static final  String  DELETE_USER = API_PATH + "/deleteId";
	protected static final  String  GET_ALL_USER = API_PATH + "/getallUse";
	protected static final  String  USE_BY_ID = API_PATH + "{productId}";
}