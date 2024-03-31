import React from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { useGetCandidateProfile } from "../hooks/profile/useGetCandidateProfile";
import { useAuth } from "../helpers/userContext";
import { ROLES } from "../helpers/constants";
import { Box, Typography, Card, Button } from "@mui/material";
import { TableCell, Table, TableHead, TableRow, TableContainer, TableBody } from "@mui/material";
import { paths } from "../router/paths";
import { useState } from "react";
import ProfileAvatar from "./avatar/ProfileAvatar";
import CvPdfButton from "./CvPdfButton"; // Importar el componente aquí

const CandidateProfileInfo = () => {
  const { id } = useParams();
  const {
    data: profileData,
    isLoading: isLoadingProfile,
    isError: isErrorProfile,
  } = useGetCandidateProfile(id);
  const { getUserRole } = useAuth();
  const { firstName, lastName, email, cv, roleId } = profileData || {};
  const navigate = useNavigate();
  const [avatarSize, setAvatarSize] = useState("500px");

  const handleInvite = (id) => {
    const candidateId = id;
    console.log("Sending invitation to candidate:", candidateId);
    navigate(`${paths.sendInvitation.replace(":id", candidateId)}`);
  };

  return (
    <>
      <h3>Profile</h3>
      <Box sx={{ mt: 5 }}>
        <Card
          elevation={3}
          sx={{
            display: "inline-block",
            width: 400,
            height: 250,
            borderRadius: 8,
            boxShadow: 8,
            mx: 2,
            py: 5,
          }}
        >
          <Box
            sx={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <ProfileAvatar user={{ firstName, lastName, email }} />
          </Box>
          <Box sx={{ my: 3 }}>
            <Typography variant="h6" gutterBottom>
              {firstName} {lastName}
            </Typography>
            <Typography variant="h6" gutterBottom>
              {email}
            </Typography>
          </Box>

          {roleId !== ROLES.CANDIDATE ? null : (
            <>
              <Button
                type="button"
                variant="contained"
                color="primary"
                onClick={() => handleInvite(id)}
                disabled={getUserRole() !== ROLES.MANAGER}
                sx={{ mx: 1 }}
              >
                Invite
              </Button>
              <CvPdfButton id={id} roleId={getUserRole()} />
            </>
          )}

          <Button type="button" variant="contained" color="primary">
            Message
          </Button>
        </Card>

        {/* Resto del código... */}
      </Box>
    </>
  );
};

export default CandidateProfileInfo;
