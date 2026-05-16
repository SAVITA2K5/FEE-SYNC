package com.feesync.exception;

/**
 * Custom exception for invalid payment scenarios
 */
public class InvalidPaymentException extends RuntimeException {

    public InvalidPaymentException(String message) {
        super(message);
    }
}
