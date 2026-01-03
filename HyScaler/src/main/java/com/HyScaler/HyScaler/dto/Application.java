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

    private String leaveType;      // LEAVE / CORRECTION
    private String status;    // Initial: PENDING / APPROVED / REJECTED

    private LocalDate from_date;
    private LocalDate to_date;

    @Column(length = 1000)
    private String reason;

    /*  MANY APPLICATIONS → ONE EMPLOYEE  */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    /* ===== MANY APPLICATIONS → ONE MANAGER ===== */
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;
}
