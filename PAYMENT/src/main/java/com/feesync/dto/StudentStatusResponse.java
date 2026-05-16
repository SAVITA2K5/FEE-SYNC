package com.feesync.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Student Status Response DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentStatusResponse {

    private String rollNumber;
    private String name;
    private Double totalFee;
    private Double paidAmount;
    private Double dueAmount;
    private String status;
}
