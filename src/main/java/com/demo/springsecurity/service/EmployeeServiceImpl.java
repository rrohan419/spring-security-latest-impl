package com.demo.springsecurity.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.springsecurity.entity.Employee;
import com.demo.springsecurity.entity.Role;
import com.demo.springsecurity.exception.CustomException;
import com.demo.springsecurity.exception.ErrorCode;
import com.demo.springsecurity.model.LoginModel;
import com.demo.springsecurity.model.SignupModel;
import com.demo.springsecurity.repository.EmployeeRepository;
import com.demo.springsecurity.repository.RoleRepository;
import com.demo.springsecurity.security.JwtUtil;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtil jwtUtil;
	
	
	
	@Override
	public Employee registerEmployee(SignupModel signup) {
		
//		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		if(employeeRepository.findByEmail(signup.getEmail()) != null)
		{
			throw new CustomException("already exist "+signup.getEmail(), ErrorCode.RESOURCE_ALREADY_EXISTS);
		}
		else {
			List<Role> roles = new ArrayList<>();
			
			Employee employee = new Employee();
			
			roles.add(roleRepository.findByName("user_read"));
			roles.add(roleRepository.findByName("user_write"));
			employee = mapper.map(signup, Employee.class);
			
			employee.setPassword(passwordEncoder.encode(signup.getPassword()));
			
			employee.setRoles(roles);
			
			employeeRepository.save(employee);
			
			return employee;
		}
	}

	@Override
	public Object login(LoginModel login) {
		Map<String, Object> data = new HashMap<>();
		
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword() ));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			String jwtToken = jwtUtil.generateToken(authentication);
			
			Employee employeeService = (Employee) authentication.getPrincipal();
			
			List<String> roles = employeeService.getAuthorities().stream().map(GrantedAuthority:: getAuthority).collect(Collectors.toList());
			
			data.put("token", jwtToken);
			data.put("usernameus", employeeService.getEmail());
			data.put("authority", employeeService.getAuthorities());
			data.put("roles", roles);
		}
		catch(Exception ex)
		{
			throw new CustomException(ex.getMessage(), ErrorCode.AUTHENTICATION_FAILED);
		}
		
		return data;
	}

}
