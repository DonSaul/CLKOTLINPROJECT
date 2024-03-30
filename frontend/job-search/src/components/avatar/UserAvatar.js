import React, { useState } from "react";
import Avatar from "@mui/material/Avatar";
import { deepOrange } from "@mui/material/colors";
import { useAuth } from "../../helpers/userContext";
import { Tooltip } from "@mui/material";
import { avatarBoxShadow, avatarProfileTextSize } from "./avatarConstants";

const UserAvatar = ({ user, avatarSize, borderStyle }) => {
  const { getUserEmail, getUserFirstName, getUserLastName } = useAuth();
  const [defaultAvatarSize, setDefaultAvatarSize] = useState("35px");
  const [defaultTextSize, setDefaultTextSize] = useState("0.9rem");
  const [defaultBorder, setDefaultBorder] = useState("");
  const capitalizeFirstLetter = (str) => {
    return str?.charAt(0).toUpperCase();
  };

  const stringToColour = (str) => {
    if (!str) {
      console.log("no color for you");
      return "#FFA500";
    }
    let hash = 0;
    str.split("").forEach((char) => {
      hash = char.charCodeAt(0) + ((hash << 5) - hash);
    });
    let colour = "#";
    for (let i = 0; i < 3; i++) {
      const value = (hash >> (i * 8)) & 0xff;
      colour += value.toString(16).padStart(2, "0");
    }
    return colour;
  };

  const getUserData = () => {
    if (user) {
      return user;
    } else {
      return {
        email: getUserEmail(),
        firstName: getUserFirstName(),
        lastName: getUserLastName(),
      };
    }
  };

  const userData = getUserData();
  const hasName = userData?.firstName || userData?.lastName;

  const textSize = avatarSize ? avatarProfileTextSize : defaultTextSize;
  const boxShadow = borderStyle ? avatarBoxShadow : undefined;

  return (
    <>
      {userData && (
        <Tooltip
          title={
            userData
              ? `${userData.firstName} ${userData.lastName} (${userData.email})`
              : "No data"
          }
        >
          <Avatar
            sx={{
              bgcolor: stringToColour(userData?.email || userData?.email),
              height: avatarSize || defaultAvatarSize,
              width: avatarSize || defaultAvatarSize,
              border: borderStyle || defaultBorder,
              boxShadow: boxShadow,
            }}
          >
            <span style={{ fontSize: textSize }}>
              {hasName
                ? `${capitalizeFirstLetter(
                    userData.firstName
                  )}${capitalizeFirstLetter(userData.lastName)}`
                : ":("}
            </span>
          </Avatar>
        </Tooltip>
      )}
    </>
  );
};

export default UserAvatar;
