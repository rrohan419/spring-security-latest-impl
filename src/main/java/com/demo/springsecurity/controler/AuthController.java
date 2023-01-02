package com.demo.springsecurity.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springsecurity.model.LoginModel;
import com.demo.springsecurity.model.SignupModel;
import com.demo.springsecurity.model.SuccessResponse;
import com.demo.springsecurity.service.EmployeeServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	EmployeeServiceImpl employeeServiceImpl;
	
	@PostMapping("/register")
	public ResponseEntity<SuccessResponse> registerUser(@Valid @RequestBody SignupModel model)
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(employeeServiceImpl.registerEmployee(model));
		response.setMessage("User successfully registered");
		response.setSuccessCode(HttpStatus.OK.value());
		
		return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
	}
	@PostMapping("/login")
	public ResponseEntity<SuccessResponse> login(@Valid @RequestBody LoginModel model)
	{
		SuccessResponse response = new SuccessResponse();
		response.setData(employeeServiceImpl.login(model));
		response.setMessage("successfull login");
		response.setSuccessCode(HttpStatus.OK.value());
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
