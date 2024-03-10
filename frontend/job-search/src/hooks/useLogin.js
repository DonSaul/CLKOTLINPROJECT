import { useMutation } from 'react-query';
import { ENDPOINTS } from '../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../helpers/constants';
import { useAuth } from '../helpers/userContext';

import { toast } from 'react-toastify';

const login = async (credentials) => {
  const response = await fetch(ENDPOINTS.login, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(credentials),
  });

  if (!response.ok) {
    throw new Error('Login failed');
  }

  const userData = await response.json();
  return userData;
};

export const useLogin = () => {
  const { login: setAuthUser } = useAuth();

  return useMutation(login, {
    onSuccess: (userData) => {
      toast.success('Login successful!'); 
      localStorage.setItem(AUTH_TOKEN_NAME, userData.token);
      setAuthUser(userData); // Use the login function from the AuthContext
    },
    onError: (error) => {
      console.error('Login error:', error);
      toast.error('Invalid username or password'); 
    },
  });
};