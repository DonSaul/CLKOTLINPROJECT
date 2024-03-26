import { useState, useEffect } from 'react';
import { ENDPOINTS } from '../../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../../helpers/constants';

export const useNotificationData = (email) => {
  const [notifications, setNotifications] = useState([]);
  let token = localStorage.getItem(AUTH_TOKEN_NAME);

  useEffect(() => {
    const fetchNotificationFromDatabase = async (email) => {
      try {
        const response = await fetch(`${ENDPOINTS.notification}/${email}`, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          },
        });
        if (!response.ok) {
          throw new Error('Failed to fetch notifications');
        }
        const data = await response.json();
        console.log('Notifications data:', data);
        setNotifications(data);
      } catch (error) {
        console.error('Error fetching notifications:', error);
      }
    };

    if (email) {
      fetchNotificationFromDatabase(email);
    }
  }, [email]);

  return notifications;
};