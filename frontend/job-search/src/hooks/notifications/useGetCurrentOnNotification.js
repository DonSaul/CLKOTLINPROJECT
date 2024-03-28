import { useState, useEffect } from 'react';
import { ENDPOINTS } from '../../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../../helpers/constants';

export const useNotificationActivated = (email) => {
  const [notificationActivated, setNotificationActivated] = useState(false);
  const [loading, setLoading] = useState(true); // Initially set to true to indicate loading
  let token = localStorage.getItem(AUTH_TOKEN_NAME);

  useEffect(() => {
    const fetchNotificationActivated = async (email) => {
      try {
        if (!email) return; // Exit early if email is falsy
        console.log("Before fetching with email:", email);
        const response = await fetch(`${ENDPOINTS.notificationIsActive}/${email}`, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          },
        });

        if (!response.ok) {
          throw new Error('Failed to fetch notification activation status');
        }

        const data = await response.json();
        setNotificationActivated(data);
        setLoading(false); // Set loading to false after data is fetched
      } catch (error) {
        console.error('Error fetching notification activation status:', error);
        setNotificationActivated(false); // Set default value in case of error
        setLoading(false); // Set loading to false in case of error
      }
    };
    if (email) {
      fetchNotificationActivated(email);
    }
  }, [email]);

  return { notificationActivated, loading }; // Return both values
};