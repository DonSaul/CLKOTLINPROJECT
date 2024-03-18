import { useState, useEffect } from 'react';
import { useQuery } from 'react-query';
import { ENDPOINTS } from '../helpers/endpoints';

export const useNotificationData = () => {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    const fetchNotificationFromDatabase = async () => {
      try {
        const response = await fetch(ENDPOINTS.notification);
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

    fetchNotificationFromDatabase();
  }, []);

  return notifications;
};
