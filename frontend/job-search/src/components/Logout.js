import React, { useEffect } from 'react';
import { logout } from '../api/login';
import Button from '@mui/material/Button';


const Logout = () => {


    const handleLogout = () => {
        
        logout();
      };

  return (
    <div>
      <Button onClick={handleLogout} variant="contained" color="primary">
                Logout
        </Button>
    </div>
  );
};

export default Logout;