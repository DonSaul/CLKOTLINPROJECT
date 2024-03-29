import React from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Container from "@mui/material/Container";

const CardContainer = ({ children, width = "lg" }) => {
  return (
    <Container maxWidth={width} sx={{ marginTop: 4 }}>
      <Card elevation={3} sx={{ borderRadius: 8, boxShadow: 8 }}>
        <CardContent>{children}</CardContent>
      </Card>
    </Container>
  );
};

export default CardContainer;
