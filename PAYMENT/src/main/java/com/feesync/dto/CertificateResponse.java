package com.feesync.dto;

import com.feesync.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Certificate Response DTO - Contains all data for the certificate
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponse {
    private String certificateId;
    private String rollNumber;
    private String studentName;
    private Double totalFee;
    private Double totalPaid;
    private Double dueAmount;
    private LocalDateTime issueDate;
    private String collegeName;
    private List<Transaction> transactions;
    private String statement;
}
