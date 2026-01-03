package com.HyScaler.HyScaler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HyScaler.HyScaler.dto.Application;
import com.HyScaler.HyScaler.dto.Employee;
import com.HyScaler.HyScaler.dto.Manager;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	List<Application> findByEmployee(Employee employee);
	List<Application> findByManager(Manager manager);

}
