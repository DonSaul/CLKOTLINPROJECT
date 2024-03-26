import React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from 'react-router-dom';
import { ROLES } from '../helpers/constants';
import { useAuth } from '../helpers/userContext';
import { toast } from 'react-toastify';
import AccountCircle from '@mui/icons-material/AccountCircle';
import Avatar from '@mui/material/Avatar';
import Stack from '@mui/material/Stack';
import { deepOrange, deepPurple } from '@mui/material/colors';
import UserAvatar from './UserAvatar';
import AssignmentIndIcon from '@mui/icons-material/AssignmentInd';
import { Typography } from '@mui/material';
import { Tooltip } from '@mui/material';
import { Grid } from '@mui/material';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import PhoneIcon from '@mui/icons-material/Phone';
import FavoriteIcon from '@mui/icons-material/Favorite';
import PersonPinIcon from '@mui/icons-material/PersonPin';
import LogoutIcon from '@mui/icons-material/Logout';
import LoginIcon from '@mui/icons-material/Login';
import HowToRegIcon from '@mui/icons-material/HowToReg';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import BusinessCenterIcon from '@mui/icons-material/BusinessCenter';
import NotificationsIcon from '@mui/icons-material/Notifications';
import ChatIcon from '@mui/icons-material/Chat';
import { useLocation } from 'react-router-dom';
import { useState,useEffect } from 'react';
import HomeIcon from '@mui/icons-material/Home';
import NotificationImportantIcon from '@mui/icons-material/NotificationImportant';
import { paths } from '../router/paths';
import PostAddIcon from '@mui/icons-material/PostAdd';
import GroupAddIcon from '@mui/icons-material/GroupAdd';
import { Search } from '@mui/icons-material';
export default function MainToolbar() {
  const { logout, getUserRole, isLoggedIn } = useAuth();


  const location =useLocation();
  const [value, setValue] = useState(0);

  const handleChange = (event, newValue) => {
    //console.log("valuetab",newValue);
    setValue(newValue);
  };
  const tabStyles = {
    color: 'white',
    fontWeight: 'bold',
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar sx={{ 
          justifyContent: 'center',
          //color:'white' ,
          backgroundColor:'#122670'
        }}
        >

          <Tabs
            // orientation="horizontal" 
             variant="standard"
            value={value}
            onChange={handleChange}
            centered
            textColor="inherit"
            aria-label="icon label tabs example"
            sx={{
              width: '100%',
              display: 'flex',
              //justifyContent: 'center',
              '& .MuiTab-label': {
                //color: 'red', 
              },
            }}
          >
            {isLoggedIn ? [
              <Tab key="home" icon={<HomeIcon />} label="Home"  component={Link} to="/" />,
              
              getUserRole() === ROLES.ADMIN && (
                <Tab key="createUser" label="Create User" icon={<GroupAddIcon></GroupAddIcon>} component={Link} to={paths.createUser} />
              ),
              getUserRole() === ROLES.MANAGER && [
                <Tab key="vacancies" icon={<BusinessCenterIcon />} label="Vacancies" component={Link} to={paths.vacancies} />,
                <Tab key="createVacancy" label="Create Vacancy" icon={<PostAddIcon></PostAddIcon>} component={Link} to={paths.createVacancy} />,
                <Tab key="managerSearchPage" label="Search Candidates" icon={<Search></Search>} component={Link} to={paths.managerSearchPage} />
            ],
              getUserRole() === ROLES.CANDIDATE && [
                <Tab key="vacancies" icon={<BusinessCenterIcon />} label="Vacancies" component={Link} to={paths.vacancies} />,
                <Tab key="myCV" icon={<AssignmentIndIcon />} label="My CV" component={Link} to={paths.cv} />,
              ],
              <Tab key="messaging" label="Messaging" icon={<ChatIcon />} component={Link} to={paths.messaging} />,
              <Tab key="notifications" label="Notifications" icon={<NotificationsIcon />} component={Link} to={paths.notifications}/>,
              <Tab key="myProfile" label="My profile" icon={<UserAvatar></UserAvatar>} component={Link} to={paths.profile}/>,
              <Tab key="logout" label="Logout" icon={<LogoutIcon />} onClick={logout} component={Link} to={paths.login} />,
            ] :
            
            [
              <Tab key="login" icon={<LoginIcon />} label="Login" component={Link} to={paths.login} />,
              <Tab key="register" icon={<HowToRegIcon />} label="Register" component={Link} to={paths.register} />,
            ]}
          </Tabs>
          
          
        </Toolbar>
      </AppBar>
    </Box>
  );
}
