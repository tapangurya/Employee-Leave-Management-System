package com.HyScaler.HyScaler.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.HyScaler.HyScaler.dto.Application;
import com.HyScaler.HyScaler.dto.Employee;
import com.HyScaler.HyScaler.dto.Manager;
import com.HyScaler.HyScaler.repository.ApplicationRepository;
import com.HyScaler.HyScaler.repository.EmployeeRepository;
import com.HyScaler.HyScaler.repository.ManagerRepository;

import jakarta.servlet.http.HttpSession;


@Service
public class EmployeeService {
	@Autowired EmployeeRepository empRepository;
	@Autowired ManagerRepository managerRepository;
	@Autowired ApplicationRepository appliRepository;
	
	public String getEmployeeDashboard(HttpSession session, ModelMap map) {
		Employee employee = (Employee) session.getAttribute("employee");
	    if (employee == null) {
	        session.setAttribute("fail", "Login first");
	        return "redirect:/employee-login";
	    }
	    
	    Manager manager = managerRepository
	            .findById(employee.getManager().getId())
	            .orElseThrow(() -> new RuntimeException("Manager not found"));
	    List<Application> applications =
	    		appliRepository.findByEmployee(employee);
	    
	    
	    map.put("applications", applications);
	    map.put("manager", manager);
	    map.put("employee", employee);
	    return "employee-dashboard";
	}

	public String postEmpLogin(String email, String password, HttpSession session) {
		
		if (!empRepository.existsByEmail(email)) {
	        session.setAttribute("fail", "Contact with your manager");
	        return "redirect:/employee-login";
	    }
	    Employee employee = empRepository.findByEmail(email);

	    if (!employee.getPassword().equals(password)) {
	        session.setAttribute("fail", "Invalid email or password");
	        return "redirect:/employee-login";
	    }
	    // Login success
	    System.out.println();
	    session.setAttribute("employee", employee);
	    session.setAttribute("success", "Login successful");

	    return "redirect:/employee-dashboard";
	}

	public String takeLeave(HttpSession session, ModelMap map) {
		Employee employee = (Employee) session.getAttribute("employee");
	    if (employee == null) {
	        session.setAttribute("fail", "Login first");
	        return "redirect:/employee-login";
	    }
	    Manager manager = managerRepository
	            .findById(employee.getManager().getId())
	            .orElseThrow(() -> new RuntimeException("Manager not found"));

	    map.put("manager", manager);
	    map.put("employee", employee);
	    return "take-leave";
	}

	public String postTakeLeave(
	        Application application,
	        HttpSession session,ModelMap map) {

	    Employee employee = (Employee) session.getAttribute("employee");

	    if (employee == null) {
	        session.setAttribute("fail", "Please Login first");
	        return "redirect:/employee-login";
	    }

	    // Assign employee
	    application.setEmployee(employee);

	    // Assign manager
	    Manager manager = managerRepository
	            .findById(employee.getManager().getId())
	            .orElseThrow(() -> new RuntimeException("Manager not found"));
	    application.setManager(manager);

	    // Set system values
	    
	    application.setStatus("PENDING");

	    // Save application
	    appliRepository.save(application);

	    session.setAttribute("pass", "Leave applied successfully");

	    return "redirect:/employee-dashboard";
	}	

}
