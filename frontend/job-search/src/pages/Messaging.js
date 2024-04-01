import React, { useState } from "react";
import CardContainer from "../components/CardContainer";
import { Grid } from "@mui/material";
import { Box, ThemeProvider, createTheme } from "@mui/material";
import ChatBox from "../components/messaging/ChatBox";
import { useAuth } from "../helpers/userContext";
import ConversationsList from "../components/messaging/ConversationList";
import UserList from "../components/messaging/UserList";
import useGetUsers from "../hooks/messaging/useGetUsers";
import { useEffect } from "react";
import useGetConversations from "../hooks/messaging/useGetConversations";
import useGetCurrentConversation from "../hooks/messaging/useGetCurrentConversation";
import { useParams } from "react-router-dom";
const theme = createTheme({
  palette: {
    primary: {
      main: "#007FFF",
      dark: "#0066CC",
    },
    secondary: {
      main: "#FF5733",
      dark: "#E64900",
    },
  },
});

const Messaging = () => {
  const { id } = useParams();
  const [selectedConversation, setSelectedConversation] = useState();
  const { getUserEmail } = useAuth();
  const [selectedUserChat, setSelectedUserChat] = useState();

  const {
    data: userList,
    isSuccess: isSuccessUserList,
    isLoading: isLoadingUserList,
  } = useGetUsers();
  const { data: userConversations, refetch: fetchAllConversations } =
    useGetConversations();
  const {
    data: currentConversation,
    refetch: fetchConversation,
    isLoading: isLoadingConversation,
  } = useGetCurrentConversation(selectedConversation);

  const [filteredUserList, setFilteredUserList] = useState([]);

  useEffect(() => {
    if (userList) {
      const filteredList = userList.filter(
        (user) => user.email !== getUserEmail()
      );
      setFilteredUserList(filteredList);
    }
  }, [userList]);

  useEffect(() => {
    if (selectedConversation) {
      fetchConversation();
    }
  }, [selectedConversation, fetchConversation]);

  useEffect(() => {
    if (id && filteredUserList) {
      let user = filteredUserList.find((user) => user.id == id);
      if (user) {
        setSelectedConversation(user.email);
        setSelectedUserChat(user);
      }
    }
  }, [filteredUserList, id, isSuccessUserList]);

  useEffect(() => {
    let interval;
    if (selectedConversation) {
      interval = setInterval(() => {
        fetchAllConversations();
      }, 1000);
    }
    return () => clearInterval(interval);
  }, [fetchAllConversations, selectedConversation]);

  useEffect(() => {
    let interval;
    if (selectedConversation) {
      interval = setInterval(() => {
        fetchConversation();
      }, 1000);
    }
    return () => clearInterval(interval);
  }, [fetchConversation, selectedConversation]);

  const [boxHeight, setBoxHeight] = useState(600);
  const [gridContainerHeight, setGridContainerHeight] = useState(600);

  return (
    <ThemeProvider theme={theme}>
      <CardContainer>
        <Grid
          container
          spacing={3}
          sx={{
            height: gridContainerHeight,
          }}
        >
          <Grid item xs={3}>
            <Box
              sx={{
                width: "100%",
                height: boxHeight,
                borderRadius: 1,
                bgcolor: "#F4F4F4",
                //'&:hover': {
                //   bgcolor: 'primary.dark',
                // },
              }}
            >
              <ConversationsList
                conversations={userConversations}
                onSelectConversation={setSelectedConversation}
                onSetUserData={setSelectedUserChat}
                selectedConversation={selectedConversation}
              ></ConversationsList>
            </Box>
          </Grid>
          <Grid item xs={6}>
            <Box
              sx={{
                width: "100%",
                height: boxHeight,
                borderRadius: 1,
                bgcolor: "#FAF9F6",
                "&:hover": {
                  //bgcolor: '#FFFDD0',
                },
              }}
            >
              <ChatBox
                data={currentConversation}
                user={selectedConversation}
                userData={selectedUserChat}
                isLoadingConversation={isLoadingConversation}
              />
            </Box>
          </Grid>
          <Grid item xs={3}>
            <Box
              sx={{
                width: "100%",
                height: boxHeight,
                borderRadius: 1,
                bgcolor: "#F4F4F4",
                // '&:hover': {
                //     bgcolor: '#45A049',
                // },
              }}
            >
              <UserList
                users={filteredUserList}
                onSelectUser={setSelectedConversation}
                onSetUserData={setSelectedUserChat}
                isLoadingUserList={isLoadingUserList}
              ></UserList>
            </Box>
          </Grid>
        </Grid>
      </CardContainer>
    </ThemeProvider>
  );
};

export default Messaging;
