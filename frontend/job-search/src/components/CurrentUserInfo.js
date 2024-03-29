import { useEffect } from 'react';
import { Box, Typography, Card } from '@mui/material';
import { TableCell, Table, TableHead, TableRow, TableContainer, TableBody } from '@mui/material';
import { useCurrentUserProfile } from '../hooks/profile/useCurrentUserProfile';
import LoadingSpinner from './LoadingSpinner';
import UserAvatar from './UserAvatar';
import { ROLES } from '../helpers/constants';   
import ProfileModal from '../components/ProfileModal'
import { useAuth } from '../helpers/userContext';
import { useUpdateProfileInfo } from '../hooks/profile/useCurrentUserProfile';

const CurrentUserInfo = ({ children }) => {
    
    const { data: profileData, isLoading, isError, refetch } = useCurrentUserProfile();
    const { mutate, isSuccess } = useUpdateProfileInfo();
    const { firstName, lastName, email, cv, roleId } = profileData || {};
    const { getUserIdFromToken } = useAuth();

    console.log(profileData)
    useEffect(() => {
        if (profileData) {
            refetch();
        console.log('My profile data:', profileData);
      }   
    }, [profileData]);
    
    useEffect(() => {
        if (isSuccess) {
            refetch();
        }
    }, [isSuccess]);

    if (isLoading) {
        return <LoadingSpinner></LoadingSpinner>;
    }

    if (isError) {
        return <div>Error fetching profile</div>;
    }

    return (
        <>  
            <h3>Profile</h3>
            <Box sx={{ mt:5 }}>
                <Card elevation={3} sx={{ display: "inline-block", width: 400, height: 250, borderRadius: 8, boxShadow: 8, mx: 2, py: 5 }}>
                    <Box sx={{ width: 100, height:100, mx: 18 }}>
                        <UserAvatar user={{ firstName, lastName, email }}></UserAvatar>
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
                    {children}
                    <ProfileModal mutate={mutate} profileData={profileData}></ProfileModal>

                </Card>   
                
                {roleId !== ROLES.CANDIDATE ? null :
                <Card elevation={3} sx={{ display: "inline-block", width: 400, height: 250, borderRadius: 8, boxShadow: 8, mx: 2, py: 5 }}>
                    <Typography variant="h5" gutterBottom>
                        Information
                    </Typography>
                    {cv !== null ? ( 
                        <Box sx={{ p: 3, textAlign:'left', mx: 3 }}>
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
                    ) : "No curriculum available"}
                </Card>
                }
            </Box>
            
            {roleId !== ROLES.CANDIDATE ? null :
            <Card elevation={3} sx={{ display: "inline-block", width: 835, borderRadius: 8, boxShadow: 8, mx: 10, mt:5, py: 5 }}>
                <Typography variant="h5" gutterBottom>
                    Projects
                </Typography>
                {cv?.projects.length ? ( 
                    <Box sx={{ mx: 10 }}>
                        <TableContainer>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell sx={{ fontWeight: 'bold' }}>Name</TableCell>
                                        <TableCell sx={{ fontWeight: 'bold' }}>Description</TableCell>
                                        <TableCell sx={{ fontWeight: 'bold' }}>Job Family</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {cv.projects.map(project => (
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
                ) : "No projects available"}
            </Card>
            }

            {roleId !== ROLES.CANDIDATE ? null :
            <Card elevation={3} sx={{ display: "inline-block", width: 835, borderRadius: 8, boxShadow: 8, mx: 10, my:5, py: 5 }}>
                <Typography variant="h5" gutterBottom>
                    Jobs
                </Typography>
                {cv?.jobs.length ? ( 
                    <Box sx={{ mx: 10 }}>
                        <TableContainer>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell sx={{ fontWeight: 'bold' }}>Start Date</TableCell>
                                        <TableCell sx={{ fontWeight: 'bold' }}>End Date</TableCell>
                                        <TableCell sx={{ fontWeight: 'bold' }}>Position</TableCell>
                                        <TableCell sx={{ fontWeight: 'bold' }}>Description</TableCell>
                                        <TableCell sx={{ fontWeight: 'bold' }}>Job Family</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {cv.jobs.map(job => (
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
                ) : "No jobs available"}
            </Card>
            }
        </>
    );
};

export default CurrentUserInfo;
