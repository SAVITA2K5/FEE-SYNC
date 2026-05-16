-- =====================================================
-- Fee Sync System - Database Schema
-- Real-Time Fee Payment Data Synchronization System
-- =====================================================

-- Create Database
CREATE DATABASE IF NOT EXISTS fee_sync_db;
USE fee_sync_db;

-- =====================================================
-- Table: students
-- Stores student information and fee status
-- =====================================================
CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    total_fee DOUBLE NOT NULL,
    paid_amount DOUBLE NOT NULL DEFAULT 0,
    due_amount DOUBLE NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT chk_status CHECK (status IN ('PAID', 'UNPAID', 'PARTIAL'))
);

-- =====================================================
-- Table: transactions
-- Stores payment transaction records
-- =====================================================
CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id VARCHAR(50) UNIQUE NOT NULL,
    roll_number VARCHAR(20) NOT NULL,
    amount DOUBLE NOT NULL,
    payment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_student FOREIGN KEY (roll_number) REFERENCES students(roll_number)
);

-- =====================================================
-- Sample Data (Optional - Application auto-populates)
-- =====================================================
INSERT INTO students (roll_number, name, total_fee, paid_amount, due_amount, status) VALUES
('22CS001', 'Rahul Sharma', 50000, 0, 50000, 'UNPAID'),
('22CS002', 'Priya Patel', 50000, 25000, 25000, 'PARTIAL'),
('22CS003', 'Amit Kumar', 50000, 50000, 0, 'PAID'),
('22CS004', 'Sneha Reddy', 50000, 0, 50000, 'UNPAID'),
('22CS005', 'Vikram Singh', 50000, 10000, 40000, 'PARTIAL'),
('22CS006', 'Anjali Gupta', 50000, 0, 50000, 'UNPAID'),
('22CS007', 'Karan Mehta', 50000, 50000, 0, 'PAID'),
('22CS008', 'Pooja Verma', 50000, 30000, 20000, 'PARTIAL');

-- =====================================================
-- Indexes for Performance
-- =====================================================
CREATE INDEX idx_roll_number ON students(roll_number);
CREATE INDEX idx_transaction_id ON transactions(transaction_id);
CREATE INDEX idx_verified ON transactions(verified);
CREATE INDEX idx_payment_date ON transactions(payment_date DESC);

-- =====================================================
-- Views (Optional)
-- =====================================================

-- View: Unpaid Students
CREATE OR REPLACE VIEW unpaid_students AS
SELECT roll_number, name, total_fee, paid_amount, due_amount
FROM students
WHERE status = 'UNPAID';

-- View: Recent Transactions
CREATE OR REPLACE VIEW recent_transactions AS
SELECT t.transaction_id, t.roll_number, s.name, t.amount, t.payment_date, t.verified
FROM transactions t
JOIN students s ON t.roll_number = s.roll_number
ORDER BY t.payment_date DESC
LIMIT 50;

-- =====================================================
-- End of Schema
-- =====================================================
