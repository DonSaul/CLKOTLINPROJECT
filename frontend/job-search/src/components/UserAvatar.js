import React from 'react';
import Avatar from '@mui/material/Avatar';
import { deepOrange } from '@mui/material/colors';
import { useAuth } from '../helpers/userContext';

const UserAvatar = () => {
  const { isLoggedIn, getUserEmail } = useAuth();


  return (
    <>
        { getUserEmail() && <Avatar sx={{ bgcolor: deepOrange[500] }}>{getUserEmail()}</Avatar>}
    </>
  );
};

export default UserAvatar;