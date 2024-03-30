import * as React from "react";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import Divider from "@mui/material/Divider";
import ListItemText from "@mui/material/ListItemText";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import Typography from "@mui/material/Typography";
import { useState } from "react";
import { useAuth } from "../../helpers/userContext";
import UserAvatar from "../avatar/UserAvatar";
import { useEffect } from "react";

const ConversationsList = ({
  conversations,
  onSelectConversation,
  onSetUserData,
}) => {
  const { getUserEmail } = useAuth();
  const [formattedConversations, setFormattedConversations] = useState();
  const [selectedConversationIndex, setSelectedConversationIndex] =
    useState(null);

  const handleConversationClick = (index) => {
    setSelectedConversationIndex(index);
    const selectedConversation = formattedConversations[index];
    if (selectedConversation?.email) {
      onSelectConversation(selectedConversation.email);
      onSetUserData(selectedConversation);
    } else {
      console.error("Can't select this conversation");
    }
  };

  const truncateText = (text, maxLength) => {
    if (text && text.length > maxLength) {
      return text.substring(0, maxLength) + "...";
    }
    return text;
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
      setFormattedConversations(conversations.map(formatConversation));
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
                ...(selectedConversationIndex === index && {
                  bgcolor: "#e1f5fe",
                }),
              }}
            >
              <ListItemAvatar>
                <UserAvatar user={conversation.user}></UserAvatar>
              </ListItemAvatar>
              <div>
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
                      {truncateText(
                        `${conversation.senderName} ${conversation.senderLastName}`,
                        15
                      )}
                    </b>
                  )}
                </Typography>
                {" — "}
                <Typography
                  sx={{
                    display: "inline",
                    maxWidth: "10px",
                    textOverflow: "ellipsis",
                    overflow: "hidden",
                    whiteSpace: "nowrap",
                  }}
                >
                  {truncateText(conversation.topMessage, 19)}
                </Typography>
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
