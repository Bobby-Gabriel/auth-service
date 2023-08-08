package com.team4.project;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@CrossOrigin
public class LoginApi {
	
	@GetMapping
	public String getAll() {
		return "jwt-fake-token-asdfasdfasfa".toString();
	}
	
	public ResponseEntity<?> createTokenForCustomer(Customer customer) {
		
		String username = null;
		String password = null;
		if (username != null && password != null && password.length() > 2) {
		
		//if (username != null && username.length() > 0 && password != null && password.length() > 0 && checkPassword(username, password)) {
		    long fiveHoursInMillis = 1000 * 60 * 60 * 5;
		    Date expireDate = new Date(System.currentTimeMillis() + fiveHoursInMillis);
		    String tokenString = JWT.create()
		    	.withSubject("apiuser")
		        .withIssuer("me@me.com")
		        .withClaim("scopes", "*")
		        .withExpiresAt(expireDate)
		        .sign(Algorithm.HMAC256("secret"));
		    tokenString = "Bearer " + tokenString + "comeonin";
			ResponseEntity<?> response = ResponseEntity.ok(new Token(tokenString));
			return response;			
		}
		// bad request
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
	}
	
	private boolean checkPassword(String username, String password) {
		Customer customer = new Customer();
		if (customer.getName() == username && customer.getPassword() == password) {
			return false;
		} else {
			return false;
		}
		
	}
	
	private Customer getCustomerByNameFromCustomerAPI(String username) {
		return new Customer();
	}  	

}    
