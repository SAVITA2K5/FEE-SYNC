# 🚀 Quick Start Guide - Fee Sync System

## ⚡ 5-Minute Setup

### Prerequisites Check
- ✅ Java 17+ installed
- ✅ MySQL 8.0+ installed and running
- ✅ Web browser available

---

## Step 1: Configure Database (2 minutes)

1. **Open**: `PAYMENT/src/main/resources/application.properties`

2. **Update MySQL password**:
   ```properties
   spring.datasource.password=YOUR_MYSQL_ROOT_PASSWORD
   ```

3. **Save the file**

---

## Step 2: Start Backend (2 minutes)

1. **Open Terminal/Command Prompt**

2. **Navigate to project**:
   ```bash
   cd C:\Users\Asus\Downloads\PAYMENT\PAYMENT
   ```

3. **Run application**:
   ```bash
   mvnw.cmd spring-boot:run
   ```

4. **Wait for**:
   ```
   Fee Sync System Started Successfully!
   Server running on: http://localhost:8080
   ```

---

## Step 3: Open Frontend (1 minute)

1. **Navigate to**: `C:\Users\Asus\Downloads\PAYMENT\frontend\`

2. **Double-click**: `index.html`

3. **Browser opens** with the application

---

## 🎯 Test the System

### Test 1: Make a Payment

1. Click **"Make Payment"**
2. Enter:
   - Roll Number: `22CS001`
   - Amount: `5000`
3. Click **"Submit Payment"**
4. **Copy the Transaction ID** shown

### Test 2: Verify Payment (Admin)

1. Click **"Admin Login"**
2. Login:
   - Username: `admin`
   - Password: `admin`
3. Find your transaction in the table
4. Click **"Verify"** button
5. Confirm verification

### Test 3: Check Status

1. Go back to **"Check Status"**
2. Enter Roll Number: `22CS001`
3. Click **"Search"**
4. See updated fee status!
5. Click **"Check No-Due Status"**

---

## 📊 Sample Students Available

| Roll Number | Name | Status |
|-------------|------|--------|
| 22CS001 | Rahul Sharma | UNPAID |
| 22CS002 | Priya Patel | PARTIAL |
| 22CS003 | Amit Kumar | PAID |
| 22CS004 | Sneha Reddy | UNPAID |

---

## 🔧 Common Issues

### Backend won't start?
- Check MySQL is running
- Verify password in `application.properties`
- Ensure port 8080 is free

### Frontend not loading?
- Try different browser
- Check backend is running
- Open browser console for errors

### Database connection error?
- Start MySQL service
- Check credentials
- Verify database exists

---

## 📞 Need Help?

1. Check `README.md` for detailed documentation
2. Review API endpoints in Postman collection
3. Check application logs in terminal

---

## ✅ Success Checklist

- [ ] Backend running (port 8080)
- [ ] Frontend opened in browser
- [ ] Can make payment
- [ ] Admin can verify
- [ ] Status updates correctly
- [ ] No-due check works

---

**🎉 You're all set! Enjoy using Fee Sync System!**
