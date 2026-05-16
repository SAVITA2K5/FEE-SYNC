package com.feesync.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Transaction Entity - Represents payment transactions
 */
@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String transactionId;

    @Column(nullable = false, length = 20)
    private String rollNumber;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private Boolean verified;

    /**
     * Constructor for creating new transaction
     */
    public Transaction(String transactionId, String rollNumber, Double amount) {
        this.transactionId = transactionId;
        this.rollNumber = rollNumber;
        this.amount = amount;
        this.paymentDate = LocalDateTime.now();
        this.verified = false;
    }
}
