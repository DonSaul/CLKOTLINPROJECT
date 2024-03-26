import React, { useState, useEffect } from 'react';
import { Checkbox, FormControlLabel, CircularProgress } from '@mui/material';
import CardContainer from '../components/CardContainer';
import NotificationItem from '../components/NotificationItem';
import { useAuth } from '../helpers/userContext';
import { useNotificationData } from '../hooks/notifications/useNotificationsByEmail';
import { useNotificationUpdater } from '../hooks/notifications/useNotificationUpdater';
import { useNotificationActivated } from '../hooks/notifications/useGetCurrentOnNotification';
import { useNotificationTypeUpdater } from '../hooks/notifications/useNotificationTypesUpdated';
import { useNotificationTypes } from '../hooks/notifications/useNotificationTypes';
import { Button, ButtonGroup } from '@mui/material';
import Badge from '@mui/material/Badge'; // Import Badge component
import NotificationsIcon from '@mui/icons-material/Notifications';
import MainToolbar from '../components/MainToolbar';

const Notifications = () => {
  const { user } = useAuth();
  const notifications = useNotificationData(user?.email);
  const { notificationActivated, loading } = useNotificationActivated(user?.email);
  const { notificationTypes, loadingType } = useNotificationTypes(user?.email);
  const handleCheckboxChange = useNotificationUpdater(user?.email);
  const handleCheckboxNotificationType = useNotificationTypeUpdater(user?.email);

  const [initialCheckboxValue, setInitialCheckboxValue] = useState(false);
  const [vacancyChecked, setVacancyChecked] = useState(false);
  const [invitationChecked, setInvitationChecked] = useState(false);
  const [messageChecked, setMessageChecked] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [readNotifications, setReadNotifications] = useState([]);
  const itemsPerPage = 4;

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleVacancyCheckboxChange = (event) => {
    const newValue = event.target.checked;
    setVacancyChecked(newValue);
    handleCheckboxNotificationType(newValue, 1);
  };

  const handleInvitationCheckboxChange = (event) => {
    const newValue = event.target.checked;
    setInvitationChecked(newValue);
    handleCheckboxNotificationType(newValue, 2);
  };

  const handleMessageCheckboxChange = (event) => {
    const newValue = event.target.checked;
    setMessageChecked(newValue);
    handleCheckboxNotificationType(newValue, 3);
  };

  useEffect(() => {
    if (!loading && notificationActivated !== null) {
      setInitialCheckboxValue(notificationActivated);
      const vacancyType = notificationTypes.find(type => type.id === 1);
      const invitationType = notificationTypes.find(type => type.id === 2);
      const messageType = notificationTypes.find(type => type.id === 3);
      if (vacancyType) setVacancyChecked(true);
      if (invitationType) setInvitationChecked(true);
      if (messageType) setMessageChecked(true);
      if (notifications.length > 0) {
        const readNotificationIds = notifications.filter(notification => notification.read).map(notification => notification.id);
        setReadNotifications(readNotificationIds);
      }
    }
  }, [notificationActivated, loading, notificationTypes, notifications]);

  if (loading || loadingType) {
    return <CircularProgress />;
  }

  return (
    <div>
      <CardContainer width='xs'>
        <FormControlLabel
          control={<Checkbox color="primary" checked={initialCheckboxValue} onChange={(event) => {
            const newValue = event.target.checked;
            setInitialCheckboxValue(newValue);
            handleCheckboxChange(newValue);
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
        <CardContainer width='sm'>
          {notifications && notifications.length > 0 ? (
            notifications.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage).map(notification => (
              <NotificationItem
                key={notification.id}
                notification={notification}
                onNotificationRead={() => setReadNotifications([...readNotifications, notification.id])}
                isRead={readNotifications.includes(notification.id)}
              />
            ))
          ) : (
            <p>No notifications found</p>
          )}
          <ButtonGroup>
            <Button
              variant="contained"
              color="primary"
              onClick={() => handlePageChange(currentPage - 1)}
              disabled={currentPage === 1}
            >
              Previous
            </Button>
            <Button style={{ color: 'black' }}>{currentPage}</Button>
            <Button style={{ color: 'black' }}>of</Button>
            <Button style={{ color: 'black' }}>{Math.ceil((notifications ? notifications.length : 0) / itemsPerPage)}</Button>
            <Button
              variant="contained"
              color="primary"
              onClick={() => handlePageChange(currentPage + 1)}
              disabled={(notifications ? notifications.length : 0) <= currentPage * itemsPerPage}
            >
              Next
            </Button>
          </ButtonGroup>
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