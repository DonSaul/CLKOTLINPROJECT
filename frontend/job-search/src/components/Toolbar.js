import { Search } from '@mui/icons-material';
import AssignmentIndIcon from '@mui/icons-material/AssignmentInd';
import BusinessCenterIcon from '@mui/icons-material/BusinessCenter';
import ChatIcon from '@mui/icons-material/Chat';
import GroupAddIcon from '@mui/icons-material/GroupAdd';
import HomeIcon from '@mui/icons-material/Home';
import HowToRegIcon from '@mui/icons-material/HowToReg';
import LoginIcon from '@mui/icons-material/Login';
import LogoutIcon from '@mui/icons-material/Logout';
import NotificationsIcon from '@mui/icons-material/Notifications';
import PostAddIcon from '@mui/icons-material/PostAdd';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import Toolbar from '@mui/material/Toolbar';
import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { ROLES } from '../helpers/constants';
import { useAuth } from '../helpers/userContext';
import { paths } from '../router/paths';
import UserAvatar from './UserAvatar';
export default function ButtonAppBar() {
  const { logout, getUserRole, isLoggedIn } = useAuth();


  const location = useLocation();
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
          backgroundColor: '#122670'
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
              <Tab key="home" icon={<HomeIcon />} label="Home" component={Link} to="/" />,

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
              <Tab key="notifications" label="Notifications" icon={<NotificationsIcon />} component={Link} to={paths.notifications} />,
              <Tab key="myProfile" label="My profile" icon={<UserAvatar></UserAvatar>} component={Link} to={paths.profile} />,
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
