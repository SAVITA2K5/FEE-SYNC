// API Base URL
const API_BASE_URL = 'http://localhost:8080/api';

// Get form elements
const paymentForm = document.getElementById('paymentForm');
const responseSection = document.getElementById('responseSection');
const responseAlert = document.getElementById('responseAlert');

// Handle form submission
paymentForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const rollNumber = document.getElementById('rollNumber').value.trim();
    const amount = parseFloat(document.getElementById('amount').value);

    // Validate inputs
    if (!rollNumber || !amount || amount <= 0) {
        showError('Please enter valid roll number and amount');
        return;
    }

    try {
        // Show loading
        showLoading();

        // Make API call
        const response = await fetch(`${API_BASE_URL}/payment`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                rollNumber: rollNumber,
                amount: amount
            })
        });

        const data = await response.json();

        if (response.ok) {
            // Show success
            showSuccess(data);
            // Reset form
            paymentForm.reset();
        } else {
            // Show error
            showError(data.message || 'Payment failed');
        }
    } catch (error) {
        console.error('Error:', error);
        showError('Failed to connect to server. Please ensure the backend is running.');
    }
});

// Show loading state
function showLoading() {
    responseSection.style.display = 'block';
    responseAlert.className = 'alert alert-info';
    responseAlert.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Processing payment...';
    document.getElementById('transactionDetails').style.display = 'none';
}

// Show success
function showSuccess(data) {
    responseAlert.className = 'alert alert-success';
    responseAlert.innerHTML = '<i class="fas fa-check-circle me-2"></i>Payment submitted successfully!';

    // Show transaction details
    document.getElementById('txnId').textContent = data.transactionId;
    document.getElementById('txnRoll').textContent = data.rollNumber;
    document.getElementById('txnAmount').textContent = '₹' + data.amount.toFixed(2);
    document.getElementById('txnDate').textContent = formatDate(data.paymentDate);
    document.getElementById('txnStatus').textContent = data.verified ? 'Verified' : 'Pending Verification';
    document.getElementById('txnStatus').className = data.verified ? 'badge bg-success' : 'badge bg-warning';

    document.getElementById('transactionDetails').style.display = 'block';
}

// Show error
function showError(message) {
    responseSection.style.display = 'block';
    responseAlert.className = 'alert alert-danger';
    responseAlert.innerHTML = `<i class="fas fa-exclamation-triangle me-2"></i>${message}`;
    document.getElementById('transactionDetails').style.display = 'none';
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
