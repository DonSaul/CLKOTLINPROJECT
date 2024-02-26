import React, { useState } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import Chip from '@mui/material/Chip';
import Stack from '@mui/material/Stack';
import FormControlLabel from '@mui/material/FormControlLabel';
import Typography from '@mui/material/Typography';
import { useRegister } from '../hooks/useRegister';

const RegisterForm = () => {

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('Candidate');
    const [termsAccepted, setTermsAccepted] = useState(false);

    const { mutate, isError, isSuccess } = useRegister();


    
    const handleRoleChange = (selectedRole) => {
        setRole(selectedRole);
      };
    
    const handleSubmit = async (e) => {
        e.preventDefault();    
        console.log('Form submitted:', { firstName, lastName, email, password, role, termsAccepted });


        const formData = {
          firstName,
          lastName,
          email,
          password,
          role,
          termsAccepted
        };
    
        try {
          await mutate(formData);
    
        } catch (error) {
    
   
        }



      };
    
    return (
      <form onSubmit={handleSubmit} style={{ maxWidth: '400px', margin: '0 auto' }}>

          <Typography variant="h5" style={{ textAlign: 'center', marginTop: '10px', fontWeight: 'bold'}}>
                Sign up to find a Job
          </Typography>  
          <TextField
            label="First Name"
            type="text"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            required
            fullWidth
            margin="normal"
          />
          
          <TextField
            label="Last Name"
            type="text"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            required
            fullWidth
            margin="normal"
          />
    
          <TextField
            label="Email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            fullWidth
            margin="normal"
          />
          
          <TextField
            label="Password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            fullWidth
            margin="normal"
          />
    
            <Stack direction="row" spacing={1} style={{marginBottom:'10px'}}>
              <Chip
                label="Candidate"
                clickable
                color={role === 'Candidate' ? 'primary' : 'default'}
                onClick={() => handleRoleChange('Candidate')}
              />
              
              {/* <Chip
                label="Manager"
                clickable
                color={role === 'Manager' ? 'primary' : 'default'}
                onClick={() => handleRoleChange('Manager')}
              /> */}
              
            </Stack>
              
            <FormControlLabel
            control={<Checkbox />}
            label={
              <Typography variant="subtitle2" style={{ fontSize: '14px' }}>
                I agree to the Terms of Service and Privacy Policy
              </Typography>
            }
            checked={termsAccepted}
            onChange={(e) => setTermsAccepted(e.target.checked)}
            style={{marginBottom: '10px'}}
          />
    
          <Button type="submit" variant="contained" color="primary" fullWidth style={{marginBottom: '10px'}} >
            <b>Sign up</b>
          </Button>         

          <Typography variant="subtitle1" style={{ fontSize: '12px', color: 'rgba(128, 128, 128, 0.7)', marginBottom: '10px'}}>
                Have an account?
          </Typography>
          
          <Button type="submit" variant="contained" color='inherit' fullWidth >
            <b>Sign in</b>
          </Button>  

        </form>
      );
    };
export default RegisterForm;    