package com.demo.springsecurity.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@GetMapping
	public String user() {
		return "test1";
	}
	@GetMapping("admin")
	public String admin() {
		return "test admin";
	}

}
