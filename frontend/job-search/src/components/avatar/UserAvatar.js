import React, { useState } from "react";
import Avatar from "@mui/material/Avatar";
import { useAuth } from "../../helpers/userContext";
import { Tooltip } from "@mui/material";
import { avatarBoxShadow, avatarProfileTextSize } from "./avatarConstants";
import { ROLES } from "../../helpers/constants";
import { avatarManagerBorderStyle } from "./avatarConstants";
import { Badge } from "@mui/base";
const UserAvatar = ({
  user,
  avatarSize,
  borderStyle,
  enableRoleBorder,
  borderSize,
}) => {
  const { getUserEmail, getUserFirstName, getUserLastName, getUserRole } =
    useAuth();

  const [defaultAvatarSize, setDefaultAvatarSize] = useState("35px");
  const [defaultTextSize, setDefaultTextSize] = useState("0.9rem");
  const [defaultBorder, setDefaultBorder] = useState(
    `solid ${borderSize ? borderSize : 4}px transparent`
  );
  const capitalizeFirstLetter = (str) => {
    return str?.charAt(0).toUpperCase();
  };

  const stringToColour = (str) => {
    if (!str) {
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
        roleId: getUserRole(),
      };
    }
  };

  const userData = getUserData();
  const hasName = userData?.firstName || userData?.lastName;

  const textSize = avatarSize ? avatarProfileTextSize : defaultTextSize;
  const boxShadow = borderStyle ? avatarBoxShadow : undefined;

  const getBorderStyle = () => {
    if (enableRoleBorder && userData?.roleId === ROLES.MANAGER) {
      return avatarManagerBorderStyle;
    }
    if (borderStyle) {
      return borderStyle;
    }
    return defaultBorder;
  };

  const finalBorderStyle = getBorderStyle();

  return (
    <>
      {userData && (
        <Tooltip
          title={
            userData
              ? `${userData.firstName} ${userData.lastName} Role:${userData.roleId} (${userData.email})`
              : "No data"
          }
        >
          <Avatar
            sx={{
              bgcolor: stringToColour(userData?.email || userData?.email),
              height: avatarSize || defaultAvatarSize,
              width: avatarSize || defaultAvatarSize,
              border: finalBorderStyle,
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
