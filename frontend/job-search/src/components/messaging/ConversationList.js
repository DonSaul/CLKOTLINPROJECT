import * as React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import Divider from '@mui/material/Divider';
import ListItemText from '@mui/material/ListItemText';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';

const ConversationsList = ({ conversations, onSelectConversation }) => {
  const handleConversationClick = (index) => {
    console.log("the index conversation is ",index);
    onSelectConversation(index);
  };

  return (
    <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
      {conversations.map((conversation, index) => (
        <React.Fragment key={index}>
          <ListItem
            alignItems="flex-start"
            button
            onClick={() => handleConversationClick(index)}
            sx={{
              ':hover': {
                bgcolor: '#f0f0f0', 
              },
            }}
          >
            <ListItemAvatar>
              <Avatar alt={`${conversation.firstName} ${conversation.lastName}`} src={`/static/images/avatar/${index + 1}.jpg`} />
            </ListItemAvatar>
            <ListItemText
              primary={conversation.topMessage}
              secondary={
                <React.Fragment>
                  <Typography
                    sx={{ display: 'inline' }}
                    component="span"
                    variant="body2"
                    color="text.primary"
                  >
                    {`${conversation.firstName} ${conversation.lastName}`}
                  </Typography>
                  {` — ${conversation.topMessage}`}
                </React.Fragment>
              }
            />
          </ListItem>
          {index < conversations.length - 1 && <Divider variant="inset" component="li" />}
        </React.Fragment>
      ))}
    </List>
  );
}

export default ConversationsList;
