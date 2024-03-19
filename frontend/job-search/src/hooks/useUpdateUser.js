import { useEffect } from 'react';
import { ENDPOINTS } from '../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../helpers/constants';

export const useUpdateUser = (checkboxValue, userId) => {
  let token = localStorage.getItem(AUTH_TOKEN_NAME);
  useEffect(() => {
    const updateUser = async () => {
      try {
        const response = await fetch(ENDPOINTS.activateNotification + userId, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          },
          body: JSON.stringify({ checkboxValue }),
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

    updateUser();
  }, [checkboxValue]);
};