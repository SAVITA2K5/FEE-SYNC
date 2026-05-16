package com.feesync.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feesync.dto.PaymentRequest;
import com.feesync.dto.PaymentResponse;
import com.feesync.dto.StudentStatusResponse;
import com.feesync.dto.VerificationRequest;
import com.feesync.model.Student;
import com.feesync.model.Transaction;
import com.feesync.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Payment Controller - REST API endpoints
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * POST /api/payment - Process payment
     */
    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
        log.info("POST /api/payment - Roll Number: {}", request.getRollNumber());
        PaymentResponse response = paymentService.processPayment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * GET /api/transaction/{id} - Get transaction details
     */
    @GetMapping("/transaction/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable String id) {
        log.info("GET /api/transaction/{}", id);
        Transaction transaction = paymentService.getTransaction(id);
        return ResponseEntity.ok(transaction);
    }

    /**
     * POST /api/verify - Verify transaction
     */
    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyTransaction(@Valid @RequestBody VerificationRequest request) {
        log.info("POST /api/verify - Transaction ID: {}", request.getTransactionId());
        String message = paymentService.verifyTransaction(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        response.put("transactionId", request.getTransactionId());
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/student-status - Get student fee status
     */
    @GetMapping("/student-status")
    public ResponseEntity<StudentStatusResponse> getStudentStatus(@RequestParam String rollNumber) {
        log.info("GET /api/student-status - Roll Number: {}", rollNumber);
        StudentStatusResponse response = paymentService.getStudentStatus(rollNumber);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/no-due - Check no-due status
     */
    @GetMapping("/no-due")
    public ResponseEntity<Map<String, String>> checkNoDue(@RequestParam String rollNumber) {
        log.info("GET /api/no-due - Roll Number: {}", rollNumber);
        String status = paymentService.checkNoDueStatus(rollNumber);
        Map<String, String> response = new HashMap<>();
        response.put("rollNumber", rollNumber);
        response.put("status", status);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/students - Get all students (Admin)
     */
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        log.info("GET /api/students");
        List<Student> students = paymentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    /**
     * GET /api/transactions - Get all transactions (Admin)
     */
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        log.info("GET /api/transactions");
        List<Transaction> transactions = paymentService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Fee Sync System is running");
        return ResponseEntity.ok(response);
    }
}
