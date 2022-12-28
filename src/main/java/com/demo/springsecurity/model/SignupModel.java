package com.demo.springsecurity.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupModel {

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;

	private String dob;
	
	@Email
	@NotBlank
	private String email;
	
	@NotNull
	private String password;
}
