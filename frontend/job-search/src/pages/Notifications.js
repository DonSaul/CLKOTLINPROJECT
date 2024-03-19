import React, { useState, useEffect } from 'react';
import CardContainer from '../components/CardContainer';
import { FormControlLabel, Checkbox } from '@mui/material';
import NotificationItem from '../components/NotificationItem';
import { useNotificationData } from '../hooks/useNotifications';
import { useCheckboxField } from '../hooks/useCheckboxField';
import { useUpdateUser } from '../hooks/useUpdateUser';
import { useGetCurrentUser } from '../hooks/useGetCurrentUser';


const Notifications = () => {
  const notification = useNotificationData();
  const [checkboxValue, handleCheckboxChange] = useCheckboxField(false);
  const {data : currentUser} = useGetCurrentUser();
  const userId = currentUser?.id;

  useUpdateUser(checkboxValue, userId);

  console.log('Notifications received:', notification);

  if (!notification) {
    console.log('No notification');
    return null;
  }

  console.log('Notification data:', notification);

  return (
    <div>
      <CardContainer width='xs'>
        <FormControlLabel
          control={<Checkbox color="primary" checked={checkboxValue} onChange={handleCheckboxChange} />}
          label={checkboxValue ? "Deactivate Notifications" : "Activate Notifications"}
        />
      </CardContainer>
      {checkboxValue ? (
      <CardContainer width='xs'>
        <NotificationItem notifications={notification} />
      </CardContainer>
    ) : (
      <div>
        <CardContainer width='xs'>
          {notification ? (
            <p>You need to activate the notifications</p>
          ) : (
            <p>No notifications found</p>
          )}          
        </CardContainer>
      </div>
    )}
    </div>
  );
};

export default Notifications;