import { Box, Typography } from '@mui/material';
const CandidateProfileInfo = () => {
    const firstName = "firstName";
    const lastName = "lastName";
    const email = "email@mail.com";
    const role = "ROLE";

    return(
        <Box sx={{ width: '100%', maxWidth: 500 }}>
            <Typography variant="h6" gutterBottom>
                {firstName} {lastName}
            </Typography>
            <Typography variant="h6" gutterBottom>
                {email}
            </Typography>
            <Typography variant="h5" gutterBottom>
                ROLE
            </Typography>
        </Box>
        
    );
};

export default CandidateProfileInfo;
