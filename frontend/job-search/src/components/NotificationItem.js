import React from 'react';
import { Typography } from '@mui/material';

const NotificationItem = ({ notifications }) => {
  if (!notifications || notifications.length === 0) {
    return <Typography>No notifications found</Typography>;
  }
  //this is only for testing the component

  return (
    <div>
      {notifications.map(notification => (
        <div key={notification.id}>
          <Typography sx={{ fontSize: 14, textAlign: 'left' }}>Subject: {notification.subject}</Typography>
          <Typography sx={{ fontSize: 14, textAlign: 'left' }}>Type: {notification.type.type}</Typography>
          {notification.sender && (
            <Typography sx={{ fontSize: 14, textAlign: 'left' }}>
              Sender: {notification.sender}
            </Typography>
          )}
          <Typography sx={{ fontSize: 14, textAlign: 'left' }}>Content: {notification.content}</Typography>
          <Typography sx={{ fontSize: 14, textAlign: 'left' }}>Date: {new Date(...notification.sentDateTime).toLocaleString()}</Typography>
          <hr />
        </div>
      ))}
    </div>
  );
};

export default NotificationItem;
