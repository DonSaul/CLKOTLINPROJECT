// Register.js
import React from 'react';

const Register = () => {

const [firstName, setFirstName] = useState('');
const [lastName, setLastName] = useState('');
const [email, setEmail] = useState('');
const [password, setPassword] = useState('');
const [role, setRole] = useState('Candidate');
const [termsAccepted, setTermsAccepted] = useState(false);

const handleSubmit = (event) => {
    event.preventDefault();    
};  

  return (
     <form onSubmit={handleSubmit}>
            <label>
                First Name:
                <input type="text" value={firstName} onChange={e => setFirstName(e.target.value)} required />
            </label>
            <label>
                Last Name:
                <input type="text" value={lastName} onChange={e => setLastName(e.target.value)} required />
            </label>
            <label>
                Email:
                <input type="email" value={email} onChange={e => setEmail(e.target.value)} required />
            </label>
            <label>
                Password:
                <input type="password" value={password} onChange={e => setPassword(e.target.value)} required />
            </label>
            <label>
                Rol:
                <input type="radio" value="Candidate" checked={role === 'Candidate'} onChange={e => setRole(e.target.value)} /> Candidate
                <input type="radio" value="Manager" checked={role === 'Manager'} onChange={e => setRole(e.target.value)} /> Manager
            </label>
            <label>
                <input type="checkbox" checked={termsAccepted} onChange={e => setTermsAccepted(e.target.checked)} /> I agree to the Terms of Service and Privacy Policy
            </label>
            <button type="submit">Sign up</button>
        </form>
  );
};

export default Register;