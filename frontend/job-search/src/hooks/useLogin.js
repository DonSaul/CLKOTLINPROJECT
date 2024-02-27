import { useMutation, useQuery } from 'react-query';
import { ENDPOINTS } from '../helpers/endpoints';
import { AUTH_TOKEN } from '../helpers/constants';

const login = async (credentials) => {
  const response = await fetch(ENDPOINTS.login, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(credentials),
  });

  if (!response.ok) {
    throw new Error('Login failed');
  }

  const data = await response.json();
  return data.token;
};

export const useLogin = () => {
  return useMutation(login, {
    onSuccess: (token) => {
      localStorage.setItem(AUTH_TOKEN, token);
      console.log('Login successful. Token:', token);
    },
    onError: (error) => {
      console.error('Login error:', error);
    },
  });
};

