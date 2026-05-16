package com.feesync.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Student Entity - Represents student fee information
 */
@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String rollNumber;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Double totalFee;

    @Column(nullable = false)
    private Double paidAmount;

    @Column(nullable = false)
    private Double dueAmount;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private FeeStatus status;

    /**
     * Fee Status Enum
     */
    public enum FeeStatus {
        PAID,
        UNPAID,
        PARTIAL
    }

    /**
     * Update fee status based on paid and due amounts
     */
    public void updateStatus() {
        if (dueAmount == 0) {
            this.status = FeeStatus.PAID;
        } else if (paidAmount == 0) {
            this.status = FeeStatus.UNPAID;
        } else {
            this.status = FeeStatus.PARTIAL;
        }
    }
}
