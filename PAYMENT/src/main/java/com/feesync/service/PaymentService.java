package com.feesync.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feesync.dto.PaymentRequest;
import com.feesync.dto.PaymentResponse;
import com.feesync.dto.StudentStatusResponse;
import com.feesync.dto.VerificationRequest;
import com.feesync.exception.InvalidPaymentException;
import com.feesync.exception.ResourceNotFoundException;
import com.feesync.model.Student;
import com.feesync.model.Transaction;
import com.feesync.repository.StudentRepository;
import com.feesync.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Payment Service - Handles all payment related operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final StudentRepository studentRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Process payment request
     */
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Processing payment for roll number: {}", request.getRollNumber());

        // Validate student exists
        Student student = studentRepository.findByRollNumber(request.getRollNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with roll number: " + request.getRollNumber()));

        // Validate amount is not negative
        if (request.getAmount() <= 0) {
            throw new InvalidPaymentException("Payment amount must be positive");
        }

        // Validate amount does not exceed due amount
        if (request.getAmount() > student.getDueAmount()) {
            throw new InvalidPaymentException(
                    "Payment amount (" + request.getAmount() +
                            ") exceeds due amount (" + student.getDueAmount() + ")");
        }

        // Generate unique transaction ID
        String transactionId = "TXN-" + UUID.randomUUID().toString();

        // Create and save transaction
        Transaction transaction = new Transaction(
                transactionId,
                request.getRollNumber(),
                request.getAmount());
        transaction = transactionRepository.save(transaction);

        log.info("Payment transaction created: {}", transactionId);

        return new PaymentResponse(
                transaction.getTransactionId(),
                transaction.getRollNumber(),
                transaction.getAmount(),
                transaction.getPaymentDate(),
                transaction.getVerified());
    }

    /**
     * Get transaction by ID
     */
    public Transaction getTransaction(String transactionId) {
        log.info("Fetching transaction: {}", transactionId);
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Transaction not found with ID: " + transactionId));
    }

    /**
     * Verify transaction
     */
    @Transactional
    public String verifyTransaction(VerificationRequest request) {
        log.info("Verifying transaction: {}", request.getTransactionId());

        // Find transaction
        Transaction transaction = transactionRepository.findByTransactionId(request.getTransactionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Transaction not found with ID: " + request.getTransactionId()));

        // Check if already verified
        if (transaction.getVerified()) {
            throw new InvalidPaymentException("Transaction already verified");
        }

        // Mark as verified
        transaction.setVerified(true);
        transactionRepository.save(transaction);

        log.info("Transaction verified successfully: {}", request.getTransactionId());

        // Now update student fee
        updateStudentFee(transaction.getRollNumber(), transaction.getAmount());

        return "Transaction verified successfully";
    }

    /**
     * Update student fee after verification
     */
    @Transactional
    public void updateStudentFee(String rollNumber, Double amount) {
        log.info("Updating fee for student: {} by amount: {}", rollNumber, amount);

        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with roll number: " + rollNumber));

        // Update paid amount
        student.setPaidAmount(student.getPaidAmount() + amount);

        // Recalculate due amount
        student.setDueAmount(student.getTotalFee() - student.getPaidAmount());

        // Update status
        student.updateStatus();

        studentRepository.save(student);

        log.info("Fee updated for student: {} - New Paid: {}, New Due: {}, Status: {}",
                rollNumber, student.getPaidAmount(), student.getDueAmount(), student.getStatus());
    }

    /**
     * Get student status
     */
    public StudentStatusResponse getStudentStatus(String rollNumber) {
        log.info("Fetching status for student: {}", rollNumber);

        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with roll number: " + rollNumber));

        student.updateStatus(); // Update the status first

        return new StudentStatusResponse(
                student.getRollNumber(),
                student.getName(),
                student.getTotalFee(),
                student.getPaidAmount(),
                student.getDueAmount(),
                student.getStatus().toString());
    }

    /**
     * Check no-due status
     */
    public String checkNoDueStatus(String rollNumber) {
        log.info("Checking no-due status for student: {}", rollNumber);

        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with roll number: " + rollNumber));

        if (student.getDueAmount() == 0) {
            return "NO DUE CLEARED";
        } else {
            return "DUES PENDING - Amount: ₹" + student.getDueAmount();
        }
    }

    /**
     * Get all students (for admin dashboard)
     */
    public List<Student> getAllStudents() {
        log.info("Fetching all students");
        return studentRepository.findAll();
    }

    /**
     * Get all transactions (for admin dashboard)
     */
    public List<Transaction> getAllTransactions() {
        log.info("Fetching all transactions");
        return transactionRepository.findAll();
    }
}
