package com.HyScaler.HyScaler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.HyScaler.HyScaler.dto.Application;
import com.HyScaler.HyScaler.dto.Employee;
import com.HyScaler.HyScaler.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeController {
	
	
	@Autowired EmployeeService service;
	
	
	@GetMapping("/employee-login")
	public String EmpLogin() {
		return "/employee-login";
	}
	@PostMapping("/employee-login")
	public String EmpLogin(@RequestParam String email,String password,HttpSession session) {
		
		return service.postEmpLogin(email,password,session);
	}
	@GetMapping("/employee-dashboard")
	public String getLogin(HttpSession session,ModelMap map) {
		return service.getEmployeeDashboard(session,map);
	}
	
	@GetMapping("/take-leave")
	public String takeLeave(HttpSession session,ModelMap map) {
		return service.takeLeave(session,map);
	}
	@PostMapping("/take-leave")
	public String postTakeLeave(@ModelAttribute Application application,HttpSession session,ModelMap map ) {
		return service.postTakeLeave(application,session,map);
	}
	@GetMapping("/employee-edit")
	public String editBasicDetails(HttpSession session,ModelMap map) {
		return service.editBasicDetails(session,map);
	}

	
	@PostMapping("/employee-edit")
	public String postEditBasicDetails(@ModelAttribute Employee employee, HttpSession session,ModelMap map) {
		return service.postEditBasicDetails(employee,session,map);
	}
	
	
	
  
	
	
	
}
