// API Base URL
const API_BASE_URL = 'http://localhost:8080/api';

document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const rollNumber = urlParams.get('rollNumber');

    if (!rollNumber) {
        showError('No student roll number provided.');
        return;
    }

    loadCertificateData(rollNumber);

    // Setup Download PDF Button
    document.getElementById('downloadPdfBtn').addEventListener('click', () => {
        const element = document.getElementById('certificateArea');
        const filename = `NoDue_${rollNumber}_${Date.now()}.pdf`;

        const opt = {
            margin: 0,
            filename: filename,
            image: { type: 'jpeg', quality: 0.98 },
            html2canvas: { scale: 2, useCORS: true },
            jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
        };

        html2pdf().set(opt).from(element).save();
    });
});

async function loadCertificateData(rollNumber) {
    try {
        const response = await fetch(`${API_BASE_URL}/generate-no-due/${rollNumber}`);
        const data = await response.json();

        if (response.ok) {
            populateCertificate(data);
        } else {
            showError(data.message || 'Unable to generate certificate.');
        }
    } catch (error) {
        console.error('Error:', error);
        showError('System error while generating certificate.');
    }
}

function populateCertificate(data) {
    document.getElementById('certCollegeName').textContent = data.collegeName;
    document.getElementById('certStudentName').textContent = data.studentName;
    document.getElementById('certRollNumber').textContent = data.rollNumber;
    document.getElementById('certTotalFee').textContent = '₹ ' + data.totalFee.toLocaleString('en-IN', { minimumFractionDigits: 2 });
    document.getElementById('certTotalPaid').textContent = '₹ ' + data.totalPaid.toLocaleString('en-IN', { minimumFractionDigits: 2 });
    document.getElementById('certDueAmount').textContent = '₹ ' + data.dueAmount.toLocaleString('en-IN', { minimumFractionDigits: 2 });
    document.getElementById('certStatement').textContent = data.statement;
    document.getElementById('certIdValue').textContent = data.certificateId;

    // Format Date
    const issueDate = new Date(data.issueDate);
    document.getElementById('certIssueDate').textContent = issueDate.toLocaleDateString('en-IN', {
        day: '2-digit',
        month: 'long',
        year: 'numeric'
    });

    // Populate Transactions Table
    const tbody = document.getElementById('certTransactionBody');
    tbody.innerHTML = '';

    if (data.transactions && data.transactions.length > 0) {
        data.transactions.forEach(txn => {
            const row = document.createElement('tr');
            const date = new Date(txn.paymentDate).toLocaleDateString();
            row.innerHTML = `
                <td>${date}</td>
                <td><code>${txn.transactionId}</code></td>
                <td>₹ ${txn.amount.toFixed(2)}</td>
            `;
            tbody.appendChild(row);
        });
    } else {
        tbody.innerHTML = '<tr><td colspan="3" class="text-center">No transactions found</td></tr>';
    }

    // Update QR Code Placeholder
    const qrPlaceholder = document.getElementById('qrCodePlaceholder');
    qrPlaceholder.innerHTML = `
        <strong>Certificate ID:</strong> ${data.certificateId}<br>
        <strong>Student:</strong> ${data.rollNumber}<br>
        <strong>Status:</strong> VERIFIED
    `;
}

function showError(message) {
    const overlay = document.getElementById('errorOverlay');
    const msgEl = document.getElementById('certErrorMessage');
    msgEl.textContent = message;
    overlay.classList.remove('d-none');
    overlay.classList.add('d-flex');
}
