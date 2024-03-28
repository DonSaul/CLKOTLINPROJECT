import { useEffect } from 'react';
import { Box, Typography, Card, Button } from '@mui/material';
import { TableCell, Table, TableHead, TableRow, TableContainer, TableBody } from '@mui/material';
import { useGetCurrentUserProfile } from '../hooks/profile/useGetCurrentUserProfile'
import LoadingSpinner from './LoadingSpinner';
import UserAvatar from './UserAvatar';
import { ROLES } from '../helpers/constants';
import ProfileModal from '../components/ProfileModal'

const CurrentUserInfo = () => {
    
    const { data, isLoading, isError } = useGetCurrentUserProfile();
    const { firstName, lastName, email, cv, roleId } = data || {};
    
    console.log(data)
    useEffect(() => {
        if (data) {
        console.log('My profile data:', data);
      }   
    }, [data]);

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
                <Card elevation={3} sx={{ display: "inline-block", width: 400, borderRadius: 8, boxShadow: 8, mx: 2, py: 5 }}>
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
                    <ProfileModal profileData={data}></ProfileModal>

                </Card>   
                
                {roleId !== ROLES.CANDIDATE ? null :
                <Card elevation={3} sx={{ display: "inline-block", width: 400, height: '100%', borderRadius: 8, boxShadow: 8, mx: 2, py: 5 }}>
                    <Typography variant="h5" gutterBottom>
                        Information
                    </Typography>
                    {cv && ( 
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
                    )}
                </Card>
                }
            </Box>
            
            {roleId !== ROLES.CANDIDATE ? null :
            <Card elevation={3} sx={{ display: "inline-block", width: 828, borderRadius: 8, boxShadow: 8, mx: 10, mt:5, py: 5 }}>
                <Typography variant="h5" gutterBottom>
                    Projects
                </Typography>
                {cv && ( 
                    <Box sx={{ mx: 10 }}>
                        <TableContainer>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Name</TableCell>
                                        <TableCell>Description</TableCell>
                                        <TableCell>Job Family</TableCell>
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
                )}
            </Card>
            }

            
           {/* Jobs Here */}
            {roleId !== ROLES.CANDIDATE ? null :
            <Card elevation={3} sx={{ display: "inline-block", width: 825, borderRadius: 8, boxShadow: 8, mx: 10, mt:5, py: 5 }}>
                <Typography variant="h5" gutterBottom>
                    Jobs
                </Typography>
            </Card>
            }

        </>
    );
};

export default CurrentUserInfo;