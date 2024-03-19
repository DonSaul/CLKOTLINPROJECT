import { useEffect } from 'react';
import { ENDPOINTS } from '../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../helpers/constants';

export const useUpdateUser = (email, checkboxValue) => {
  useEffect(() => {
    const updateUser = async () => {
      try {
        const token = localStorage.getItem(AUTH_TOKEN_NAME);
        if (!token) {
          throw new Error('Authentication token not found');
        }

        const url = `${ENDPOINTS.activateNotification}/${email}/${checkboxValue}`;

        const response = await fetch(url, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          }
        });

        if (!response.ok) {
          throw new Error('Failed to update user');
        }

        const data = await response.json();
        console.log('User updated:', data);
      } catch (error) {
        console.error('Error updating user:', error);
      }
    };

    if (email) {
      updateUser();
    }
  }, [email, checkboxValue]);
};