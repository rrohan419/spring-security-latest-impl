package com.demo.springsecurity.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginModel {
	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String password;

}
