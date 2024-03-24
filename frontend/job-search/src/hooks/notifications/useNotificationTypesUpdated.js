import { useUpdateUserNotificationType } from './useUpdateUserNotificationType';

export const useNotificationTypeUpdater = (userEmail) => {
    const updateUserNotificationStatus = useUpdateUserNotificationType();

    const handleCheckboxNotificationtype = async (notificationType, newValue) => {
        try {
            console.log(newValue);
            console.log(userEmail);
            await updateUserNotificationStatus(userEmail, notificationType, newValue);
        } catch (error) {
            console.error('Error updating notification status:', error);
        }
    };

    return handleCheckboxNotificationtype;
};