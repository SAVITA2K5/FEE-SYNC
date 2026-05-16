package com.feesync.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Certificate Entity - Represents generated No-Due certificates
 */
@Entity
@Table(name = "certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String certificateId;

    @Column(nullable = false, length = 20)
    private String rollNumber;

    @Column(nullable = false)
    private LocalDateTime issueDate;

    @Column(nullable = false, length = 50)
    private String generatedBy;

    public Certificate(String certificateId, String rollNumber, String generatedBy) {
        this.certificateId = certificateId;
        this.rollNumber = rollNumber;
        this.generatedBy = generatedBy;
        this.issueDate = LocalDateTime.now();
    }
}
