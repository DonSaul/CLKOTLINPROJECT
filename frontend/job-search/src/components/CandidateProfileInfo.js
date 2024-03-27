
import { useParams } from 'react-router-dom';
import { useGetCandidateProfile } from '../hooks/profile/useGetCandidateProfile';

import { ROLES } from '../helpers/constants';
import { Box, Typography, Card, Button } from '@mui/material';
import { TableCell, Table, TableHead, TableRow, TableContainer, TableBody } from '@mui/material';

const CandidateProfileInfo = () => {
    const { id } = useParams();
    const { data: profileData, isLoading: isLoadingProfile, isError: isErrorProfile } = useGetCandidateProfile(id);
    
    const { firstName, lastName, email, cv } = profileData || {};
    console.log(id);
    console.log(profileData);

    return (
        <>
            <h3>Profile</h3>
            <Box sx={{ mt:5 }}>
                <Card elevation={3} sx={{ display: "inline-block", width: 400, borderRadius: 8, boxShadow: 8, mx: 2, py: 5 }}>
                    <Box sx={{ width: 100, height:100, backgroundColor: "lightgray", mx: 18 }}>
                        Profile picture here?
                    </Box>
                    <Box sx={{ my: 3 }}>
                        <Typography variant="h6" gutterBottom>
                            {firstName} {lastName}
                        </Typography>
                        <Typography variant="h6" gutterBottom>
                            {email}
                        </Typography>
                    </Box>
                    
                    <Button 
                        type="button" 
                        variant="contained" 
                        color="primary" 
                        disabled={getUserRole() !== ROLES.MANAGER}
                        sx={{ mx:1 }}
                    >
                        Invite
                    </Button>
                    <Button type="button" variant="contained" color="primary" >
                        Message
                    </Button>  
                </Card>   
                
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
            </Box>
            
            <Card elevation={3} sx={{ display: "inline-block", width: 828, borderRadius: 8, boxShadow: 8, mx: 10, mt:5, py: 5 }}>
                <Typography variant="h5" gutterBottom>
                    Projects
                </Typography>
                {cv && ( 
                    <Box>
                        <TableContainer>
                            <Table bordered>
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
           {/* Jobs Here */}
           <Card elevation={3} sx={{ display: "inline-block", width: 825, borderRadius: 8, boxShadow: 8, mx: 10, mt:5, py: 5 }}>
                <Typography variant="h5" gutterBottom>
                    Jobs
                </Typography>
           </Card>
           
      </>  
    );
};

export default CandidateProfileInfo;
