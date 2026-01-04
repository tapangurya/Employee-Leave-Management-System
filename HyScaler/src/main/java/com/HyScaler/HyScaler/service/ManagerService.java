package com.HyScaler.HyScaler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.HyScaler.HyScaler.dto.Application;
import com.HyScaler.HyScaler.dto.Employee;
import com.HyScaler.HyScaler.dto.LeaveBalance;
import com.HyScaler.HyScaler.dto.Manager;
import com.HyScaler.HyScaler.repository.ApplicationRepository;
import com.HyScaler.HyScaler.repository.EmployeeRepository;
import com.HyScaler.HyScaler.repository.LeaveBalanceRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ManagerService {

	@Autowired
	EmployeeRepository empRepository;
	@Autowired
	ApplicationRepository appRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	LeaveBalanceRepository leaveBalanceRepository;

	public String getManagerDashboard(HttpSession session, ModelMap map) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			session.setAttribute("fail", "Login first");
			return "redirect:/login";
		}
		List<Employee> employees = empRepository.findByManager(manager);
		List<Application> applications = appRepository.findByManager(manager);
		
		session.setAttribute("manager", manager);
		map.put("applications", applications);
		map.put("manager", manager);
		map.put("employees", employees);
		return "manager-dashboard";
	}

	public String createEmployee(HttpSession session, ModelMap map) {

		Manager manager = (Manager) session.getAttribute("manager");

		if (manager != null && session.getAttribute("isLoggedIn").equals(true)) {
			map.put("employee", new Employee());
			return "create-employee";
		}

		map.put("fail", "Login first");
		return "redirect:/login";
	}

	public String saveEmployee(Employee employee, HttpSession session, ModelMap map) {

		Manager manager = (Manager) session.getAttribute("manager");

		if (manager != null) {

			if (!empRepository.existsByEmail(employee.getEmail())
					&& !empRepository.existsByPhone(employee.getPhone())) {

				employee.setManager(manager);

				// 1️ SAVE EMPLOYEE FIRST
				Employee savedEmployee = empRepository.save(employee);

				// 2️ NOW INITIALIZE LEAVE BALANCE
				employeeService.initLeaveBalance(savedEmployee);

				session.setAttribute("pass", "Employee created successfully");
				return "redirect:/manager-dashboard";
			} else {
				map.put("fail", "Employee already exist");
				map.put("employee", employee);
				return "create-employee";
			}
		}

		session.setAttribute("fail", "Login first");
		return "redirect:/login";
	}

	public String editEmployee(int id, HttpSession session, ModelMap map) {
		if (session.getAttribute("manager") != null) {
			Employee employee = empRepository.findById(id).orElse(null);
			map.put("employee", employee);
			return "edit-employee";

		} else {
			session.setAttribute("fail", "please login first");
			return "redirect:/login";
		}

	}

	public String editEmployee(Employee employee, HttpSession session) {
		if (session.getAttribute("manager") != null) {
			Employee existingEmp = empRepository.findById(employee.getId()).orElse(null);
			if (existingEmp == null) {
				session.setAttribute("fail", "Employe not found!");
				return "redirect:/manager-dashboard";
			}

			Manager manager = (Manager) session.getAttribute("manager");
			employee.setManager(manager);
			empRepository.save(employee);
			session.setAttribute("pass", "Employee updated sucessfully");
			return "redirect:/manager-dashboard";
		} else {
			session.setAttribute("fail", "Please login first!");
			return "redirect:/login";
		}
	}

	public String deleteEmployee(int id, HttpSession session) {
		if (session.getAttribute("manager") != null) {
			empRepository.deleteById(id);
			session.setAttribute("pass", "Employee Deleted Successfully");
			return "redirect:/manager-dashboard";
		} else {
			session.setAttribute("fail", "Please login first");
			return "redirect:/login";
		}
	}

	public String applicationReject(int id, HttpSession session, ModelMap map) {
		if (session.getAttribute("manager") != null) {
			Application application = appRepository.findById(id).orElse(null);
			if (application == null) {
				session.setAttribute("fail", "Application Not found");
				return "redirect:/manager-dashboard";
			}
			application.setStatus("REJECTED");
			appRepository.save(application);
			return "redirect:/manager-dashboard";
		} else {
			session.setAttribute("fail", "Please login first");
			return "redirect:/login";
		}
	}

	public String applicationApproved(int id, HttpSession session, ModelMap map) {

	    if (session.getAttribute("manager") == null) {
	        session.setAttribute("fail", "Please login first");
	        return "redirect:/login";
	    }

	    Application application = appRepository.findById(id).orElse(null);
	    if (application == null) {
	        session.setAttribute("fail", "Application Not found");
	        return "redirect:/manager-dashboard";
	    }

	    LeaveBalance balance = leaveBalanceRepository
	            .findByEmployeeAndLeaveType(
	                application.getEmployee(),
	                application.getLeaveType()
	            ).orElse(null);

	    if (balance == null) {
	        session.setAttribute("fail", "Leave balance not found");
	        return "redirect:/manager-dashboard";
	    }

	    if (balance.getRemaining() < application.getTotalDays()) {
	        session.setAttribute("fail", "Insufficient leave balance");
	        return "redirect:/manager-dashboard";
	    }

	    // ✅ UPDATE ONLY USED (remaining auto-calculated)
	    balance.setUsed(balance.getUsed() + application.getTotalDays());

	    // ✅ UPDATE STATUS
	    application.setStatus("APPROVED");

	    leaveBalanceRepository.save(balance);
	    appRepository.save(application);

	    session.setAttribute("pass", "Leave approved");
	    return "redirect:/manager-dashboard";
	}


}
