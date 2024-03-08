import React, { useState } from 'react';
import CardContainer from '../components/CardContainer';
import { Grid } from '@mui/material';
import { Box, ThemeProvider, createTheme } from '@mui/material';
import AlignItemsList from '../components/messaging/ConversationList';
import VirtualizedList from '../components/messaging/UserList';
import MessageBubble from '../components/messaging/MessageBubble';
import ChatBox from '../components/messaging/ChatBox';
import { useAuth } from '../helpers/userContext';
import ConversationsList from '../components/messaging/ConversationList';



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

    const [selectedConversation, setSelectedConversation] = useState(null);
    const { getUserEmail,getUserFirstName,getUserLastName } = useAuth();
    const handleSelectConversation = (index) => {
        setSelectedConversation(index);
       console.log("selected conversatioin:",index);
    };


    const messages = [
        { date: '2024-03-05', userName: 'John', firstName: 'John', lastName: 'Doe', message: 'Hello there!' },
        { date: '2024-03-05', userName:  getUserEmail() , firstName: getUserFirstName(), lastName: getUserLastName(), message: 'Hi John! How are you?' },
        { date: '2024-03-05T12:30:00', userName: 'John', firstName: 'John', lastName: 'Doe', message: 'Whats up' },
        { date: '2024-03-05T12:30:00', userName:  getUserEmail() , firstName: getUserFirstName(), lastName: getUserLastName(), message: 'ABD' },
        { date: '2024-03-05T12:55:12', userName: 'John', firstName: 'John', lastName: 'Doe', message: 'Im back to left' },
    ];
    const conversations= [
        { date: '2022-03-05', userName: 'John', topMessage: 'Hello there!' ,firstName: 'John', lastName: 'Doe'},
        { date: '2022-03-05', userName: 'Laura', topMessage: 'hey!' ,firstName: 'Laura', lastName: 'Bue'},

        { date: '2022-03-05', userName: 'Zaul', topMessage: 'job for you' ,firstName: 'Zaul', lastName: 'Xolo'},


    ]


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
                            <ConversationsList conversations={conversations} onSelectConversation={handleSelectConversation}></ConversationsList>

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
                            <ChatBox data={messages}/>

                           


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
                            <VirtualizedList></VirtualizedList>

                        </Box>
                    </Grid>





                </Grid>
            </CardContainer>
        </ThemeProvider>
    );
};

export default Messaging;