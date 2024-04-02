import { useUpdateUserNotificationType } from "./useUpdateUserNotificationType";

export const useNotificationTypeUpdater = (userEmail) => {
  const updateUserNotificationStatus = useUpdateUserNotificationType();

  const handleCheckboxNotificationtype = async (newValue, notificationType) => {
    try {
      await updateUserNotificationStatus(userEmail, newValue, notificationType);
    } catch (error) {
      console.error("Error updating notification status:", error);
    }
  };

  return handleCheckboxNotificationtype;
};
