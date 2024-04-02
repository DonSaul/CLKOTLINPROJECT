import { useCallback } from "react";
import { AUTH_TOKEN_NAME } from "../../helpers/constants";
import { ENDPOINTS } from "../../helpers/endpoints";

const useNotificationDeletion = () => {
    let token = localStorage.getItem(AUTH_TOKEN_NAME);
  const deleteNotification = useCallback(async (id) => {
    try {
      const response = await fetch(`${ENDPOINTS.notificationGeneral}/${id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.ok) {
        return true; 
      } else {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
      }
    } catch (error) {
      throw new Error("Failed to delete notification. Please try again later.");
    }
  }, []);

  return { deleteNotification };
};

export default useNotificationDeletion;