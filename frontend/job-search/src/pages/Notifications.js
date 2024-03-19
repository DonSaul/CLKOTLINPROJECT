import React, { useState, useEffect } from 'react';
import CardContainer from '../components/CardContainer';
import { FormControlLabel, Checkbox } from '@mui/material';
import NotificationItem from '../components/NotificationItem';
import { useCheckboxField } from '../hooks/useCheckboxField';
import { useAuth } from '../helpers/userContext';
import { useNotificationData } from '../hooks/useNotifications'; // Import the updated useNotificationData hook
import { useUpdateUser } from '../hooks/useUpdateUser'; // Import the useUpdateUser hook

const Notifications = () => {
  const [checkboxValue, handleCheckboxChange] = useCheckboxField(false);
  const { user } = useAuth(); // Get the user object from the AuthContext
  const notifications = useNotificationData(user?.email); // Pass the user ID to the useNotificationData hook
  useUpdateUser(user?.email, checkboxValue); // Pass the checkboxValue and user ID to the useUpdateUser hook

  console.log('Notifications received:', user?.email);
  console.log('USERRRRRRs:', user);
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
          <NotificationItem notifications={notifications} />
        </CardContainer>
      ) : (
        <div>
          <CardContainer width='xs'>
            {notifications.length > 0 ? (
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