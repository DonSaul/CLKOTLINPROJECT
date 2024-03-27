
import { Box, Typography } from '@mui/material';
import { useParams } from 'react-router-dom';
import { useGetCandidateProfile } from '../hooks/profile/useGetCandidateProfile';
import UserAvatar from './UserAvatar';


const CandidateProfileInfo = () => {
    const { id } = useParams();
    const { data: profileData, isLoading: isLoadingProfile, isError: isErrorProfile } = useGetCandidateProfile(id);
    
    const { firstName, lastName, email, cv } = profileData || {};
    console.log(id);
    console.log(profileData);

    return (
        <Box sx={{ width: '100%', maxWidth: 500 }}>
            <Typography variant="h6" gutterBottom>
                Name: {firstName} {lastName}
            </Typography>
            <Typography variant="h6" gutterBottom>
                Email: {email}
            </Typography>
            {cv && ( 
                <div>
                    <Typography variant="h6" gutterBottom>
                        CV Details:
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        Years of Experience: {cv.yearsOfExperience}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        Salary Expectation: {cv.salaryExpectation}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        Education: {cv.education}
                    </Typography>
                </div>
            )}
        </Box>
    );
};

export default CandidateProfileInfo;
