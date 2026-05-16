# 🎯 STEP-BY-STEP RUN INSTRUCTIONS

## ⚠️ BEFORE YOU START

### 1. Check MySQL is Running
- Open **Services** (Windows + R → type `services.msc`)
- Find **MySQL80** (or your MySQL version)
- Status should be **Running**
- If not, right-click → **Start**

### 2. Know Your MySQL Password
- You'll need your MySQL root password
- If you don't remember, you may need to reset it

---

## 📝 STEP 1: Configure Database Connection

1. **Open File**:
   ```
   C:\Users\Asus\Downloads\PAYMENT\PAYMENT\src\main\resources\application.properties
   ```

2. **Find Line 4**:
   ```properties
   spring.datasource.password=root
   ```

3. **Replace `root` with YOUR MySQL password**:
   ```properties
   spring.datasource.password=YourActualPassword123
   ```

4. **Save the file** (Ctrl + S)

---

## 🚀 STEP 2: Start the Backend

### Option A: Using Command Prompt

1. **Open Command Prompt** (Windows + R → type `cmd`)

2. **Navigate to project**:
   ```bash
   cd C:\Users\Asus\Downloads\PAYMENT\PAYMENT
   ```

3. **Run the application**:
   ```bash
   mvnw.cmd spring-boot:run
   ```

4. **Wait for this message**:
   ```
   ==============================================
   Fee Sync System Started Successfully!
   Server running on: http://localhost:8080
   ==============================================
   ```

5. **✅ Backend is now running!** (Keep this window open)

### Option B: Using IDE (Eclipse/IntelliJ)

1. **Import project** as Maven project
2. **Right-click** on `FeeSyncApplication.java`
3. **Run As** → **Java Application**

---

## 🌐 STEP 3: Open the Frontend

### Method 1: Direct Browser Open

1. **Navigate to**:
   ```
   C:\Users\Asus\Downloads\PAYMENT\frontend\
   ```

2. **Double-click** `index.html`

3. **Browser opens** with the application

### Method 2: Using VS Code Live Server (Recommended)

1. **Open folder** `frontend` in VS Code
2. **Right-click** `index.html`
3. **Select** "Open with Live Server"
4. **Browser opens** automatically

---

## 🧪 STEP 4: Test the System

### Test 1: Make Your First Payment

1. **Click** "Make Payment" button on home page

2. **Enter**:
   - Roll Number: `22CS001`
   - Amount: `5000`

3. **Click** "Submit Payment"

4. **You should see**:
   - ✅ Success message
   - Transaction ID (like `TXN-a1b2c3d4-...`)
   - Payment details

5. **📋 COPY THE TRANSACTION ID** - You'll need it!

---

### Test 2: Verify the Payment (Admin)

1. **Click** "Admin" in navigation

2. **Login with**:
   - Username: `admin`
   - Password: `admin`

3. **You'll see**:
   - Statistics cards (Total, Paid, Partial, Unpaid students)
   - All students table
   - Transaction history table

4. **Find your transaction** in the bottom table

5. **Click** "Verify" button (green button)

6. **Confirm** verification in popup

7. **✅ Transaction is now verified!**

---

### Test 3: Check Updated Status

1. **Click** "Check Status" in navigation

2. **Enter** Roll Number: `22CS001`

3. **Click** "Search"

4. **You should see**:
   - Student name: Rahul Sharma
   - Total Fee: ₹50,000.00
   - Paid Amount: ₹5,000.00 (updated!)
   - Due Amount: ₹45,000.00
   - Status: PARTIAL (changed from UNPAID!)

5. **Click** "Check No-Due Status"

6. **You should see**:
   - "DUES PENDING - Amount: ₹45,000.0"

---

### Test 4: Complete Payment & Get No-Due

1. **Go back** to "Make Payment"

2. **Enter**:
   - Roll Number: `22CS001`
   - Amount: `45000` (remaining amount)

3. **Submit payment**

4. **Go to Admin** → **Verify** the new transaction

5. **Check Status** again for `22CS001`

6. **Click** "Check No-Due Status"

7. **You should see**:
   - ✅ "NO DUE CLEARED"
   - Congratulations message!

---

## 🎓 STEP 5: Explore Other Students

Try these roll numbers:

| Roll Number | Name | Initial Status |
|-------------|------|----------------|
| 22CS001 | Rahul Sharma | UNPAID |
| 22CS002 | Priya Patel | PARTIAL (₹25,000 paid) |
| 22CS003 | Amit Kumar | PAID (No dues) |
| 22CS004 | Sneha Reddy | UNPAID |
| 22CS005 | Vikram Singh | PARTIAL (₹10,000 paid) |

---

## 🧪 STEP 6: Test with Postman (Optional)

1. **Open Postman**

2. **Import Collection**:
   - Click "Import"
   - Select file: `C:\Users\Asus\Downloads\PAYMENT\postman\Fee_Sync_System_API.postman_collection.json`

3. **Test Health Check**:
   - Select "Health Check"
   - Click "Send"
   - Should return: `{"status": "UP", ...}`

4. **Test Payment API**:
   - Select "1. Process Payment"
   - Click "Send"
   - Copy the `transactionId` from response

5. **Test Verification**:
   - Select "3. Verify Transaction"
   - Replace `TXN-REPLACE-WITH-ACTUAL-ID` with your transaction ID
   - Click "Send"

---

## ❌ TROUBLESHOOTING

### Problem: Backend won't start

**Error**: "Cannot connect to database"

**Solution**:
1. Check MySQL is running
2. Verify password in `application.properties`
3. Try connecting to MySQL manually

---

### Problem: Port 8080 already in use

**Error**: "Port 8080 is already in use"

**Solution**:
```bash
# Find what's using port 8080
netstat -ano | findstr :8080

# Kill the process (replace 1234 with actual PID)
taskkill /PID 1234 /F
```

---

### Problem: Frontend shows "Failed to connect"

**Solution**:
1. Verify backend is running (check terminal)
2. Check URL is `http://localhost:8080`
3. Open browser console (F12) for errors

---

### Problem: No sample data showing

**Solution**:
1. Check backend logs for errors
2. Manually run SQL from `database/schema.sql`
3. Restart backend

---

## 📊 EXPECTED RESULTS

### After Starting Backend:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.2)

...
==============================================
Fee Sync System Started Successfully!
Server running on: http://localhost:8080
==============================================
```

### After Opening Frontend:
- Beautiful landing page with gradient background
- Three main buttons: Make Payment, Check Status, Admin Login
- Responsive design

### After Making Payment:
- Success message
- Transaction ID displayed
- Payment details shown
- Status: "Pending Verification"

### After Admin Verification:
- Transaction marked as "Verified"
- Student fee updated automatically
- Status changed (UNPAID → PARTIAL or PAID)

### After Checking Status:
- All fee information displayed
- Status badge (color-coded)
- No-due status available

---

## ✅ SUCCESS CHECKLIST

- [ ] MySQL is running
- [ ] Password configured in application.properties
- [ ] Backend started successfully
- [ ] See "Fee Sync System Started Successfully!" message
- [ ] Frontend opened in browser
- [ ] Can see landing page
- [ ] Made a test payment
- [ ] Got transaction ID
- [ ] Logged in as admin
- [ ] Verified transaction
- [ ] Checked updated status
- [ ] Tested no-due check

---

## 🎉 CONGRATULATIONS!

If all tests passed, your system is **FULLY FUNCTIONAL**!

You now have a complete, production-ready Fee Sync System running!

---

## 📚 NEXT STEPS

1. **Read** `README.md` for detailed API documentation
2. **Import** Postman collection for API testing
3. **Review** `PROJECT_STRUCTURE.md` for architecture
4. **Customize** the system as needed

---

## 💡 TIPS

- Keep backend terminal open while using the system
- Refresh admin dashboard to see real-time updates
- Use different browsers to test simultaneously
- Check browser console (F12) if something doesn't work
- Backend logs show all API calls

---

**🚀 Happy Testing!**
