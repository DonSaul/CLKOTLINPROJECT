import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { TextField, Button, Typography, Link as MuiLink, Container } from '@mui/material'; // Import Container from Material-UI
import useChangePassword from '../hooks/useChangePassword';
import CardContainer from '../components/CardContainer';

const ChangePassword = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const token = searchParams.get('token');
  const { newPassword, setNewPassword, message, resetPassword } = useChangePassword();
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordsMatch, setPasswordsMatch] = useState(true);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (newPassword === confirmPassword) {
      resetPassword(token);
    } else {
      setPasswordsMatch(false);
    }
  };

  return (
    <CardContainer width="xs"> {/* Wrap the content in a Container */}
      <div>
        <h2>Reset Password</h2>
        <form onSubmit={handleSubmit} style={{ maxWidth: '300px', margin: '0 auto' }}>
          <label htmlFor="newPassword">New Password:</label>
          <TextField
            type="password"
            id="newPassword"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
            fullWidth
            margin="normal"
          />
          <label htmlFor="confirmPassword">Confirm New Password:</label> {/* Add a label for confirming the new password */}
          <TextField
            type="password"
            id="confirmPassword"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
            fullWidth
            margin="normal"
            error={!passwordsMatch} // Show error if passwords don't match
            helperText={!passwordsMatch && "Passwords do not match"} // Helper text for password mismatch
          />
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            style={{ marginTop: '10px' }}
          >
            Reset Password
          </Button>
        </form>
        {message && <Typography variant="body1" style={{ marginTop: '10px' }}>{message}</Typography>}
        <Typography variant="body2" style={{ marginTop: '10px' }}>
          Remember your password? <MuiLink component={Link} to="/login">Login</MuiLink> {/* Use MuiLink for Material-UI link styling */}
        </Typography>
      </div>
    </CardContainer>
  );
};

export default ChangePassword;