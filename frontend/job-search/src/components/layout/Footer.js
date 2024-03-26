import React from 'react';
import { Grid, Typography } from '@mui/material';
import GitHubIcon from '@mui/icons-material/GitHub';

//change this to look good
const Footer = () => {
    return (
        <Grid container justify="space-between" alignItems="center" style={{ backgroundColor: '#f0f0f0', padding: '20px', marginTop: '20px' }}>
            {/* GitHub  */}
            <Grid item xs={12} sm={4}>
                <Typography variant="body1">
                    <a href="https://github.com/DonSaul/CLKOTLINPROJECT" target="_blank" rel="noopener noreferrer">
                        <GitHubIcon />
                    </a>
                </Typography>
            </Grid>
            {/* Members Grid */}
            <Grid item xs={12} sm={4}>
                <div>
                    <Typography variant="body1">Edgar Araya</Typography>
                    <Typography variant="body1">Natalia Devia</Typography>
                    <Typography variant="body1">Rodrigo Molina</Typography>
                    <Typography variant="body1">Gabriel Urbina</Typography>
                    <Typography variant="body1">Rafael Uribe</Typography>
                    <Typography variant="body1">Esteban Santibañez</Typography>
                    <Typography variant="body1">Jamiro Manriquez</Typography>
                </div>
            </Grid>
            {/* Other information */}
            <Grid item xs={12} sm={4}>
                <Typography variant="body1">
                    some other stuff
                </Typography>
            </Grid>
        </Grid>
    );
}

export default Footer;
