import { useState } from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "none",
  borderRadius: 5,
  boxShadow: 24,
  p: 4,
};

const InvitationModal = ({children, data}) => {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const dataIsSelected = data && Object.keys(data).length > 0;

  return (
    <div>
      <Button 
        mt={2} variant="contained" 
        color="primary" 
        onClick={handleOpen}
        disabled={dataIsSelected ? false : true}
      >Send invitation</Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          {children}
        </Box>
      </Modal>
    </div>
  );
};

export default InvitationModal;
