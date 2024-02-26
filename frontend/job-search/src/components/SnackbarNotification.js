import React, { useState } from 'react';
import { Snackbar } from '@mui/material';
import { Alert } from '@mui/material';

const SnackbarNotification = ({ open, message, onClose, severity,autoHideDuration=3000 }) => {
  return (
    <Snackbar open={open} autoHideDuration={autoHideDuration} onClose={onClose}>
      <Alert onClose={onClose} severity={severity}>
        {message}
      </Alert>
    </Snackbar>
  );
};

export default SnackbarNotification;