import { useState } from "react";
import { useUpdateUser } from "./useUpdateUser";

export const useNotificationUpdater = (userEmail) => {
  const updateUserNotificationStatus = useUpdateUser();

  const handleCheckboxChange = async (newValue) => {
    try {
      console.log(newValue);
      console.log(userEmail);
      await updateUserNotificationStatus(userEmail, newValue);
    } catch (error) {
      console.error("Error updating notification status:", error);
    }
  };

  return handleCheckboxChange;
};
