import React from 'react';
import { Typography } from '@mui/material';

const NotificationItem = ({ notifications }) => {
  if (!notifications || notifications.length === 0) {
    return <Typography>No notifications found</Typography>;
  }

  return (
    <div>
      {notifications.map(notification => {
        const date = new Date(notification.sentDateTime);

        date.setHours(date.getHours() - 3);
        // Extract date components
        const year = date.getFullYear();
        const month = ('0' + (date.getMonth() + 1)).slice(-2); // Adding 1 because getMonth() returns 0-based index
        const day = ('0' + date.getDate()).slice(-2);
        const hours = ('0' + date.getHours()).slice(-2);
        const minutes = ('0' + date.getMinutes()).slice(-2);
        const seconds = ('0' + date.getSeconds()).slice(-2);
        
        // Construct the formatted date string
        const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds} GMT-3`;
        
        // Optionally, convert to GMT-3 timezone string
       
        const formattedDateWithOffset = new Date(date.getTime());
const dateString = formattedDateWithOffset.toISOString().slice(0, 19).replace('T', ' ') + ' GMT-3';

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
              Date: {dateString}
            </Typography>
            <hr />
          </div>
        );
      })}
    </div>
  );
};

export default NotificationItem;