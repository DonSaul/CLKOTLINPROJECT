// NotificationCountContext.js
import React, { createContext, useContext, useState } from 'react';

const NotificationCountContext = createContext();

export const NotificationCountProvider = ({ children }) => {
  const [unreadNotificationsCount, setUnreadNotificationsCount] = useState(0);

  const increaseNotificationCount = () => {
    setUnreadNotificationsCount(prevCount => prevCount + 1);
  };

  const decreaseNotificationCount = () => {
    setUnreadNotificationsCount(prevCount => prevCount - 1);
  };

  const resetNotificationCount = () => {
    setUnreadNotificationsCount(0);
  };

  return (
    <NotificationCountContext.Provider
      value={{
        unreadNotificationsCount,
        increaseNotificationCount,
        decreaseNotificationCount,
        resetNotificationCount,
      }}
    >
      {children}
    </NotificationCountContext.Provider>
  );
};

export const useNotificationCount = () => {
  return useContext(NotificationCountContext);
};
