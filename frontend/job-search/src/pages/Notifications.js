import React from 'react';
import CardContainer from '../components/CardContainer';
import NotificationItem from '../components/NotificationItem';
import { useNotificationData } from '../hooks/useNotifications';
const Notifications = () => {
  const notification = useNotificationData();

  console.log('Notifications received:', notification);

  if(!notification) {
    console.log('No notification');
    return null;
  }

  console.log('notification data', notification);

  return (
    <div>
      <CardContainer width='xs'>
        <NotificationItem notification={notification} />
      </CardContainer>
    </div>
  );
};

export default Notifications;
