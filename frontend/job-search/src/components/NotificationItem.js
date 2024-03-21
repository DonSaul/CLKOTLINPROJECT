import React from 'react';
import { Typography } from '@mui/material';

const NotificationItem = ({ notifications }) => {
  if (!notifications || notifications.length === 0) {
    return <Typography>No notifications found</Typography>;
  }

  return (
    <div>
      {notifications.map(notification => {
        // Create a Date object from the sentDateTime array
        let dateArray = notification.sentDateTime;
        const date = `${dateArray[0]}-${dateArray[1]}-${dateArray[2]} ${dateArray[3]}:${dateArray[4]}:${dateArray[5]} GMT-3`;

        return (
          <div key={notification.id}>
            <Typography sx={{ fontSize: 14, textAlign: 'left' }}>Subject: {notification.subject}</Typography>
            <Typography sx={{ fontSize: 14, textAlign: 'left' }}>Type: {notification.type.type}</Typography>
            {notification.sender && (
              <Typography sx={{ fontSize: 14, textAlign: 'left' }}>
                Sender: {notification.sender.email}
              </Typography>
            )}
            <Typography sx={{ fontSize: 14, textAlign: 'left' }}>Content: {notification.content}</Typography>
            <Typography sx={{ fontSize: 14, textAlign: 'left' }}>
              Date: {date}
            </Typography>
            <hr />
          </div>
        );
      })}
    </div>
  );
};

export default NotificationItem;