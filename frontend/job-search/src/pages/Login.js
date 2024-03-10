import { useState } from 'react';
import { TextField } from '@mui/material';
import { useEffect } from 'react';
import Button from '@mui/material/Button';
import CardContainer from '../components/CardContainer';
import {Typography} from '@mui/material';
import { Link } from 'react-router-dom';
import { useLogin } from '../hooks/useLogin';
import { useNavigate } from 'react-router-dom';
const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate=useNavigate()



  const { mutate, isError, isSuccess } = useLogin();

  useEffect(() => {
    if (isSuccess) {
     
      navigate('/vacancies');
    }
  }, [isSuccess, navigate]);

  const handleSubmit =async (e) => {
    e.preventDefault();

    console.log('Logging in with:', username, password);


    let formData={
      username,
      password
    }

    try {
      await mutate(formData);
   
    } catch (error) {

    }


  };



  return (
    <div>
      <CardContainer width='xs'>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>

        <TextField
          label="Username"
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          fullWidth
          margin="normal"
          required
        />
        <TextField
          label="Password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          fullWidth
          margin="normal"
          required
        />
        <Button
          type="submit"
          variant="contained"
          color="primary"
          //onClick={handleSubmit}
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