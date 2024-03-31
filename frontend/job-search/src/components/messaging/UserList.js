import * as React from "react";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import UserAvatar from "../avatar/UserAvatar";
import { Typography } from "@mui/material";
import { truncateText } from "../../helpers/funHelpers";
import { getRoleString } from "../../helpers/constants";
import Box from "@mui/material/Box";
import { messagingPageHeight } from "./messagingHelper";

export default function UserList({ users, onSelectUser, onSetUserData }) {
  const handleUserSelect = (user) => () => {
    onSelectUser(user?.email);
    onSetUserData(user);
  };

  return (
    <Box sx={{ maxHeight: messagingPageHeight, overflowY: "auto" }}>
      <List
        dense
        sx={{ width: "100%", maxWidth: 360, bgcolor: "background.paper" }}
      >
        <Typography>Users</Typography>

        {users &&
          users.map((user) => {
            const labelId = `icon-list-label-${user.id}`;
            return (
              <ListItem key={user.id} disablePadding>
                <ListItemButton
                  sx={{ display: "flex", gap: "7px" }}
                  onClick={handleUserSelect(user)}
                >
                  <UserAvatar user={user} enableRoleBorder={true}></UserAvatar>

                  <ListItemText
                    id={labelId}
                    primary={truncateText(
                      `${user.firstName} ${user.lastName} `,
                      20
                    )}
                  />
                </ListItemButton>
              </ListItem>
            );
          })}
      </List>
    </Box>
  );
}
