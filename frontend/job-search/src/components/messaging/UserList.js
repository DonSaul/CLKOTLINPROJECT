import * as React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import CheckBoxIcon from '@mui/icons-material/CheckBox';

export default function IconList() {
  const handleItemClick = (value) => () => {
 
    console.log(`Item ${value + 1} clicked`);
  };

  return (
    <List dense sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
      {[0, 1, 2, 3].map((value) => {
        const labelId = `icon-list-label-${value}`;
        return (
          <ListItem key={value} disablePadding>
            <ListItemButton onClick={handleItemClick(value)}>
              <Avatar
                alt={`Avatar n°${value + 1}`}
                src={`/static/images/avatar/${value + 1}.jpg`}
              />
              <ListItemText id={labelId} primary={` User ${value + 1}`} />
            </ListItemButton>
          </ListItem>
        );
      })}
    </List>
  );
}
