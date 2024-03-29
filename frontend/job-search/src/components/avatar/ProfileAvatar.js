import React, { useState } from "react";
import { avatarSizeProfile } from "./avatarConstants";
import UserAvatar from "./UserAvatar";
import { Box } from "@mui/material";
import { avatarBorderStyle } from "./avatarConstants";
const ProfileAvatar = ({ user }) => {
  return (
    <>
      <Box
        sx={{
          width: 50,
          height: 50,
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          padding: "25px",
        }}
      >
        <UserAvatar
          user={user}
          avatarSize={avatarSizeProfile}
          borderStyle={avatarBorderStyle}
        />
      </Box>
    </>
  );
};

export default ProfileAvatar;
