import React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from 'react-router-dom';
import { ROLES } from '../helpers/constants';
import { useAuth } from '../helpers/userContext';
import {  toast } from 'react-toastify';
import AccountCircle from '@mui/icons-material/AccountCircle';

export default function ButtonAppBar() {
  const { logout, getUserRole, isLoggedIn } = useAuth();

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar sx={{ justifyContent: 'center' }}>
          {/* 
          <IconButton size="large" edge="start" color="inherit" aria-label="menu" sx={{ mr: 2 }}>
            <MenuIcon />
          </IconButton>
        */}
          <Button color="inherit" component={Link} to="/vacancies">
            Vacancies
          </Button>
          {isLoggedIn ? (
            <>
              {getUserRole() === ROLES.ADMIN && (
                <Button color="inherit" component={Link} to="/admin/users/new">
                  Create user
                </Button>
              )}

              {getUserRole() === ROLES.MANAGER && (
                <Button color="inherit" component={Link} to="/vacancies/new">
                  Create vacancy
                </Button>
              )}

              {getUserRole() === ROLES.CANDIDATE && (
                <Button color="inherit" component={Link} to="/cv">
                  My CV
                </Button>
              )}

              <Button color="inherit" onClick={logout} component={Link} to="/login">
                Logout
              </Button>
            </>
          ) : (
            <>
              <Button color="inherit" component={Link} to="/login">
                Login
              </Button>
              <Button color="inherit" component={Link} to="/register">
                Register
              </Button>
            </>
          )}

        </Toolbar>
      </AppBar>
    </Box>
  );
}
