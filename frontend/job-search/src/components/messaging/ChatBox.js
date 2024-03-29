import React, { useState, useEffect, useRef, useLayoutEffect } from "react";
import MessageBubble from "./MessageBubble";
import ChatInput from "./ChatInput";
import { Box } from "@mui/material";
import { useAuth } from "../../helpers/userContext";
import { useSendMessage } from "../../hooks/messaging/useSendMessage";
import UserAvatar from "../avatar/UserAvatar";
import { Typography } from "@mui/material";
import { getRoleString } from "../../helpers/constants";
import { truncateText } from "../../helpers/funHelpers";
import LoadingSpinner from "../LoadingSpinner";
const ChatBox = ({
  data,
  user,
  userData,
  onSendMessage,
  isLoadingConversation,
}) => {
  const { getUserEmail, getUserFirstName, getUserLastName } = useAuth();

  const messages = data?.data || [];

  const [chatMessages, setChatMessages] = useState([]);
  const chatContainerRef = useRef();

  const { mutate: sendMessage } = useSendMessage();

  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useLayoutEffect(() => {
    scrollToBottom();
  }, [chatMessages]);

  useEffect(() => {
    if (data?.length > 0) {
      setChatMessages(data);
    } else {
      setChatMessages([]);
    }
  }, [data]);

  const handleSendMessage = (newMessage) => {
    let dataMessage = {
      message: newMessage,
      receiverUserName: user,
    };

    setChatMessages((prevMessages) => [
      ...prevMessages,
      {
        message: newMessage,
        date: new Date(),
        sender: {
          firstName: getUserFirstName(),
          lastName: getUserLastName(),
          email: getUserEmail(),
        },
      },
    ]);

    sendMessage(dataMessage);
  };

  return (
    <>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          height: "100%",
          bgcolor: "EFF0F3",
        }}
      >
        <Box
          sx={{
            display: "flex",
            flexDirection: "row",
            alignItems: "center",
            bgcolor: "#F0F8FF",
            p: 2,
            borderBottom: "1px solid #ddd",
          }}
        >
          {userData ? (
            <>
              <UserAvatar user={userData}></UserAvatar>
              <Box
                ml={2}
                sx={{
                  height: "100%",
                }}
              >
                <div>Conversation with</div>
                <Typography variant="subtitle1">
                  <span style={{ fontWeight: "bold" }}>
                    <i>{getRoleString(userData.roleId)}</i>
                  </span>{" "}
                  {`${truncateText(
                    `${userData.firstName} ${userData.lastName}`,
                    25
                  )}  (${userData.email})`}
                </Typography>
              </Box>
            </>
          ) : (
            <Typography variant="subtitle1" color="textSecondary">
              No conversation selected
            </Typography>
          )}
        </Box>
        <Box
          ref={chatContainerRef}
          sx={{
            maxHeight: "500px",
            overflowY: "auto",
            position: "relative",
            bgcolor: "#E5E4E2",
            height: "100%",
          }}
        >
          {userData && (
            <>
              {isLoadingConversation ? (
                <LoadingSpinner></LoadingSpinner>
              ) : (
                <>
                  {chatMessages?.length > 0 ? (
                    <>
                      {chatMessages.map((message, index) => (
                        <React.Fragment key={index}>
                          <MessageBubble key={index} data={message} />
                          <div ref={messagesEndRef} />
                        </React.Fragment>
                      ))}
                    </>
                  ) : (
                    <p>No messages</p>
                  )}
                </>
              )}
            </>
          )}
        </Box>
        <Box
          sx={
            {
              //position: 'sticky',
              //bottom: 0,
              //backgroundColor: 'white',
              //bgcolor: 'yellow'
            }
          }
        >
          {userData && <ChatInput onSendMessage={handleSendMessage} />}
        </Box>
      </Box>
    </>
  );
};

export default ChatBox;
