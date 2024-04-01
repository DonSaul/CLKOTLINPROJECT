import { useEffect } from "react";
import { Box, Typography, Card, Button } from "@mui/material";
import {
  TableCell,
  Table,
  TableHead,
  TableRow,
  TableContainer,
  TableBody,
} from "@mui/material";
import { useCurrentUserProfile } from "../hooks/profile/useCurrentUserProfile";
import LoadingSpinner from "./LoadingSpinner";
import { ROLES } from "../helpers/constants";
import ProfileModal from "../components/ProfileModal";
import { useAuth } from "../helpers/userContext";
import { useUpdateProfileInfo } from "../hooks/profile/useCurrentUserProfile";
import ProfileAvatar from "./avatar/ProfileAvatar";
import CvPdfButton from "./CvPdfButton";

const CurrentUserInfo = () => {
  const {
    data: profileData,
    isLoading,
    isError,
    refetch,
  } = useCurrentUserProfile();
  const { mutate, isSuccess } = useUpdateProfileInfo();
  const { firstName, lastName, email, cv, roleId } = profileData || {};

  useEffect(() => {
    if (profileData) {
      console.log("My profile data:", profileData);
    }
  }, [profileData]);

  useEffect(() => {
    if (isSuccess) {
      refetch();
    }
  }, [isSuccess]);

  if (isLoading) {
    return <LoadingSpinner />;
  }

  if (isError) {
    return <div>Error fetching profile</div>;
  }

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

          {/* Edit info Modal */}
          <Button>
            <ProfileModal
              mutate={mutate}
              profileData={profileData}
            >
            </ProfileModal>
          </Button>
          <CvPdfButton />
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
                  // display: 'flex', 
                  // flexDirection: 'column', 
                  // alignItems: 'center', 
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

export default CurrentUserInfo;
