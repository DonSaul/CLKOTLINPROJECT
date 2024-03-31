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

  const handleInvite = () => { 
    // const candidateId = id;
    // console.log("Sending invitation to candidate:", candidateId);
    navigate(`${paths.sendInvitation.replace(":id", id)}`);
  }; 

  const handleSend = () => {
    navigate(`${paths.messagingUser.replace(":id", id)}`)
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
            height: 300, 
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
              mt:3
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
              >
                Invite
              </Button>
              <CvPdfButton id={id} roleId={getUserRole()} />
            </>
          )}

          <Button 
            type="button" 
            variant="contained"
            color="primary"
            onClick={() => handleSend(id)}
          >
            Message
          </Button>
        </Card>

        {roleId !== ROLES.CANDIDATE ? null : (
          <Card
            elevation={3}
            sx={{
              display: "inline-block",
              width: 600,
              height: 300,
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
              <Box 
                sx={{ 
                  display: 'flex', 
                  flexDirection: 'column', 
                  alignItems: 'center', 
                  p: 2,
                  mx: 3 
                  }}>
                <TableContainer>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell sx={{ border:'none', p: 0, pr:2 }}>
                          <Typography sx={{ fontWeight: 'bold', marginBlock: 0  }}  variant="subtitle1" gutterBottom>Years of Experience</Typography>
                        </TableCell>
                        <TableCell sx={{ border:'none', p: 0, pr:2  }}>
                          <Typography sx={{ fontWeight: 'bold', m: 0  }}  variant="subtitle1" gutterBottom>Salary Expectation</Typography>
                        </TableCell>
                        <TableCell sx={{ border:'none', p: 0, pr:2  }}>
                          <Typography sx={{ fontWeight: 'bold', m: 0 }}  variant="subtitle1" gutterBottom>Education</Typography>
                        </TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                        <TableRow>
                          <TableCell sx={{ border:'none', p: 0 }}>{cv.yearsOfExperience}</TableCell>
                          <TableCell sx={{ border:'none', p: 0 }}>{cv.salaryExpectation}</TableCell>
                          <TableCell  sx={{ border:'none',  p: 0 }}>{cv.education}</TableCell>
                        </TableRow>
                    </TableBody>
                  </Table>
                </TableContainer>
                <Box sx={{ mt: 2, mb:1, textAlign: 'left' }}>
                  <Typography sx={{ fontWeight: 'bold' }} variant="subtitle1" gutterBottom>
                    Summary
                  </Typography>
                  <Typography variant="body1" gutterBottom>
                    {cv.summary}
                  </Typography>
                </Box>
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
            width: 1030,
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
            width: 1030,
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
                        Company
                      </TableCell>
                      <TableCell sx={{ fontWeight: "bold" }}>
                        Position
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
                        <TableCell>{job.company}</TableCell>
                        <TableCell>{job.position}</TableCell>
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
