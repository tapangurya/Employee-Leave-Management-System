package com.HyScaler.HyScaler.dto;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Example: VACATION / SICK / CASUAL
    @Column(nullable = false, length = 20)
    private String leaveType;

    // PENDING / APPROVED / REJECTED
    @Column(nullable = false, length = 20)
    private String status;
    
    private String comment;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;
    
    @Column(nullable = false)
    private LocalDate applyedDate;
    
    @Column(length = 1000)
    private String reason;

    @Column(nullable = false)
    private int totalDays;

    /* MANY APPLICATIONS → ONE EMPLOYEE */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    /* MANY APPLICATIONS → ONE MANAGER */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;
}
