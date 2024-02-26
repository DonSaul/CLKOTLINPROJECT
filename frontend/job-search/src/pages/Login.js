import { useState } from 'react';
import { TextField } from '@mui/material';
import { useEffect } from 'react';
import Button from '@mui/material/Button';
import CardContainer from '../components/CardContainer';
import {Typography} from '@mui/material';
import { Link } from 'react-router-dom';
import { useLogin } from '../hooks/useLogin';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = () => {
    console.log('Logging in with:', username, password);
  };



  return (
    <div>
      <CardContainer width='xs'>
      <h2>Login</h2>
      <form>
        <TextField
          label="Username"
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          fullWidth
          margin="normal"
        />
        <Button
          type="button"
          variant="contained"
          color="primary"
          onClick={handleLogin}
          //fullWidth
        >
          Login
        </Button>
      </form>

      <Typography variant="body2" sx={{ marginTop: 2 }}>
        Don't have an account? <Link to="/register"> Register</Link>
      </Typography>
    </CardContainer>





    </div>
  );
};

export default Login;