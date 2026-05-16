package com.feesync.service;

import com.feesync.dto.CertificateResponse;
import com.feesync.exception.InvalidPaymentException;
import com.feesync.exception.ResourceNotFoundException;
import com.feesync.model.Certificate;
import com.feesync.model.Student;
import com.feesync.model.Transaction;
import com.feesync.repository.CertificateRepository;
import com.feesync.repository.StudentRepository;
import com.feesync.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Certificate Service - Handles logic for No-Due certificate generation
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateService {

    private final StudentRepository studentRepository;
    private final TransactionRepository transactionRepository;
    private final CertificateRepository certificateRepository;

    private static final String COLLEGE_NAME = "ABC Engineering College";
    private static final String STATEMENT = "All academic and financial obligations have been cleared. The student has no outstanding dues as of the date of issue.";

    /**
     * Generate or fetch No-Due certificate
     */
    @Transactional
    public CertificateResponse generateCertificate(String rollNumber) {
        log.info("Generating No-Due Certificate for roll number: {}", rollNumber);

        // 1. Validate student exists
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with roll number: " + rollNumber));

        // 2. Check due_amount == 0
        if (student.getDueAmount() > 0) {
            throw new InvalidPaymentException(
                    "No Due Certificate cannot be generated. Pending dues exist: ₹" + student.getDueAmount());
        }

        // 3. Fetch all transactions
        List<Transaction> allTransactions = transactionRepository.findAll().stream()
                .filter(t -> t.getRollNumber().equals(rollNumber))
                .collect(Collectors.toList());

        // 4. Check if any transactions exist
        if (allTransactions.isEmpty()) {
            throw new InvalidPaymentException(
                    "No transactions found for this student. Certificate cannot be generated.");
        }

        // 5. Check if all transactions are verified
        boolean allVerified = allTransactions.stream().allMatch(Transaction::getVerified);
        if (!allVerified) {
            throw new InvalidPaymentException(
                    "Cannot generate certificate. Some transactions are still pending verification.");
        }

        // 6. Check if certificate already exists, if not create one
        Certificate certificate = certificateRepository.findByRollNumber(rollNumber)
                .orElseGet(() -> {
                    String certId = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                    Certificate newCert = new Certificate(certId, rollNumber, "ADMIN_SYSTEM");
                    return certificateRepository.save(newCert);
                });

        // 7. Build Response
        return new CertificateResponse(
                certificate.getCertificateId(),
                student.getRollNumber(),
                student.getName(),
                student.getTotalFee(),
                student.getPaidAmount(),
                student.getDueAmount(),
                certificate.getIssueDate(),
                COLLEGE_NAME,
                allTransactions,
                STATEMENT);
    }

    /**
     * Verify certificate validity
     */
    public CertificateResponse verifyCertificate(String certificateId) {
        log.info("Verifying certificate ID: {}", certificateId);
        Certificate certificate = certificateRepository.findByCertificateId(certificateId)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with ID: " + certificateId));

        return generateCertificate(certificate.getRollNumber());
    }
}
