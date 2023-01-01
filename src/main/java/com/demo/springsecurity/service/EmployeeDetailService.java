package com.demo.springsecurity.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.springsecurity.entity.Employee;
import com.demo.springsecurity.repository.EmployeeRepository;

@Service
public class EmployeeDetailService implements UserDetailsService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Employee emp = null;
		emp =  employeeRepository.findByEmail(username);
		if(emp == null)
		{
			throw new UsernameNotFoundException("Invalid username or password");
		}
		
		return new User(emp.getEmail(), emp.getPassword(), getAuthority(emp));
	}

	 private List<SimpleGrantedAuthority> getAuthority(Employee emp) {
	        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	        emp.getRoles().forEach(role -> {
	            authorities.add(new SimpleGrantedAuthority(role.getName()));
	        });
	        return authorities;
	    }
}
