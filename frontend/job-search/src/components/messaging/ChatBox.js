import React, { useState, useEffect, useRef } from 'react';
import MessageBubble from './MessageBubble';
import ChatInput from './ChatInput';
import { Box } from '@mui/material';
import { useAuth } from '../../helpers/userContext';

const ChatBox = (data) => {
    const { getUserEmail,getUserFirstName,getUserLastName } = useAuth();

    const messages = [
        { date: '2024-03-05', userName: 'John', firstName: 'John', lastName: 'Doe', message: 'Hello there!' },
        { date: '2024-03-05', userName:  getUserEmail() , firstName: getUserFirstName(), lastName: getUserLastName(), message: 'Hi John! How are you?' },
        { date: '2024-03-05T12:30:00', userName: 'John', firstName: 'John', lastName: 'Doe', message: 'Whats up' },
        { date: '2024-03-05T12:30:00', userName:  getUserEmail() , firstName: getUserFirstName(), lastName: getUserLastName(), message: 'ABD' },
        { date: '2024-03-05T12:55:12', userName: 'John', firstName: 'John', lastName: 'Doe', message: 'Im back to left' },
    ];

    const [chatMessages, setChatMessages] = useState(messages)
    const chatContainerRef = useRef();

    useEffect(() => {
        chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
    }, [chatMessages]);

    const handleSendMessage = (newMessage) => {
        const updatedMessages = [...chatMessages, { message: newMessage, date: new Date(), userName: getUserEmail(),firstName: getUserFirstName(), lastName: getUserLastName() }];
        setChatMessages(updatedMessages);
        console.log("message", newMessage);
        console.log("messages",updatedMessages);
    };

    return (
        <>
            <Box
                ref={chatContainerRef}
                sx={{
                    maxHeight: '500px',
                    overflowY: 'auto',
                }}
            >
                {chatMessages?.map((message, index) => (
                    <MessageBubble key={index} data={message} />
                ))}
            </Box>


            <ChatInput onSendMessage={handleSendMessage}></ChatInput>
        </>
    );
};

export default ChatBox;
