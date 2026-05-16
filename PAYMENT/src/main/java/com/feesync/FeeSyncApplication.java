package com.feesync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Class for Fee Sync System
 * Real-Time Fee Payment Data Synchronization and No-Due Clearance System
 */
@SpringBootApplication
public class FeeSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeeSyncApplication.class, args);
        System.out.println("==============================================");
        System.out.println("Fee Sync System Started Successfully!");
        System.out.println("Server running on: http://localhost:8080");
        System.out.println("==============================================");
    }
}
