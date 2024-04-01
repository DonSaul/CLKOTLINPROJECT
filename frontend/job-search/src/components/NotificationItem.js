import React, { useState } from "react";

import { ENDPOINTS } from "../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../helpers/constants";
import { Typography, Checkbox, Button } from "@mui/material";

const NotificationItem = ({ notification, onNotificationRead, isRead }) => {
  const [markingAsRead, setMarkingAsRead] = useState(false);
  let token = localStorage.getItem(AUTH_TOKEN_NAME);

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

      onNotificationRead(notification.id); // Update globally
    } catch (error) {
      console.error("Error marking notification as read:", error);
    } finally {
      setMarkingAsRead(false);
    }
  };

  const date = new Date(notification.sentDateTime);

  date.setHours(date.getHours() - 3);
  // Extract date components
  const year = date.getFullYear();
  const month = ("0" + (date.getMonth() + 1)).slice(-2); // Adding 1 because getMonth() returns 0-based index
  const day = ("0" + date.getDate()).slice(-2);
  const hours = ("0" + date.getHours()).slice(-2);
  const minutes = ("0" + date.getMinutes()).slice(-2);
  const seconds = ("0" + date.getSeconds()).slice(-2);

  // Construct the formatted date string
  const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds} GMT-3`;

  // Optionally, convert to GMT-3 timezone string

  const formattedDateWithOffset = new Date(date.getTime());
  const dateString =
    formattedDateWithOffset.toISOString().slice(0, 19).replace("T", " ") +
    " GMT-3";

  return (
    <div style={{ opacity: isRead ? 0.5 : 1 }}>
      {" "}
      {/* Apply different opacity based on whether notification is read */}
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
      <hr />
    </div>
  );
};

export default NotificationItem;
