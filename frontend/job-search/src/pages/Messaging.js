import React, { useState } from 'react';
import CardContainer from '../components/CardContainer';
import { Grid } from '@mui/material';
import { Box, ThemeProvider, createTheme } from '@mui/material';
import AlignItemsList from '../components/messaging/ConversationList';
import VirtualizedList from '../components/messaging/UserList';
import MessageBubble from '../components/messaging/MessageBubble';
import ChatBox from '../components/messaging/ChatBox';
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

    const messages = [
        { date: '2022-03-05', userName: 'John', message: 'Hello there!' },
        { date: '2022-03-05', userName: 'You', message: 'Hi John! How are you?' },
        { date: '2024-03-05T12:30:00', userName: 'You', message: 'Whats up' },
        { date: '2024-03-05T12:30:00', userName: 'You', message: 'ABD' },
        { date: '2022-03-05T12:55:12', userName: 'John', message: 'Im back to left' },
    ];


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
                                bgcolor: 'primary.main',
                                '&:hover': {
                                    bgcolor: 'primary.dark',
                                },
                            }}
                        >
                            <AlignItemsList></AlignItemsList>

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
                                bgcolor: '#4CAF50', 
                                '&:hover': {
                                    bgcolor: '#45A049', 
                                },
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