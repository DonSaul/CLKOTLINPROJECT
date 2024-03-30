import React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import { Link } from "react-router-dom";
import { ROLES } from "../helpers/constants";
import { useAuth } from "../helpers/userContext";
import UserAvatar from "./avatar/UserAvatar";
import AssignmentIndIcon from "@mui/icons-material/AssignmentInd";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import LogoutIcon from "@mui/icons-material/Logout";
import LoginIcon from "@mui/icons-material/Login";
import HowToRegIcon from "@mui/icons-material/HowToReg";
import BusinessCenterIcon from "@mui/icons-material/BusinessCenter";
import NotificationsIcon from "@mui/icons-material/Notifications";
import ChatIcon from "@mui/icons-material/Chat";
import { useState, useEffect } from "react";
import HomeIcon from "@mui/icons-material/Home";
import { paths } from "../router/paths";
import PostAddIcon from "@mui/icons-material/PostAdd";
import GroupAddIcon from "@mui/icons-material/GroupAdd";
import { Search } from "@mui/icons-material";
import DoneAllIcon from "@mui/icons-material/DoneAll";

import { useNotificationData } from "../hooks/notifications/useNotificationByEmailInterval";
import Badge from "@mui/material/Badge"; // Import Badge component

export default function MainToolbar() {
  const { logout, getUserRole, isLoggedIn, getUserEmail } = useAuth();
  const notifications = useNotificationData(getUserEmail()); // Fetch notifications data
  const [unreadNotificationsCount, setUnreadNotificationsCount] = useState(0);

  useEffect(() => {
    if (notifications) {
      const count = notifications.filter(
        (notification) => !notification.read
      ).length;
      setUnreadNotificationsCount(count);
    }
  }, [notifications]);

  const [value, setValue] = useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  const tabStyles = {
    color: "white",
    fontWeight: "bold",
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar
          sx={{
            justifyContent: "center",
            //color:'white' ,
            backgroundColor: "#122670",
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
              width: "100%",
              display: "flex",
              //justifyContent: 'center',
              "& .MuiTab-label": {
                //color: 'red',
              },
            }}
          >
            {isLoggedIn()
              ? [
                  <Tab
                    key="home"
                    icon={<HomeIcon />}
                    label="Home"
                    component={Link}
                    to="/"
                  />,

                  getUserRole() === ROLES.ADMIN && (
                    <Tab
                      key="createUser"
                      label="Create User"
                      icon={<GroupAddIcon></GroupAddIcon>}
                      component={Link}
                      to={paths.createUser}
                    />
                  ),
                  getUserRole() === ROLES.MANAGER && [
                    <Tab
                      key="vacancies"
                      icon={<BusinessCenterIcon />}
                      label="Vacancies"
                      component={Link}
                      to={paths.vacancies}
                    />,
                    <Tab
                      key="createVacancy"
                      label="Create Vacancy"
                      icon={<PostAddIcon></PostAddIcon>}
                      component={Link}
                      to={paths.createVacancy}
                    />,
                    <Tab
                      key="managerSearchPage"
                      label="Search Candidates"
                      icon={<Search></Search>}
                      component={Link}
                      to={paths.managerSearchPage}
                    />,
                    <Tab
                      key="managerVacanciesPage"
                      label="My Vacancies"
                      icon={<DoneAllIcon></DoneAllIcon>}
                      component={Link}
                      to={paths.managerVacanciesPage}
                    />,
                  ],
                  getUserRole() === ROLES.CANDIDATE && [
                    <Tab
                      key="vacancies"
                      icon={<BusinessCenterIcon />}
                      label="Vacancies"
                      component={Link}
                      to={paths.vacancies}
                    />,
                    <Tab
                      key="myCV"
                      icon={<AssignmentIndIcon />}
                      label="My CV"
                      component={Link}
                      to={paths.cv}
                    />,
                  ],
                  <Tab
                    key="messaging"
                    label="Messaging"
                    icon={<ChatIcon />}
                    component={Link}
                    to={paths.messaging}
                  />,
                  <Tab
                    key="notifications"
                    icon={
                      <Badge
                        badgeContent={unreadNotificationsCount}
                        color="secondary"
                      >
                        <NotificationsIcon />
                      </Badge>
                    }
                    label="Notifications"
                    //labelPlacement="bottom"
                    component={Link}
                    to={paths.notifications}
                    value={paths.notifications}
                  />,
                  <Tab
                    key="myProfile"
                    label="My profile"
                    icon={<UserAvatar></UserAvatar>}
                    component={Link}
                    to={paths.profile}
                  />,
                  <Tab
                    key="logout"
                    label="Logout"
                    icon={<LogoutIcon />}
                    onClick={logout}
                    component={Link}
                    to={paths.login}
                  />,
                ]
              : [
                  <Tab
                    key="login"
                    icon={<LoginIcon />}
                    label="Login"
                    component={Link}
                    to={paths.login}
                  />,
                  <Tab
                    key="register"
                    icon={<HowToRegIcon />}
                    label="Register"
                    component={Link}
                    to={paths.register}
                  />,
                ]}
          </Tabs>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
