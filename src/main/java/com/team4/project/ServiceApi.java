package com.team4.project;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/")
public class ServiceApi {
	@GetMapping
	public String healthCheck() {
		return "<h3>Service is on</h3>";
	}
}