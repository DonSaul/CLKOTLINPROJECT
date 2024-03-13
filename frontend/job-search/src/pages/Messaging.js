import React, { useState } from 'react';
import CardContainer from '../components/CardContainer';
import { Grid } from '@mui/material';
import { Box, ThemeProvider, createTheme } from '@mui/material';
import ChatBox from '../components/messaging/ChatBox';
import { useAuth } from '../helpers/userContext';
import ConversationsList from '../components/messaging/ConversationList';
import UserList from '../components/messaging/UserList';
import useGetUsers from '../hooks/messaging/useGetUsers';
import { useEffect } from 'react';
import useGetConversations from '../hooks/messaging/useGetConversations';
import useGetCurrentConversation from '../hooks/messaging/useGetCurrentConversation';
import {CircularProgress} from '@mui/material';
const theme = createTheme({
    palette: {
        primary: {
            main: '#007FFF',
            dark: '#0066CC',
        },
        secondary: {
            main: '#FF5733',
            dark: '#E64900',
        },
    },
});


const Messaging = () => {

    const [selectedConversation, setSelectedConversation] = useState();
    const { getUserEmail,getUserFirstName,getUserLastName } = useAuth();
    const [selectedUserChat,setSelectedUserChat]= useState();
  
    
    const {data:userList} = useGetUsers();
    const {data:userConversations}= useGetConversations();
    const {data:currentConversation,refetch:fetchConversation}=useGetCurrentConversation(selectedConversation);



    const [filteredUserList, setFilteredUserList] = useState([]);

    useEffect(() => {
      if (userList) {
        const filteredList = userList.filter(user => user.email !== getUserEmail());
        setFilteredUserList(filteredList);
      }
    }, [userList]);

  

    useEffect( () =>{

        if (selectedConversation){
            console.log("YES ",currentConversation)
            fetchConversation();
        }
        
        

    },[selectedConversation,fetchConversation])


    return (

        <ThemeProvider theme={theme}>

            <CardContainer>
                <Grid container spacing={3}>
                    <Grid item xs={3}>
                        <Box
                            sx={{
                                width: '100%',
                                height: 600, 
                                borderRadius: 1,
                                bgcolor: '#F4F4F4',
                                //'&:hover': {
                                 //   bgcolor: 'primary.dark',
                               // },
                            }}
                        >
                             
                            <ConversationsList conversations={userConversations} onSelectConversation={setSelectedConversation} onSetUserData={setSelectedUserChat}>

                            </ConversationsList>

                        </Box>
                    </Grid>
                    <Grid item xs={6}>
                        <Box
                            sx={{
                                width: '100%',
                                height: 600, 
                                borderRadius: 1,
                                bgcolor: '#FAF9F6',
                                '&:hover': {
                                    //bgcolor: '#FFFDD0',
                                },
                            }}
                        >
                            <ChatBox data={currentConversation} user={selectedConversation} userData={selectedUserChat}/>

                           


                        </Box>
                    </Grid>
                    <Grid item xs={3}>
                        <Box
                            sx={{
                                width: '100%',
                                height: 600, 
                                borderRadius: 1,
                                bgcolor: '#F4F4F4', 
                               // '&:hover': {
                               //     bgcolor: '#45A049', 
                               // },
                            }}
                        >
                            <UserList users={filteredUserList} onSelectUser={setSelectedConversation}  onSetUserData={setSelectedUserChat}></UserList>

                        </Box>
                    </Grid>


                </Grid>
            </CardContainer>
        </ThemeProvider>
    );
};

export default Messaging;