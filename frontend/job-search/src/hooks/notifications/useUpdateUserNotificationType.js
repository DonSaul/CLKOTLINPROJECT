import { ENDPOINTS } from '../../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../../helpers/constants';

export const useUpdateUserNotificationType = () => {
    return async (email, checkboxValue, notificationType) => { // Return a function
        try {
            const token = localStorage.getItem(AUTH_TOKEN_NAME);
            if (!token) {
                throw new Error('Authentication token not found');
            }

            const url = `${ENDPOINTS.updateNotificationType}/${email}/${notificationType}/${checkboxValue}`;

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
};