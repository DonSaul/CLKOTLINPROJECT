import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from 'react-router-dom';
import { logout,isLoggedIn } from '../api/login';

export default function ButtonAppBar() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Button color="inherit" component={Link} to="/cv">
            CV
          </Button>
          <Button color="inherit" component={Link} to="/vacancies">
            Vacancies
          </Button>
          {isLoggedIn() ? (
            <Button color="inherit" onClick={logout} component={Link} to="/login">
              Logout
            </Button>
          ) : (
            <Button color="inherit" component={Link} to="/login">
              Login
            </Button>
          )}
          <Button color="inherit" component={Link} to="/register">
            Register
          </Button>
          
        
        </Toolbar>
      </AppBar>
    </Box>
  );
}
