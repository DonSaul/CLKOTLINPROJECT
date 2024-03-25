import * as React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import CheckBoxIcon from '@mui/icons-material/CheckBox';
import UserAvatar from '../UserAvatar';
import { Typography } from '@mui/material';
import { truncateText } from '../../helpers/funHelpers';

export default function UserList({users,onSelectUser,onSetUserData}) {
  const handleUserSelect = (value) => () => {
    //console.log("im in the list",users)
    //console.log(`User ${value?.email} clicked`);
    //console.log("user data in list",value);
    
    onSelectUser(value?.email);
    onSetUserData(value);

  };

  return (
    <List dense sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>

      <Typography>
      Users
      </Typography>
      
      {users && users.map((value) => {
        const labelId = `icon-list-label-${value.id}`;
        return (
          <ListItem key={value.id} disablePadding>
            <ListItemButton onClick={handleUserSelect(value)}>
              
              <UserAvatar user={value}></UserAvatar>

              <ListItemText id={labelId} primary={truncateText(`${value.firstName} ${value.lastName}`,20)} />
            </ListItemButton>
          </ListItem>
        );
      })}
    </List>
  );
}
