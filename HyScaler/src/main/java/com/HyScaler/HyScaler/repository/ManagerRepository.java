package com.HyScaler.HyScaler.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HyScaler.HyScaler.dto.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
	boolean existsByPhone(Long phone);
	boolean existsByEmail(String email);
	Manager findByEmail(String email);
	Optional<Manager> findById(Integer id);
	
}
