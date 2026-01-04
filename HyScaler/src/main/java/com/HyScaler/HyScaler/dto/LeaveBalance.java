package com.HyScaler.HyScaler.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(
    name = "leave_balance",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"employee_id", "leave_type"}
    )
)
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // VACATION / SICK / CASUAL
    @Column(name = "leave_type", nullable = false, length = 20)
    private String leaveType;

    @Column(nullable = false)
    private int totalAllocated;

    @Column(nullable = false)
    private int used;

    @Column(nullable = false)
    private int remaining;

    public LeaveBalance(Employee employee, String leaveType, int totalAllocated) {
        this.employee = employee;
        this.leaveType = leaveType;
        this.totalAllocated = totalAllocated;
        this.used = 0;
        this.remaining = totalAllocated;
    }

    public void setUsed(int used) {
        this.used = used;
        this.remaining = this.totalAllocated - used;
    }

    public void setTotalAllocated(int totalAllocated) {
        this.totalAllocated = totalAllocated;
        this.remaining = totalAllocated - this.used;
    }
}
