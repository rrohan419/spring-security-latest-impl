package com.demo.springsecurity.controler;

import org.springframework.security.access.prepost.PreAuthorize;
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
	@GetMapping("/admin")
//	@PreAuthorize("hasRole('user_read')")
	public String admin() {
		return "test admin";
	}
	@GetMapping("/super")
	@PreAuthorize("hasRole('user_write')")
	public String superAdmin() {
		return "super admin login success";
		
	}
	
	@GetMapping("/test")
	@PreAuthorize("user_abc")
	public String test() {
		return "ababbababa";
		}

}
