import { useEffect } from 'react';
import { ENDPOINTS } from '../../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../../helpers/constants';

export const useUpdateCurrentProfile = () => {
    return async (updatedProfileData) => { 
        try {
            const token = localStorage.getItem(AUTH_TOKEN_NAME);
            console.log(token)
            if (!token) {
                throw new Error('Authentication token not found');
            }

            console.log("updated",updatedProfileData.id)
            const response = await fetch(`${ENDPOINTS.updateProfile}/${updatedProfileData.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
                body: JSON.stringify(updatedProfileData)
            });

            if (!response.ok) {
                throw new Error('Failed to update profile');
            }

            const data = await response.json();
            console.log('Profile updated:', data);
        } catch (error) {
            console.error('Error updating profile:', error);
        }
    };
};