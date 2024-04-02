import { useState, useEffect } from "react";
import { ENDPOINTS } from "../../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../../helpers/constants";

export const useNotificationTypes = (email) => {
  const [notificationTypes, setNotificationTypes] = useState([]);
  const [loadingType, setLoadingType] = useState(true);
  const [vacancyChecked, setVacancyChecked] = useState(false);
  const [invitationChecked, setInvitationChecked] = useState(false);
  const [messageChecked, setMessageChecked] = useState(false);
  const token = localStorage.getItem(AUTH_TOKEN_NAME);

  useEffect(() => {
    const fetchNotificationTypes = async (email) => {
      try {
        if (!email) return; // Exit early if email is falsy

        const response = await fetch(
          `${ENDPOINTS.notificationTypes}/${email}`,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!response.ok) {
          throw new Error("Failed to fetch notification types");
        }

        const data = await response.json();
        setNotificationTypes(data);
        setLoadingType(false);

        // Initialize state for notification types
        const vacancyType = data.find((type) => type.name === "Vacancy");
        const invitationType = data.find((type) => type.name === "Invitation");
        const messageType = data.find((type) => type.name === "Message");
        if (vacancyType) setVacancyChecked(true);
        if (invitationType) setInvitationChecked(true);
        if (messageType) setMessageChecked(true);
      } catch (error) {
        console.error("Error fetching notification types:", error);
        setLoadingType(false);
      }
    };

    if (email) {
      fetchNotificationTypes(email);
    }
  }, [email, token]);

  return {
    notificationTypes,
    loadingType,
    vacancyChecked,
    invitationChecked,
    messageChecked,
  };
};
