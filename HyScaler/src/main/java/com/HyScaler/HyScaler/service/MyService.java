package com.HyScaler.HyScaler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.HyScaler.HyScaler.dto.Manager;
import com.HyScaler.HyScaler.repository.ManagerRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class MyService {
	@Autowired ManagerRepository managerRepository;
	@Autowired BCryptPasswordEncoder passwordEncrypt;

	public String postManager(Manager manager, HttpSession session) {
		if(managerRepository.existsByEmail(manager.getEmail())) {
			session.setAttribute("error", "Email already exist");
			return "redirect:/manager-login";

		}else {
			// Encrypt password 
	        String encryptedPassword = passwordEncrypt.encode(manager.getPassword());
	        manager.setPassword(encryptedPassword);
	        manager.setRole("MANAGER");
			managerRepository.save(manager);
			return "redirect:/login";
		}
	}

	public String postLogin(String email, String password, HttpSession session) {

	    if (!managerRepository.existsByEmail(email)) {
	        session.setAttribute("fail", "Manager does not exist");
	        return "redirect:/manager-register";
	    }

	    Manager manager = managerRepository.findByEmail(email);

	    //CORRECT BCrypt check
	    if (!passwordEncrypt.matches(password, manager.getPassword())) {
	        session.setAttribute("fail", "Invalid email or password");
	        return "redirect:/login";
	    }

	    // Login success
	    session.setAttribute("manager", manager);
	    session.setAttribute("success", "Login successful");

	    return "redirect:/manager-dashboard";
	}

	
	
}
