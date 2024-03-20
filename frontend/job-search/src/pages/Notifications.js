import React, { useState, useEffect } from 'react';
import { Checkbox, FormControlLabel, CircularProgress } from '@mui/material';
import CardContainer from '../components/CardContainer';
import NotificationItem from '../components/NotificationItem';
import { useAuth } from '../helpers/userContext';
import { useNotificationData } from '../hooks/notifications/useNotifications';
import { useNotificationUpdater } from '../hooks/notifications/useNotificationUpdater';
import { useNotificationActivated } from '../hooks/notifications/useGetCurrentOnNotification';

const Notifications = () => {
  const { user } = useAuth();
  const notifications = useNotificationData(user?.email);
  const { notificationActivated, loading } = useNotificationActivated(user?.email);
  const handleCheckboxChange = useNotificationUpdater(user?.email);

  

  // Ensure notificationActivated is not null before using it
  const [initialCheckboxValue, setInitialCheckboxValue] = useState(false);
  const [vacancyChecked, setVacancyChecked] = useState(false);
  const [invitationChecked, setInvitationChecked] = useState(false);
  const [messageChecked, setMessageChecked] = useState(false);

  const handleVacancyCheckboxChange = (event) => {
    const newValue = event.target.checked;
    setVacancyChecked(newValue);
    handleCheckboxChange(newValue, "vacancy");
  };
  
  const handleInvitationCheckboxChange = (event) => {
    const newValue = event.target.checked;
    setInvitationChecked(newValue);
    handleCheckboxChange(newValue, "invitation");
  };
  
  const handleMessageCheckboxChange = (event) => {
    const newValue = event.target.checked;
    setMessageChecked(newValue);
    handleCheckboxChange(newValue, "message");
  };

  useEffect(() => {
    if (!loading && notificationActivated !== null) {
      setInitialCheckboxValue(notificationActivated);
      setVacancyChecked(false);
      setInvitationChecked(false);
      setMessageChecked(false);
    }
  }, [notificationActivated, loading]);

  // Handle loading state
  if (loading) {
    return <CircularProgress />;
  }

  return (
    <div>
      <CardContainer width='xs'>
        <FormControlLabel
          control={<Checkbox color="primary" checked={initialCheckboxValue} onChange={(event) => {
            const newValue = event.target.checked;
            setInitialCheckboxValue(newValue); // Update the checkbox state
            handleCheckboxChange(newValue); // Update the notification status
          }} />}
          label={initialCheckboxValue ? "Deactivate Notifications" : "Activate Notifications"}
        />
      </CardContainer>
      {initialCheckboxValue && (
        <CardContainer width='xs'>
          <FormControlLabel
  control={<Checkbox color="primary" checked={vacancyChecked} onChange={handleVacancyCheckboxChange} />}
  label="Vacancies"
/>
<FormControlLabel
  control={<Checkbox color="primary" checked={invitationChecked} onChange={handleInvitationCheckboxChange} />}
  label="Invitations"
/>
<FormControlLabel
  control={<Checkbox color="primary" checked={messageChecked} onChange={handleMessageCheckboxChange} />}
  label="Messages"
/>
        </CardContainer>
      )}
      {initialCheckboxValue ? (
        <CardContainer width='xs'>
          {notifications.length > 0 ? (
            <NotificationItem notifications={notifications} />
          ) : (
            <p>No notifications found</p>
          )}
        </CardContainer>
      ) : (
        <div>
          <CardContainer width='xs'>
            <p>You need to activate the notifications</p>
          </CardContainer>
        </div>
      )}
    </div>
  );
};

export default Notifications;