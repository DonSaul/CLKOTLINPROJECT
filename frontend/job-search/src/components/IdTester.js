import React from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

const IdTester = ({ defaultId, setId }) => {
  return (
    <div>
      <h2>Testing</h2>
      <TextField
        label="Test ID"
        type="text"
        value={defaultId}
        onChange={(e) => setId(e.target.value)}
        fullWidth
        margin="normal"
      />
    
    </div>
  );
};

export default IdTester;