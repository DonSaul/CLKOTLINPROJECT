import React from "react";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import UserAvatar from "../avatar/UserAvatar";
import { useAuth } from "../../helpers/userContext";
const MessageBubble = ({ data }) => {
  const {
    date,
    message,
    sender: { firstName, lastName, email },
  } = data;
  const { getUserEmail } = useAuth();

  const formattedDate = new Date(date).toLocaleString("en-US", {
    hour: "numeric",
    minute: "numeric",
    second: "numeric",
    month: "short",
    day: "numeric",
    year: "numeric",
  });

  const isCurrentUser = () => {
    return email === getUserEmail();
  };

  return (
    <Box
      display="flex"
      flexDirection={isCurrentUser() ? "row-reverse" : "row"}
      alignItems="flex-end"
      mb={2}
    >
      <UserAvatar user={{ firstName, lastName, email }}></UserAvatar>
      <Paper
        elevation={3}
        sx={{
          padding: 2,
          maxWidth: "70%",
          minWidth: "30%",
          bgcolor: isCurrentUser() ? "white" : "#A7C7E7",
          color: isCurrentUser() ? "black" : "black",
        }}
      >
        <Typography variant="caption" color="textSecondary" mb={1}>
          {formattedDate}
        </Typography>
        <Typography variant="body1" sx={{ overflowWrap: "break-word" }}>
          {message}
        </Typography>
      </Paper>
    </Box>
  );
};

export default MessageBubble;
