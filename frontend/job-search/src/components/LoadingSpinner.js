import React from 'react';
import CircularProgress from '@mui/material/CircularProgress';

const LoadingSpinner = () => {
    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '50vh' }}>
            <CircularProgress size={80} />
        </div>
    );
};

export default LoadingSpinner;
