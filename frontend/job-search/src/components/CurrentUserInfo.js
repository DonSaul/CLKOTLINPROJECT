import { useEffect } from 'react';
import { Box, Typography, Card, Button } from '@mui/material';
import { useGetCurrentUserProfile } from '../hooks/profile/useGetCurrentUserProfile'

 
const CurrentUserInfo = () => {
    
    const { data, isLoading, isError } = useGetCurrentUserProfile();

  useEffect(() => {
    if (data) {
      console.log('My profile data:', data);
    }
  }, [data]);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (isError) {
    return <div>Error fetching profile</div>;
  }

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
                            {data.firstName} {data.lastName}
                        </Typography>
                        <Typography variant="h6" gutterBottom>
                            {data.email}
                        </Typography>
                    </Box>
                    
                    {/* <Button 
                        type="button" 
                        variant="contained" 
                        color="primary" 
                        disabled={getUserRole() !== ROLES.MANAGER}
                        sx={{ mx:1 }}
                    >
                        Invite
                    </Button> */}
                    <Button type="button" variant="contained" color="primary" >
                        Edit
                    </Button>  
                </Card>   
            </Box>
    </>
  );
};



export default CurrentUserInfo;