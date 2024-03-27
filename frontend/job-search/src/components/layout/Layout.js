import React from 'react';
import { AppBar, Autocomplete, Button, Grid, TextField, Typography } from '@mui/material';
import MainToolbar from '../MainToolbar';

import { Outlet } from "react-router-dom";
import Footer from './Footer';

const Layout = ({ children }) => {
    return (
        <Grid container direction="column" 
        style=
        {{// minHeight: '1vh'

        }}>
            {/* Toolbar */}
            <Grid item>
                
                <MainToolbar></MainToolbar>

            </Grid>

            {/* Content */}
            <Grid item style={{ flexGrow: 1, marginTop: '5px' ,minHeight: '100vh'}}>
                {children}
            </Grid>

            {/* Footer */}

            <Footer ></Footer>
            
        </Grid>
    );
}

export default Layout;