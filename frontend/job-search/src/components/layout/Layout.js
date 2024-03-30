import React, { useState, useEffect } from "react";
import { Grid } from "@mui/material";
import MainToolbar from "../MainToolbar";

import Footer from "./Footer";

const Layout = ({ children }) => {
  const [contentMinHeight, setContentMinHeight] = useState("71.5vh");
  const [marginBottomContent, setMarginBottomContent] = useState("5vh");

  return (
    <Grid
      container
      direction="column"
      style={
        {
          // minHeight: '1vh'
        }
      }
    >
      {/* Toolbar */}
      <Grid item>
        <MainToolbar></MainToolbar>
      </Grid>

      {/* Content */}
      <Grid
        item
        style={{
          flexGrow: 1,
          marginTop: "5px",
          minHeight: contentMinHeight,
          marginBottom: marginBottomContent,
        }}
      >
        {children}
      </Grid>

      {/* Footer */}

      <Footer></Footer>
    </Grid>
  );
};

export default Layout;
