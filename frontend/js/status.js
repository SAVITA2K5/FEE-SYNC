// API Base URL
const API_BASE_URL = 'http://localhost:8080/api';

// Get form elements
const statusForm = document.getElementById('statusForm');
const statusSection = document.getElementById('statusSection');
const errorSection = document.getElementById('errorSection');
const checkNoDueBtn = document.getElementById('checkNoDueBtn');
const noDueResult = document.getElementById('noDueResult');

let currentRollNumber = '';

// Handle form submission
statusForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const rollNumber = document.getElementById('rollNumber').value.trim();

    if (!rollNumber) {
        showError('Please enter a roll number');
        return;
    }

    currentRollNumber = rollNumber;
    await fetchStudentStatus(rollNumber);
});

// Fetch student status
async function fetchStudentStatus(rollNumber) {
    try {
        const response = await fetch(`${API_BASE_URL}/student-status?rollNumber=${rollNumber}`);
        const data = await response.json();

        if (response.ok) {
            displayStatus(data);
            errorSection.style.display = 'none';
            statusSection.style.display = 'block';
            noDueResult.style.display = 'none';
        } else {
            showError(data.message || 'Student not found');
            statusSection.style.display = 'none';
        }
    } catch (error) {
        console.error('Error:', error);
        showError('Failed to connect to server. Please ensure the backend is running.');
        statusSection.style.display = 'none';
    }
}

// Display status
function displayStatus(data) {
    document.getElementById('studentRoll').textContent = data.rollNumber;
    document.getElementById('studentName').textContent = data.name;
    document.getElementById('totalFee').textContent = '₹' + data.totalFee.toFixed(2);
    document.getElementById('paidAmount').textContent = '₹' + data.paidAmount.toFixed(2);
    document.getElementById('dueAmount').textContent = '₹' + data.dueAmount.toFixed(2);

    // Set status badge
    const statusBadge = document.getElementById('statusBadge');
    statusBadge.textContent = data.status;

    switch (data.status) {
        case 'PAID':
            statusBadge.className = 'badge bg-success fs-5';
            break;
        case 'PARTIAL':
            statusBadge.className = 'badge bg-warning fs-5';
            break;
        case 'UNPAID':
            statusBadge.className = 'badge bg-danger fs-5';
            break;
        default:
            statusBadge.className = 'badge bg-secondary fs-5';
    }
}

// Check no-due status
checkNoDueBtn.addEventListener('click', async () => {
    if (!currentRollNumber) return;

    try {
        const response = await fetch(`${API_BASE_URL}/no-due?rollNumber=${currentRollNumber}`);
        const data = await response.json();

        if (response.ok) {
            displayNoDueStatus(data.status);
        } else {
            showError(data.message || 'Failed to check no-due status');
        }
    } catch (error) {
        console.error('Error:', error);
        showError('Failed to connect to server');
    }
});

// Display no-due status
function displayNoDueStatus(status) {
    noDueResult.style.display = 'block';

    if (status === 'NO DUE CLEARED') {
        noDueResult.className = 'alert alert-success';
        noDueResult.innerHTML = `
            <h5><i class="fas fa-certificate me-2"></i>No-Due Status</h5>
            <hr>
            <p class="mb-0"><strong>${status}</strong></p>
            <p class="mb-0 mt-2">Congratulations! All fees have been paid. You can collect your no-due certificate.</p>
            <button class="btn btn-outline-success mt-3 w-100" onclick="generateCertificate()">
                <i class="fas fa-file-pdf me-2"></i>Generate No-Due Certificate
            </button>
        `;
    } else {
        noDueResult.className = 'alert alert-warning';
        noDueResult.innerHTML = `
            <h5><i class="fas fa-exclamation-triangle me-2"></i>No-Due Status</h5>
            <hr>
            <p class="mb-0"><strong>${status}</strong></p>
            <p class="mb-0 mt-2">Please clear all pending dues to obtain no-due certificate.</p>
        `;
    }
}

// Function to navigate to certificate page
function generateCertificate() {
    if (!currentRollNumber) return;
    window.location.href = `certificate.html?rollNumber=${currentRollNumber}`;
}

// Show error
function showError(message) {
    errorSection.style.display = 'block';
    document.getElementById('errorMessage').textContent = message;
}
