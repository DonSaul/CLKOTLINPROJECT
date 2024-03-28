import { useEffect, useState } from 'react';
import { Box, Button } from '@mui/material';
import TextField from '@mui/material/TextField';
import Modal from '@mui/material/Modal';
import { useUpdateCurrentProfile } from '../hooks/profile/useUpdateCurrentProfile';

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

const InvitationModal = ({profileData}) => {
    const { mutate } = useUpdateCurrentProfile();
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    
    useEffect(() => {
        if (profileData) {
            setFirstName(profileData.firstName || '');
            setLastName(profileData.lastName || '');
            setEmail(profileData.email || '');
        }
    }, [profileData]);

    
    console.log("data!", profileData)

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const updatedProfileData = {
                firstName,
                lastName,
                email,
            };
            await mutate(profileData.id, updatedProfileData); // Pass the user ID and updated profile data
            handleClose(); // Close the modal after successful update
        } catch (error) {
            console.error('Error updating user information:', error);
        }
    }

    return (
        <div>
            <Button 
                sx={{mt: 2}}
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

                <TextField
                    id="outlined-multiline-static"
                    label="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
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

export default InvitationModal;       