package com.HyScaler.HyScaler.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.HyScaler.HyScaler.dto.Manager;
import com.HyScaler.HyScaler.service.EmployeeService;
import com.HyScaler.HyScaler.service.MyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
	@Autowired MyService service;
	@Autowired EmployeeService empService;
	
	@GetMapping("/")
    public String root(ModelMap map) {
        return "index";
    }
	
	// Manager login
	@GetMapping("/login")
	public String Login() {
		return "/login";
	}
	
	@PostMapping("/login")
	public String Login(@RequestParam String email,String password,HttpSession session) {
		
		return service.postLogin(email,password,session);
	}
	
	
	// Register 
	@GetMapping("/manager-register")
	public String login(ModelMap map) {
		return "/manager-register";
	}
	
	@PostMapping("/manager-signup")
	public String postManagerSignUp(@ModelAttribute Manager manager,HttpSession session) {
		return service.postManager(manager,session);
	}
	
	@PostMapping("/logout")
	public String postLogout(HttpSession session) {

	    session.invalidate(); 
	    return "redirect:/login";
	}
}
