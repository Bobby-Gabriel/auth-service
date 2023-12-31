package com.team4.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@RestController
@RequestMapping("/account/token")
public class LoginApi {
	
	JWTUtil jwtUtil = new JWTHelper();
		
	@PostMapping
	public ResponseEntity<?> createTokenForCustomer(@RequestBody Customer customer) {
		
		String username = customer.getName();
		String password = customer.getPassword();
		
		Customer c = new Customer();
		c = getCustomerByNameFromCustomerAPI(username);

		if (c != null && c.getPassword().equals(password)) {
	
		    Token token = jwtUtil.createToken();
		    token.setToken(token.getToken());
			return ResponseEntity.ok(token);
		}
		// bad request
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
	}
	
	
	private Customer getCustomerByNameFromCustomerAPI(String username) {
		
		RestTemplate rt = new RestTemplate();

		return rt.getForObject("http://18.188.12.86:9001/gateway/customers/byname/" + username, Customer.class);
	}  	

}    
