document.getElementById('resetPasswordForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const token = document.getElementById('token').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const messageDiv = document.getElementById('message');

    if (newPassword !== confirmPassword) {
        messageDiv.textContent = 'Passwords do not match!';
        return;
    }

    try {
        const response = await fetch('/reset-password/reset', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                token: token,
                newPassword: newPassword
            })
        });

        if (response.ok) {
            messageDiv.textContent = 'Password reset successfully!';
            messageDiv.style.color = 'green';
        } else {
            const error = await response.json();
            messageDiv.textContent = error.message || 'An error occurred!';
        }
    } catch (error) {
        messageDiv.textContent = 'An error occurred while resetting the password.';
    }
});

