
import React, { useState } from "react";
import { Typography, Button } from "@mui/material";
import { ENDPOINTS } from "../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../helpers/constants";

const NotificationItem = ({ notification, onNotificationRead, onDelete, isRead }) => {
  const [markingAsRead, setMarkingAsRead] = useState(false);
  const token = localStorage.getItem(AUTH_TOKEN_NAME);

  const handleMarkAsRead = async () => {
    setMarkingAsRead(true);
    try {
      const response = await fetch(
        `${ENDPOINTS.notificationRead}/${notification.id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Failed to mark notification as read");
      }

      
    } catch (error) {
      console.error("Error marking notification as read:", error);
    } finally {
      setMarkingAsRead(false);
    }
  };

  const handleDelete = async () => {
    try {
      const response = await fetch(
        `${ENDPOINTS.notificationGeneral}/${notification.id}`,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      
      if (!response.ok) {
        throw new Error("Failed to delete notification");
      }

      onDelete(notification.id); 
    } catch (error) {
      console.error("Error deleting notification:", error);
    }
  };

  const date = new Date(notification.sentDateTime);
  date.setHours(date.getHours() - 3);
  const formattedDateWithOffset = new Date(date.getTime());
  const dateString =
    formattedDateWithOffset.toISOString().slice(0, 19).replace("T", " ") +
    " GMT-3";

  return (
    <>
      <div style={{ opacity: isRead ? 0.5 : 1 }}>
        <Typography sx={{ fontSize: 14, textAlign: "left" }}>
          Subject: {notification.subject}
        </Typography>
        <Typography sx={{ fontSize: 14, textAlign: "left" }}>
          Type: {notification.type.type}
        </Typography>
        {notification.sender && (
          <Typography sx={{ fontSize: 14, textAlign: "left" }}>
            Sender: {notification.sender.email}
          </Typography>
        )}
        <Typography sx={{ fontSize: 14, textAlign: "left" }}>
          Content: {notification.content}
        </Typography>
        <Typography sx={{ fontSize: 14, textAlign: "left" }}>
          Date: {dateString}
        </Typography>
        <Button
          variant="contained"
          color="primary"
          disabled={markingAsRead}
          onClick={handleMarkAsRead}
          sx={{ marginTop: "8px" }}
        >
          Mark as Read
        </Button>
        <Button
          variant="contained"
          color="error"
          onClick={handleDelete}
          sx={{ marginTop: "8px", marginLeft: "8px" }}
        >
          Delete
        </Button>
        <hr />
      </div>
    </>
  );
};

export default NotificationItem;