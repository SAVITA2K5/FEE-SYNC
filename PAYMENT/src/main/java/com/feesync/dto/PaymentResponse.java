package com.feesync.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Payment Response DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String transactionId;
    private String rollNumber;
    private Double amount;
    private LocalDateTime paymentDate;
    private Boolean verified;
    private String message;

    public PaymentResponse(String transactionId, String rollNumber, Double amount,
            LocalDateTime paymentDate, Boolean verified) {
        this.transactionId = transactionId;
        this.rollNumber = rollNumber;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.verified = verified;
        this.message = "Payment initiated successfully";
    }
}
