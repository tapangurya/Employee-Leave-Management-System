package com.HyScaler.HyScaler.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HyScaler.HyScaler.dto.Employee;
import com.HyScaler.HyScaler.dto.LeaveBalance;



public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer> {
	Optional<LeaveBalance> findByEmployeeAndLeaveType(Employee employee, String leaveType);
	List<LeaveBalance> findByEmployee(Employee employee);
}
