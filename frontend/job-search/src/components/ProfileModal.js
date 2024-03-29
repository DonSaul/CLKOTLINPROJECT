import { useEffect, useState } from 'react';
import { Box, Button } from '@mui/material';
import TextField from '@mui/material/TextField';
import Modal from '@mui/material/Modal';
import { useCurrentUserProfile } from '../hooks/profile/useCurrentUserProfile';
import { useAuth } from '../helpers/userContext';
import { useUpdateProfileInfo } from '../hooks/profile/useCurrentUserProfile';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 350,
  bgcolor: 'background.paper',
  border: 'none',
  borderRadius: 5,
  boxShadow: 24,
  p: 4,
};

const ProfileModal = ({mutate, profileData}) => {
    // const { updateUserProfile, profileData, isLoading, isError } = useCurrentUserProfile();
    const { getUserIdFromToken } = useAuth();
   
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const id = getUserIdFromToken(); 

    console.log("ID", id)
    
    useEffect(() => {
        if (profileData) {
            setFirstName(profileData.firstName || '');
            setLastName(profileData.lastName || '');
            
        }
    }, [profileData]);

    console.log("data!", profileData)

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            if (!id) {
                throw new Error('User ID not available')
            }

            const updatedProfileData = {
                id,
                firstName,
                lastName,
               
            };

            console.log("updated", updatedProfileData)
            mutate(updatedProfileData);
            // await updateUserProfile(id, updatedProfileData)
            handleClose();
        } catch (error) {
            console.error("Error updating user profile", error)
        }
    };

    return (
        <div>
            <Button 
                sx={{mb: 2}}
                variant="contained" 
                color="primary" 
                onClick={handleOpen}
            >Edit</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
            <Box sx={style}>
            <form onSubmit={handleSubmit}>
                <TextField
                    id="outlined-multiline-static"
                    label="First Name"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    fullWidth
                    margin="normal"
                />
    
                <TextField
                    id="outlined-multiline-static"
                    label="Last Name"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}                  
                    fullWidth
                    margin="normal"        
                /> 


                <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
                    Send
                </Button> 

            </form>
            </Box>
            </Modal>
        </div>
    );
}

export default ProfileModal;       