package com.team4.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
@RequestMapping("/account/register")
public class RegisterApi {
	
	JWTUtil jwtUtil = new JWTHelper();

	@PostMapping
	public ResponseEntity<?> registerCustomer(@RequestBody Customer newCustomer, UriComponentsBuilder uri) throws JsonProcessingException {
		if (newCustomer.getEmail() == null) {
			return ResponseEntity.badRequest().build();
		}	
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(newCustomer);
		Token token = postNewCustomerToCustomerAPI(json);

		return ResponseEntity.ok(token);
	}

	private Token postNewCustomerToCustomerAPI(String json_string) {
		try {

			URL url = new URL("http://18.188.12.86:9001/gateway/customers");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
	  		
			Token token = jwtUtil.createToken();
			token.setToken("Bearer " + token.getToken());

			OutputStream os = conn.getOutputStream();
			os.write(json_string.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
			
			return token;

		} catch (IOException e) {

			e.printStackTrace();
			return null;

		}

	}
}