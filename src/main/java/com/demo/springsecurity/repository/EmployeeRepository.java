package com.demo.springsecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.springsecurity.entity.Employee;
import com.demo.springsecurity.entity.Role;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Employee findByEmail(String email);
	List<Role> findByRoles(String role);

}
