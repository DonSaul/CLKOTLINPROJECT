import { useParams } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import { useGetCandidateProfile } from "../hooks/profile/useGetCandidateProfile";
import { useGetUserPdf } from "../hooks/useGetPdf";
import { useAuth } from "../helpers/userContext";
import { ROLES } from "../helpers/constants";
import { Box, Typography, Card, Button } from "@mui/material";
import {
  TableCell,
  Table,
  TableHead,
  TableRow,
  TableContainer,
  TableBody,
} from "@mui/material";
import { paths } from "../router/paths";
import { useState } from "react";
import ProfileAvatar from "./avatar/ProfileAvatar";

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

  const ProfilePdfButton = () => {
    const { pdf, isLoading: isLoadingPdf, isError: isErrorPdf } = useGetUserPdf(id);

    const handleGetPdf = () => { 
      if (pdf) {
        const pdfUrl = URL.createObjectURL(pdf);
        window.open(pdfUrl, '_blank');
      }
    }; 

    return (
      <Button
        type="button"
        variant="contained"
        color="primary"
        onClick={handleGetPdf}
        disabled={getUserRole() !== ROLES.MANAGER || isLoadingPdf || isErrorPdf}
        sx={{ mx: 1 }}
      >
        View CV on PDF
      </Button>
    );
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
              <ProfilePdfButton />
            </>
          )}

          <Button type="button" variant="contained" color="primary">
            Message
          </Button>
        </Card>

        {roleId !== ROLES.CANDIDATE ? null : (
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
            <Typography variant="h5" gutterBottom>
              Information
            </Typography>
            {cv !== null ? (
              <Box sx={{ p: 3, textAlign: "left", mx: 3 }}>
                <Typography variant="h6" gutterBottom>
                  Years of Experience: {cv.yearsOfExperience}
                </Typography>
                <Typography variant="h6" gutterBottom>
                  Salary Expectation: {cv.salaryExpectation}
                </Typography>
                <Typography variant="h6" gutterBottom>
                  Education: {cv.education}
                </Typography>
              </Box>
            ) : (
              "No curriculum available"
            )}
          </Card>
        )}
      </Box>

      {roleId !== ROLES.CANDIDATE ? null : (
        <Card
          elevation={3}
          sx={{
            display: "inline-block",
            width: 835,
            borderRadius: 8,
            boxShadow: 8,
            mx: 10,
            mt: 5,
            py: 5,
          }}
        >
          <Typography variant="h5" gutterBottom>
            Projects
          </Typography>
          {cv?.projects.length ? (
            <Box sx={{ mx: 10 }}>
              <TableContainer>
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell sx={{ fontWeight: "bold" }}>Name</TableCell>
                      <TableCell sx={{ fontWeight: "bold" }}>
                        Description
                      </TableCell>
                      <TableCell sx={{ fontWeight: "bold" }}>
                        Job Family
                      </TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {cv.projects.map((project) => (
                      <TableRow key={project.projectId}>
                        <TableCell>{project.name}</TableCell>
                        <TableCell>{project.description}</TableCell>
                        <TableCell>{project.jobFamily.name}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </Box>
          ) : (
            "No projects available"
          )}
        </Card>
      )}

      {roleId !== ROLES.CANDIDATE ? null : (
        <Card
          elevation={3}
          sx={{
            display: "inline-block",
            width: 835,
            borderRadius: 8,
            boxShadow: 8,
            mx: 10,
            my: 5,
            py: 5,
          }}
        >
          <Typography variant="h5" gutterBottom>
            Jobs
          </Typography>
          {cv?.jobs.length ? (
            <Box sx={{ mx: 10 }}>
              <TableContainer>
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell sx={{ fontWeight: "bold" }}>
                        Start Date
                      </TableCell>
                      <TableCell sx={{ fontWeight: "bold" }}>
                        End Date
                      </TableCell>
                      <TableCell sx={{ fontWeight: "bold" }}>
                        Position
                      </TableCell>
                      <TableCell sx={{ fontWeight: "bold" }}>
                        Description
                      </TableCell>
                      <TableCell sx={{ fontWeight: "bold" }}>
                        Job Family
                      </TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {cv.jobs.map((job) => (
                      <TableRow key={job.jobsId}>
                        <TableCell>{job.startDate}</TableCell>
                        <TableCell>{job.endDate}</TableCell>
                        <TableCell>{job.position}</TableCell>
                        <TableCell>{job.description}</TableCell>
                        <TableCell>{job.jobFamily.name}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </Box>
          ) : (
            "No jobs available"
          )}
        </Card>
      )}
    </>
  );
};

export default CandidateProfileInfo;
