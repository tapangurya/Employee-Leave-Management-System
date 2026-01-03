package com.HyScaler.HyScaler.dto;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name ="manager")
public class Manager {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	@Column(nullable = false)
	private String fullname;
	@Column(nullable = false)
	private Long phone;
	@Column(nullable = false,unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String role;
	
	@OneToMany(mappedBy = "manager")
	private List<Employee> employees;
	
	@OneToMany(mappedBy = "manager")
    private List<Application> applications;

}
