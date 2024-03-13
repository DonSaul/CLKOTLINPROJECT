import React from 'react';
import { Link, useLocation  } from 'react-router-dom';
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
          <input
            type="password"
            id="newPassword"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />
          <button type="submit">Reset Password</button>
        </form>
        {message && <p>{message}</p>}
        <p>
          Remember your password? <Link to="/login">Login</Link>
        </p>
      </div>
    );
  };
  
  export default ChangePassword;