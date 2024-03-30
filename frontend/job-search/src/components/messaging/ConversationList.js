import * as React from "react";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import Divider from "@mui/material/Divider";
import ListItemText from "@mui/material/ListItemText";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import Typography from "@mui/material/Typography";
import { useState, useEffect, useRef } from "react";
import { useAuth } from "../../helpers/userContext";
import UserAvatar from "../avatar/UserAvatar";

const ConversationsList = ({
  conversations,
  onSelectConversation,
  onSetUserData,
  selectedConversation,
}) => {
  const { getUserEmail } = useAuth();
  const [formattedConversations, setFormattedConversations] = useState();
  const initialSelectionHandled = useRef(false);

  const handleConversationClick = (index) => {
    const selectedConversation = formattedConversations[index];
    if (selectedConversation?.email) {
      onSelectConversation(selectedConversation.email);
      onSetUserData(selectedConversation);
      initialSelectionHandled.current = true;
    } else {
      console.error("Can't select this conversation");
    }
  };

  const formatConversation = (conversation) => {
    const { id, lastMessage, user1, user2 } = conversation;

    let firstName, lastName, email, user;

    if (user1?.email === getUserEmail()) {
      firstName = user2.firstName;
      lastName = user2.lastName;
      email = user2.email;
      user = user2;
    } else {
      firstName = user1?.firstName;
      lastName = user1?.lastName;
      email = user1?.email;
      user = user1;
    }

    const sender = lastMessage?.sender;

    return {
      id,
      topMessage: lastMessage?.message,
      fullName: `${firstName} ${lastName}`,
      firstName: firstName,
      lastName: lastName,
      email: email,
      user: user,
      senderName:
        sender?.email === getUserEmail()
          ? sender?.firstName
          : sender?.firstName,
      senderLastName:
        sender?.email === getUserEmail() ? sender?.firstName : sender?.lastName,
      roleId: user.role.id,
      senderEmail: sender?.email,
    };
  };

  useEffect(() => {
    if (conversations) {
      console.log("conversations", conversations);
      setFormattedConversations(conversations.map(formatConversation));
    }
  }, [conversations]);

  useEffect(() => {
    if (conversations) {
      console.log("conversations", conversations);
    }
  }, [conversations]);

  return (
    <List sx={{ width: "100%", maxWidth: 360, bgcolor: "background.paper" }}>
      <Typography>Conversations</Typography>
      {formattedConversations &&
        formattedConversations.map((conversation, index) => (
          <React.Fragment key={index}>
            <ListItem
              alignItems="flex-start"
              button
              onClick={() => handleConversationClick(index)}
              sx={{
                ":hover": { bgcolor: "#f0f0f0" },
                ...(selectedConversation === conversation.email && {
                  bgcolor: "#e1f5fe",
                }),
              }}
            >
              <ListItemAvatar>
                <UserAvatar user={conversation.user}></UserAvatar>
              </ListItemAvatar>
              <div
                style={{
                  overflow: "hidden",
                  textOverflow: "ellipsis",
                  width: "auto",
                }}
              >
                <Typography
                  sx={{ display: "inline" }}
                  component="span"
                  variant="body2"
                  color="text.primary"
                >
                  {conversation.senderEmail === getUserEmail() ? (
                    <b>You</b>
                  ) : (
                    <b>
                      {conversation.senderName} {conversation.senderLastName}
                    </b>
                  )}
                </Typography>
                {" — "}
                <div>
                  <Typography
                    noWrap
                    sx={{
                      display: "inline",
                      // maxWidth: "10px",
                      // textOverflow: "ellipsis",
                      // overflow: "hidden",
                      //  whiteSpace: "nowrap",
                    }}
                  >
                    {conversation.topMessage}
                  </Typography>
                </div>
              </div>
            </ListItem>
            {index < conversations.length - 1 && (
              <Divider variant="fullwidth" component="li" />
            )}
          </React.Fragment>
        ))}
      {!formattedConversations && <>No users Available</>}
    </List>
  );
};

export default ConversationsList;
