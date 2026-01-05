package com.HyScaler.HyScaler.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.HyScaler.HyScaler.repository.ManagerRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository empRepository;
	@Autowired
	ManagerRepository managerRepository;
	@Autowired
	ApplicationRepository appliRepository;
	@Autowired
	private LeaveBalanceRepository leaveBalanceRepository;

	public String getEmployeeDashboard(HttpSession session, ModelMap map) {

	    Employee sessionEmployee = (Employee) session.getAttribute("employee");
	    if (sessionEmployee == null) {
	        session.setAttribute("fail", "Login first");
	        return "redirect:/employee-login";
	    }

	    // 🔥 ALWAYS FETCH FRESH EMPLOYEE
	    Employee employee = empRepository.findById(sessionEmployee.getId())
	            .orElseThrow(() -> new RuntimeException("Employee not found"));

	    List<Application> applications =
	            appliRepository.findByEmployee(employee);

	    // 🔥 EXPLICITLY FETCH LEAVE BALANCE
	    List<LeaveBalance> balances =
	            leaveBalanceRepository.findByEmployee(employee);
	    map.put("employee", employee);
	    map.put("applications", applications);
	    map.put("balances", balances);
	    session.setAttribute("applications", applications);
	    session.setAttribute("balances", balances);
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
		Manager manager = managerRepository.findById(employee.getManager().getId())
				.orElseThrow(() -> new RuntimeException("Manager not found"));

		map.put("manager", manager);
		map.put("employee", employee);
		return "take-leave";
	}

	public String postTakeLeave(Application application, HttpSession session, ModelMap map) {

		Employee employee = (Employee) session.getAttribute("employee");

		if (employee == null) {
			session.setAttribute("fail", "Please Login first");
			return "redirect:/employee-login";
		}

		// Assign employee
		application.setEmployee(employee);

		// Assign manager
		Manager manager = managerRepository.findById(employee.getManager().getId())
				.orElseThrow(() -> new RuntimeException("Manager not found"));
		application.setManager(manager);
		//Set total days
		long days = ChronoUnit.DAYS.between(
		        application.getFromDate(),
		        application.getToDate()
		) + 1;

		application.setTotalDays((int) days);
		
		
		// Set system values

		application.setStatus("PENDING");
		LocalDate date = LocalDate.now();
		application.setApplyedDate(date);
		// Save application
		appliRepository.save(application);
		

		session.setAttribute("pass", "Leave applied successfully");

		return "redirect:/employee-dashboard";
	}

	public String editBasicDetails(HttpSession session, ModelMap map) {
		Employee employee = (Employee) session.getAttribute("employee");

		if (employee == null) {

			session.setAttribute("fail", "Login first");
			return "redirect:/employee-login";
		}
		Employee existingEmp = empRepository.findById(employee.getId()).orElse(null);
		map.put("employee", existingEmp);
		return "employee-edit";
	}

	public String postEditBasicDetails(Employee employee, HttpSession session, ModelMap map) {

	    Employee sessionEmployee = (Employee) session.getAttribute("employee");
	    if (sessionEmployee == null) {
	        session.setAttribute("fail", "Login first");
	        return "redirect:/employee-login";
	    }

	    Employee existingEmp = empRepository
	            .findById(sessionEmployee.getId())
	            .orElse(null);

	    if (existingEmp == null) {
	        session.setAttribute("fail", "Employee not found");
	        return "redirect:/employee-dashboard";
	    }

	    // update only allowed fields
	    existingEmp.setFullname(employee.getFullname());
	    existingEmp.setPhone(employee.getPhone());
	    existingEmp.setEmail(employee.getEmail());
	    existingEmp.setDob(employee.getDob());

	    empRepository.save(existingEmp);

	    session.setAttribute("pass", "Update details successfully");
	    return "redirect:/employee-dashboard";
	}

	public void initLeaveBalance(Employee employee) {

	    createBalance(employee, "VACATION", 24);
	    createBalance(employee, "SICK", 12);
	    createBalance(employee, "CASUAL", 10);
	}

	private void createBalance(Employee emp, String type, int quota) {
	    LeaveBalance lb = new LeaveBalance();
	    lb.setEmployee(emp);
	    lb.setLeaveType(type);
	    lb.setTotalAllocated(quota);
	    lb.setUsed(0);
	    lb.setRemaining(quota);
	    leaveBalanceRepository.save(lb);
	}
	public String employeeLeaveCalendar(HttpSession session, ModelMap map) {
	    
	    // 1. Get the logged-in Employee
	    Employee employee = (Employee) session.getAttribute("employee");
	    if (employee == null) {
	        return "redirect:/login";
	    }

	    // 2. Get the Employee's Manager (to find teammates)
	    // Assuming your Employee entity has a 'getManager()' method
	    Manager teamManager = employee.getManager();
	    
	    List<Application> teamLeaves = new ArrayList<>();
	    
	    if (teamManager != null) {
	        // 3. Fetch all APPROVED leaves for this Manager (Team Leaves)
	        teamLeaves = appliRepository.findByManagerAndStatus(teamManager, "APPROVED");
	    }

	    // 4. Add to Model
	    map.put("leaves", teamLeaves);

	    return "employee-leave-calendar";
	}
 
}
