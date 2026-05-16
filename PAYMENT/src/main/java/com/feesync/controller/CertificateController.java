package com.feesync.controller;

import com.feesync.dto.CertificateResponse;
import com.feesync.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Certificate Controller - Handles certificate related API requests
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CertificateController {

    private final CertificateService certificateService;

    /**
     * GET /api/generate-no-due/{rollNumber} - Generate No-Due Certificate
     */
    @GetMapping("/generate-no-due/{rollNumber}")
    public ResponseEntity<?> generateNoDue(@PathVariable String rollNumber) {
        log.info("API GET /api/generate-no-due/{}", rollNumber);
        try {
            CertificateResponse response = certificateService.generateCertificate(rollNumber);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error generating certificate for {}: {}", rollNumber, e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(400).body(errorResponse);
        }
    }

    /**
     * GET /api/verify-certificate/{certificateId} - Verify a certificate
     */
    @GetMapping("/verify-certificate/{certificateId}")
    public ResponseEntity<?> verifyCertificate(@PathVariable String certificateId) {
        log.info("API GET /api/verify-certificate/{}", certificateId);
        try {
            CertificateResponse response = certificateService.verifyCertificate(certificateId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error verifying certificate {}: {}", certificateId, e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        }
    }
}
