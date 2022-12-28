package com.demo.springsecurity.service;

import com.demo.springsecurity.entity.Employee;
import com.demo.springsecurity.model.LoginModel;
import com.demo.springsecurity.model.SignupModel;

public interface EmployeeService {
	
	Employee registerEmployee(SignupModel signup);
	Object login(LoginModel login);

}
