# Fee Sync System - Complete Documentation

## 📋 Table of Contents
1. [Overview](#overview)
2. [System Requirements](#system-requirements)
3. [Installation & Setup](#installation--setup)
4. [Running the Application](#running-the-application)
5. [API Documentation](#api-documentation)
6. [Testing with Postman](#testing-with-postman)
7. [Frontend Usage](#frontend-usage)
8. [Troubleshooting](#troubleshooting)

---

## 🎯 Overview

**Real-Time Fee Payment Data Synchronization and No-Due Clearance System**

A full-stack web application that eliminates manual fee verification by:
- Automatically receiving payment data
- Verifying transactions
- Updating student fee status instantly
- Providing no-due clearance status
- Offering admin dashboard with real-time updates

### Tech Stack
- **Backend**: Java 17, Spring Boot 3.2.2, Spring Data JPA, MySQL
- **Frontend**: HTML5, CSS3, Bootstrap 5, JavaScript (Fetch API)
- **Database**: MySQL 8.0+

---

## 💻 System Requirements

### Required Software
1. **Java Development Kit (JDK) 17 or higher**
   - Download: https://www.oracle.com/java/technologies/downloads/

2. **MySQL Server 8.0 or higher**
   - Download: https://dev.mysql.com/downloads/mysql/

3. **Maven 3.6+ (or use included Maven wrapper)**

4. **Web Browser** (Chrome, Firefox, Edge, Safari)

### Optional
- **Postman** for API testing
- **Git** for version control

---

## 🚀 Installation & Setup

### Step 1: Database Setup

1. **Start MySQL Server**
   ```bash
   # Windows: Start MySQL service from Services
   # Or use MySQL Workbench
   ```

2. **Create Database** (Optional - Application auto-creates)
   ```sql
   CREATE DATABASE fee_sync_db;
   ```

3. **Configure Database Credentials**
   
   Edit: `PAYMENT/src/main/resources/application.properties`
   
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/fee_sync_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```
   
   **⚠️ IMPORTANT**: Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL root password!

### Step 2: Backend Setup

1. **Navigate to Backend Directory**
   ```bash
   cd C:\Users\Asus\Downloads\PAYMENT\PAYMENT
   ```

2. **Build the Project**
   ```bash
   # Using Maven Wrapper (Windows)
   mvnw.cmd clean install
   
   # Or using Maven directly
   mvn clean install
   ```

3. **Verify Build Success**
   - Look for "BUILD SUCCESS" message
   - JAR file created in `target/` directory

---

## ▶️ Running the Application

### Start Backend Server

**Option 1: Using Maven**
```bash
mvnw.cmd spring-boot:run
```

**Option 2: Using JAR file**
```bash
java -jar target/fee-sync-system-1.0.0.jar
```

**Expected Output:**
```
==============================================
Fee Sync System Started Successfully!
Server running on: http://localhost:8080
==============================================
```

### Access Frontend

1. **Open Frontend Files**
   - Navigate to: `C:\Users\Asus\Downloads\PAYMENT\frontend\`
   - Open `index.html` in your web browser

2. **Or use Live Server** (Recommended)
   - Use VS Code Live Server extension
   - Right-click `index.html` → "Open with Live Server"

---

## 📡 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Endpoints

#### 1. **Health Check**
```http
GET /api/health
```
**Response:**
```json
{
  "status": "UP",
  "message": "Fee Sync System is running"
}
```

---

#### 2. **Process Payment**
```http
POST /api/payment
Content-Type: application/json
```

**Request Body:**
```json
{
  "rollNumber": "22CS001",
  "amount": 5000
}
```

**Success Response (201 Created):**
```json
{
  "transactionId": "TXN-a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "rollNumber": "22CS001",
  "amount": 5000.0,
  "paymentDate": "2026-02-16T12:30:45",
  "verified": false,
  "message": "Payment initiated successfully"
}
```

**Error Response (400 Bad Request):**
```json
{
  "timestamp": "2026-02-16T12:30:45",
  "status": 400,
  "error": "Bad Request",
  "message": "Payment amount exceeds due amount"
}
```

---

#### 3. **Get Transaction Details**
```http
GET /api/transaction/{transactionId}
```

**Example:**
```http
GET /api/transaction/TXN-a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

**Response (200 OK):**
```json
{
  "id": 1,
  "transactionId": "TXN-a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "rollNumber": "22CS001",
  "amount": 5000.0,
  "paymentDate": "2026-02-16T12:30:45",
  "verified": false
}
```

---

#### 4. **Verify Transaction**
```http
POST /api/verify
Content-Type: application/json
```

**Request Body:**
```json
{
  "transactionId": "TXN-a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

**Success Response (200 OK):**
```json
{
  "message": "Transaction verified successfully",
  "transactionId": "TXN-a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

---

#### 5. **Get Student Status**
```http
GET /api/student-status?rollNumber=22CS001
```

**Response (200 OK):**
```json
{
  "rollNumber": "22CS001",
  "name": "Rahul Sharma",
  "totalFee": 50000.0,
  "paidAmount": 5000.0,
  "dueAmount": 45000.0,
  "status": "PARTIAL"
}
```

---

#### 6. **Check No-Due Status**
```http
GET /api/no-due?rollNumber=22CS001
```

**Response (200 OK):**
```json
{
  "rollNumber": "22CS001",
  "status": "DUES PENDING - Amount: ₹45000.0"
}
```

**Or (if fully paid):**
```json
{
  "rollNumber": "22CS003",
  "status": "NO DUE CLEARED"
}
```

---

#### 7. **Get All Students (Admin)**
```http
GET /api/students
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "rollNumber": "22CS001",
    "name": "Rahul Sharma",
    "totalFee": 50000.0,
    "paidAmount": 0.0,
    "dueAmount": 50000.0,
    "status": "UNPAID"
  },
  ...
]
```

---

#### 8. **Get All Transactions (Admin)**
```http
GET /api/transactions
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "transactionId": "TXN-...",
    "rollNumber": "22CS001",
    "amount": 5000.0,
    "paymentDate": "2026-02-16T12:30:45",
    "verified": false
  },
  ...
]
```

---

## 🧪 Testing with Postman

### Setup Postman Collection

1. **Create New Collection**: "Fee Sync System"

2. **Set Base URL Variable**
   - Variable: `base_url`
   - Value: `http://localhost:8080/api`

### Test Scenarios

#### Scenario 1: Complete Payment Flow

**Step 1: Check Initial Status**
```http
GET {{base_url}}/student-status?rollNumber=22CS001
```

**Step 2: Make Payment**
```http
POST {{base_url}}/payment
Content-Type: application/json

{
  "rollNumber": "22CS001",
  "amount": 10000
}
```
**Save the `transactionId` from response!**

**Step 3: Verify Transaction**
```http
POST {{base_url}}/verify
Content-Type: application/json

{
  "transactionId": "TXN-..." // Use saved transaction ID
}
```

**Step 4: Check Updated Status**
```http
GET {{base_url}}/student-status?rollNumber=22CS001
```

**Step 5: Check No-Due**
```http
GET {{base_url}}/no-due?rollNumber=22CS001
```

---

#### Scenario 2: Error Handling Tests

**Test 1: Invalid Roll Number**
```http
POST {{base_url}}/payment
Content-Type: application/json

{
  "rollNumber": "INVALID",
  "amount": 5000
}
```
**Expected**: 404 Not Found

**Test 2: Negative Amount**
```http
POST {{base_url}}/payment
Content-Type: application/json

{
  "rollNumber": "22CS001",
  "amount": -1000
}
```
**Expected**: 400 Bad Request

**Test 3: Amount Exceeds Due**
```http
POST {{base_url}}/payment
Content-Type: application/json

{
  "rollNumber": "22CS001",
  "amount": 999999
}
```
**Expected**: 400 Bad Request

**Test 4: Duplicate Verification**
```http
POST {{base_url}}/verify
Content-Type: application/json

{
  "transactionId": "ALREADY_VERIFIED_TXN_ID"
}
```
**Expected**: 400 Bad Request - "Transaction already verified"

---

## 🌐 Frontend Usage

### 1. **Home Page** (`index.html`)
- Landing page with system overview
- Quick navigation to all features

### 2. **Payment Page** (`payment.html`)
- **URL**: `payment.html`
- **Purpose**: Submit fee payments
- **Steps**:
  1. Enter roll number (e.g., 22CS001)
  2. Enter amount (e.g., 5000)
  3. Click "Submit Payment"
  4. View transaction details
  5. Save transaction ID for tracking

### 3. **Status Page** (`status.html`)
- **URL**: `status.html`
- **Purpose**: Check fee status
- **Steps**:
  1. Enter roll number
  2. Click "Search"
  3. View fee details
  4. Click "Check No-Due Status"

### 4. **Admin Login** (`admin.html`)
- **URL**: `admin.html`
- **Credentials**:
  - Username: `admin`
  - Password: `admin`

### 5. **Admin Dashboard** (`dashboard.html`)
- **URL**: `dashboard.html` (requires login)
- **Features**:
  - View all students
  - View transaction history
  - Verify pending transactions
  - Real-time statistics
  - Auto-refresh every 10 seconds

---

## 🔧 Troubleshooting

### Issue 1: Backend Won't Start

**Error**: "Cannot connect to database"

**Solution**:
1. Verify MySQL is running
2. Check credentials in `application.properties`
3. Ensure database exists or auto-create is enabled

---

### Issue 2: CORS Errors in Frontend

**Error**: "Access-Control-Allow-Origin"

**Solution**:
- Backend already has `@CrossOrigin(origins = "*")` enabled
- Clear browser cache
- Try different browser

---

### Issue 3: Port 8080 Already in Use

**Error**: "Port 8080 is already in use"

**Solution**:
```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID)
taskkill /PID <PID> /F
```

**Or change port** in `application.properties`:
```properties
server.port=8081
```
(Update frontend JS files with new port!)

---

### Issue 4: Maven Build Fails

**Error**: "Cannot resolve dependencies"

**Solution**:
```bash
# Clear Maven cache
mvnw.cmd clean

# Force update dependencies
mvnw.cmd clean install -U
```

---

### Issue 5: Sample Data Not Loading

**Solution**:
- Check application logs
- Manually run SQL from `database/schema.sql`
- Verify `DataInitializer` is executing

---

## 📊 Sample Test Data

### Pre-loaded Students

| Roll Number | Name | Total Fee | Paid | Due | Status |
|-------------|------|-----------|------|-----|--------|
| 22CS001 | Rahul Sharma | ₹50,000 | ₹0 | ₹50,000 | UNPAID |
| 22CS002 | Priya Patel | ₹50,000 | ₹25,000 | ₹25,000 | PARTIAL |
| 22CS003 | Amit Kumar | ₹50,000 | ₹50,000 | ₹0 | PAID |
| 22CS004 | Sneha Reddy | ₹50,000 | ₹0 | ₹50,000 | UNPAID |
| 22CS005 | Vikram Singh | ₹50,000 | ₹10,000 | ₹40,000 | PARTIAL |
| 22CS006 | Anjali Gupta | ₹50,000 | ₹0 | ₹50,000 | UNPAID |
| 22CS007 | Karan Mehta | ₹50,000 | ₹50,000 | ₹0 | PAID |
| 22CS008 | Pooja Verma | ₹50,000 | ₹30,000 | ₹20,000 | PARTIAL |

---

## 🎓 System Flow

```
1. Student submits payment (payment.html)
   ↓
2. Transaction created with unique ID
   ↓
3. Transaction stored in database (verified = false)
   ↓
4. Admin views transaction in dashboard
   ↓
5. Admin clicks "Verify" button
   ↓
6. System marks transaction as verified
   ↓
7. Student fee automatically updated
   ↓
8. Status changes (UNPAID → PARTIAL → PAID)
   ↓
9. No-due clearance available if fully paid
```

---

## 📞 Support

For issues or questions:
1. Check logs in console
2. Review this documentation
3. Verify all prerequisites are installed
4. Ensure MySQL is running
5. Check application.properties configuration

---

## ✅ Quick Start Checklist

- [ ] Java 17+ installed
- [ ] MySQL 8.0+ installed and running
- [ ] Database credentials configured
- [ ] Backend built successfully
- [ ] Backend running on port 8080
- [ ] Frontend files accessible
- [ ] Tested health endpoint
- [ ] Sample data loaded
- [ ] Admin login working
- [ ] Payment flow tested

---

**🎉 Congratulations! Your Fee Sync System is ready to use!**
