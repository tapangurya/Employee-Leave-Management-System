package com.HyScaler.HyScaler.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private Long phone;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String panNumber;

    @Column(nullable = false, length = 12)
    private Long aadhaarNumber;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String password;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Application> applications;

    // 🔥 ADD THIS
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<LeaveBalance> leaveBalances;
}
