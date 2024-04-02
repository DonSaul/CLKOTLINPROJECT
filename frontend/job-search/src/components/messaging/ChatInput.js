import React, { useState, useEffect, useRef } from "react";
import { TextField } from "@mui/material";
import { Box } from "@mui/material";
import { Button } from "@mui/material";
const ChatInput = ({ onSendMessage }) => {
  const [message, setMessage] = useState("");

  const inputRef = useRef(null);

  const handleChange = (event) => {
    setMessage(event.target.value);
  };

  const handleSend = () => {
    if (message.trim() !== "") {
      onSendMessage(message);
      setMessage("");
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === "Enter") {
      handleSend();
    }
  };

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  });

  return (
    <>
      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          "& > :not(style)": { mr: 1 },
          position: "sticky",
          bottom: 0,
          backgroundColor: "#F0F8FF",
          zIndex: 1000,
          p: 2,
          borderTop: "1px solid #ddd",
        }}
      >
        <TextField
          value={message}
          onChange={handleChange}
          label="Type your message..."
          variant="outlined"
          fullWidth
          inputProps={{ maxLength: 100 }}
          onKeyDown={handleKeyDown}
          //autoFocus
          inputRef={inputRef}
        />
        <Button
          onClick={handleSend}
          variant="contained"
          color="primary"
          size="small"
          sx={{ flex: "none" }}
        >
          Send
        </Button>
      </Box>
    </>
  );
};

export default ChatInput;
