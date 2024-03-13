import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { TextField, Button, Typography, Link as MuiLink } from '@mui/material'; // Import Button from Material-UI
import useChangePassword from '../hooks/useChangePassword';

const ChangePassword = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const token = searchParams.get('token');
  const { newPassword, setNewPassword, message, resetPassword } = useChangePassword();

  const handleSubmit = (e) => {
    e.preventDefault();
    resetPassword(token);
  };

  return (
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
  );
};

export default ChangePassword;