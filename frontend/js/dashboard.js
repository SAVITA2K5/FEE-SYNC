// API Base URL
const API_BASE_URL = 'http://localhost:8080/api';

// Check if admin is logged in
if (!sessionStorage.getItem('adminLoggedIn')) {
    window.location.href = 'admin.html';
}

// Logout function
function logout() {
    sessionStorage.removeItem('adminLoggedIn');
    window.location.href = 'admin.html';
}

// Global variables
let currentTransactionId = '';
let verifyModal;

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
    verifyModal = new bootstrap.Modal(document.getElementById('verifyModal'));
    loadDashboardData();

    // Set up auto-refresh every 10 seconds
    setInterval(loadDashboardData, 10000);
});

// Load all dashboard data
async function loadDashboardData() {
    await Promise.all([
        loadStudents(),
        loadTransactions()
    ]);
}

// Load students
async function loadStudents() {
    try {
        const response = await fetch(`${API_BASE_URL}/students`);
        const students = await response.json();

        if (response.ok) {
            displayStudents(students);
            updateStats(students);
        } else {
            console.error('Failed to load students');
        }
    } catch (error) {
        console.error('Error loading students:', error);
    }
}

// Display students in table
function displayStudents(students) {
    const tbody = document.getElementById('studentsTableBody');

    if (students.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="text-center">No students found</td></tr>';
        return;
    }

    tbody.innerHTML = students.map(student => {
        const rowClass = student.status === 'UNPAID' ? 'unpaid-row' : '';
        const statusClass = getStatusBadgeClass(student.status);

        return `
            <tr class="${rowClass}">
                <td><strong>${student.rollNumber}</strong></td>
                <td>${student.name}</td>
                <td>₹${student.totalFee.toFixed(2)}</td>
                <td class="text-success">₹${student.paidAmount.toFixed(2)}</td>
                <td class="text-danger">₹${student.dueAmount.toFixed(2)}</td>
                <td><span class="badge ${statusClass}">${student.status}</span></td>
            </tr>
        `;
    }).join('');
}

// Update statistics
function updateStats(students) {
    const total = students.length;
    const paid = students.filter(s => s.status === 'PAID').length;
    const partial = students.filter(s => s.status === 'PARTIAL').length;
    const unpaid = students.filter(s => s.status === 'UNPAID').length;

    document.getElementById('totalStudents').textContent = total;
    document.getElementById('paidStudents').textContent = paid;
    document.getElementById('partialStudents').textContent = partial;
    document.getElementById('unpaidStudents').textContent = unpaid;
}

// Load transactions
async function loadTransactions() {
    try {
        const response = await fetch(`${API_BASE_URL}/transactions`);
        const transactions = await response.json();

        if (response.ok) {
            displayTransactions(transactions);
        } else {
            console.error('Failed to load transactions');
        }
    } catch (error) {
        console.error('Error loading transactions:', error);
    }
}

// Display transactions in table
function displayTransactions(transactions) {
    const tbody = document.getElementById('transactionsTableBody');

    if (transactions.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="text-center">No transactions found</td></tr>';
        return;
    }

    // Sort by date (newest first)
    transactions.sort((a, b) => new Date(b.paymentDate) - new Date(a.paymentDate));

    tbody.innerHTML = transactions.map(txn => {
        const statusBadge = txn.verified
            ? '<span class="badge bg-success">Verified</span>'
            : '<span class="badge bg-warning">Pending</span>';

        const actionButton = txn.verified
            ? '<button class="btn btn-sm btn-secondary" disabled>Verified</button>'
            : `<button class="btn btn-sm btn-success" onclick="openVerifyModal('${txn.transactionId}')">
                <i class="fas fa-check me-1"></i>Verify
               </button>`;

        return `
            <tr>
                <td><code>${txn.transactionId}</code></td>
                <td><strong>${txn.rollNumber}</strong></td>
                <td class="text-success">₹${txn.amount.toFixed(2)}</td>
                <td>${formatDate(txn.paymentDate)}</td>
                <td>${statusBadge}</td>
                <td>${actionButton}</td>
            </tr>
        `;
    }).join('');
}

// Open verify modal
function openVerifyModal(transactionId) {
    currentTransactionId = transactionId;
    document.getElementById('modalTxnId').textContent = transactionId;
    verifyModal.show();
}

// Confirm verification
document.getElementById('confirmVerifyBtn').addEventListener('click', async () => {
    if (!currentTransactionId) return;

    try {
        const response = await fetch(`${API_BASE_URL}/verify`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                transactionId: currentTransactionId
            })
        });

        const data = await response.json();

        if (response.ok) {
            // Close modal
            verifyModal.hide();

            // Show success message
            showToast('Transaction verified successfully!', 'success');

            // Reload data
            await loadDashboardData();
        } else {
            showToast(data.message || 'Verification failed', 'error');
        }
    } catch (error) {
        console.error('Error verifying transaction:', error);
        showToast('Failed to verify transaction', 'error');
    }
});

// Get status badge class
function getStatusBadgeClass(status) {
    switch (status) {
        case 'PAID':
            return 'bg-success';
        case 'PARTIAL':
            return 'bg-warning';
        case 'UNPAID':
            return 'bg-danger';
        default:
            return 'bg-secondary';
    }
}

// Format date
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString('en-IN', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

// Show toast notification
function showToast(message, type) {
    const alertClass = type === 'success' ? 'alert-success' : 'alert-danger';
    const icon = type === 'success' ? 'fa-check-circle' : 'fa-exclamation-triangle';

    const toast = document.createElement('div');
    toast.className = `alert ${alertClass} position-fixed top-0 start-50 translate-middle-x mt-3`;
    toast.style.zIndex = '9999';
    toast.innerHTML = `<i class="fas ${icon} me-2"></i>${message}`;

    document.body.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 3000);
}
