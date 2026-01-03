package com.HyScaler.HyScaler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HyScaler.HyScaler.dto.Employee;
import com.HyScaler.HyScaler.dto.Manager;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	boolean existsByPhone(Long phone);
	boolean existsByEmail(String email);
	Employee findByEmail(String email);
	List<Employee> findByManager(Manager manager);
	
}
