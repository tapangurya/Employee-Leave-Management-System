package com.HyScaler.HyScaler.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.HyScaler.HyScaler.dto.Employee;
import com.HyScaler.HyScaler.service.ManagerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ManagerController {
	@Autowired ManagerService service;
	@GetMapping("/manager-dashboard")
	public String getManagerDashBoard(HttpSession session,ModelMap map) {
		return service.getManagerDashboard(session,map);
	}
	@GetMapping("/employee/create")
	public String createEmp(HttpSession session,ModelMap map) {
		return service.createEmployee(session,map);
	}
	@PostMapping("/employee/save")
	public String saveEmp(@ModelAttribute Employee employee,HttpSession session,ModelMap map) {
		return service.saveEmployee(employee,session,map);
	}

	@GetMapping("/edit-employee/{id}")
	public String editEmployee(@PathVariable int id, HttpSession session, ModelMap map) {
		return service.editEmployee(id, session, map);
	}
	
	@PostMapping("/edit-employee")
	public String editEmployee(@ModelAttribute Employee employee,HttpSession session)throws IOException {
		return service.editEmployee(employee, session);
	}
	
	@GetMapping("/delete-employee/{id}")
	public String deleteEmployee(@PathVariable int id, HttpSession session) {
		return service.deleteEmployee(id, session);
	}
	
	@GetMapping("/application-reject/{id}")
	public String  applicationReject(@PathVariable int id,HttpSession session,ModelMap map) {
		return service.applicationReject(id,session,map);
	}
	
	@GetMapping("/application-approve/{id}")
	public String showApprovePage(
	        @PathVariable int id,
	        ModelMap map,
	        HttpSession session) {
		return service.showApprovePage(id,map,session);
	}
	
	@PostMapping("/application-approve/{id}")
	public String  applicationApproved(@PathVariable int id,HttpSession session,ModelMap map,@RequestParam String comment) {
		System.out.println("ID in controller............... = " + id);
	    System.out.println("COMMENT in controller..................... = " + comment);
		return service.applicationApproved(id,session,map,comment);
	}
	
	
	//////////////////////////leave calendar/////////////////////////////////////////////// 
	@GetMapping("/manager/leave-calendar")
	public String managerLeaveCalendar(HttpSession session, ModelMap map) {
	    return service.managerLeaveCalendar(session, map);
	}
	
	
	
	
}
