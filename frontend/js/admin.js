// Get form elements
const loginForm = document.getElementById('loginForm');
const errorMessage = document.getElementById('errorMessage');

// Handle login
loginForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    // Simple hardcoded authentication
    if (username === 'admin' && password === 'admin') {
        // Store login status
        sessionStorage.setItem('adminLoggedIn', 'true');
        // Redirect to dashboard
        window.location.href = 'dashboard.html';
    } else {
        // Show error
        errorMessage.style.display = 'block';
        // Clear password field
        document.getElementById('password').value = '';
    }
});
