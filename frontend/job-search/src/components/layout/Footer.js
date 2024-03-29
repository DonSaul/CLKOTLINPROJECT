import React from "react";
import { Grid, Typography, Link, Button } from "@mui/material";
import GitHubIcon from "@mui/icons-material/GitHub";
import ContactSupportIcon from "@mui/icons-material/ContactSupport";

const Footer = () => {
  const memberLinks = {
    "Edgar Araya,": "https://github.com/edgararaya",
    " Natalia Devia,": "https://github.com/ndevia",
    " Rodrigo Molina,": "https://github.com/s0alken",
    " Gabriel Urbina,": "https://github.com/Gabe239",
    " Rafael Uribe,": "https://github.com/RafaUribeG",
    " Esteban Santibañez,": "https://github.com/Santisu",
    " Jamiro Manriquez": "https://github.com/jamirou",
  };

  const getRandomColor = () => {
    const r = Math.floor(Math.random() * 156) + 100;
    const g = Math.floor(Math.random() * 156) + 100;
    const b = Math.floor(Math.random() * 156) + 100;
    return `rgb(${r}, ${g}, ${b})`;
  };

  const handleHover = (name) => {
    const link = document.getElementById(name);
    const randomColor = getRandomColor();
    link.style.color = randomColor;
  };

  const handleLeave = (name) => {
    const link = document.getElementById(name);
    link.style.color = "#fff";
  };

  const memberNames = Object.keys(memberLinks);
  const firstThreeMembers = memberNames.slice(0, 3);
  const remainingMembers = memberNames.slice(3);

  return (
    <Grid
      container
      justify="space-between"
      alignItems="center"
      style={{
        backgroundColor: "#263238",
        color: "#fff",
        padding: "40px",
        marginTop: "5px",
      }}
    >
      {/* GitHub */}
      <Grid item xs={12} sm={4}>
        <Typography variant="body1">
          <Link
            href="https://github.com/DonSaul/CLKOTLINPROJECT"
            target="_blank"
            rel="noopener noreferrer"
            style={{ color: "#fff" }}
          >
            <GitHubIcon style={{ marginRight: "5px" }} />
            GitHub Repository
          </Link>
        </Typography>
      </Grid>
      {/* Members Grid */}
      <Grid item xs={12} sm={4}>
        <div style={{ textAlign: "center" }}>
          <Typography variant="body1">
            Made with{" "}
            <span style={{ color: "transparent", textShadow: "0 0 0 #ef457c" }}>
              🤍
            </span>{" "}
            by:
          </Typography>
          <Typography variant="body2" style={{ marginLeft: "10px" }}>
            {/* Render the first three members */}
            {firstThreeMembers.map((name, index) => (
              <Link
                key={index}
                id={name}
                href={memberLinks[name]}
                target="_blank"
                rel="noopener noreferrer"
                style={{ color: "#fff", textDecoration: "none" }}
                onMouseEnter={() => handleHover(name)}
                onMouseLeave={() => handleLeave(name)}
              >
                {name}
              </Link>
            ))}
          </Typography>
          {/* Render the remaining members */}
          <Typography variant="body2" style={{ marginLeft: "10px" }}>
            {remainingMembers.map((name, index) => (
              <Link
                key={index}
                id={name}
                href={memberLinks[name]}
                target="_blank"
                rel="noopener noreferrer"
                style={{ color: "#fff", textDecoration: "none" }}
                onMouseEnter={() => handleHover(name)}
                onMouseLeave={() => handleLeave(name)}
              >
                {name}
              </Link>
            ))}
          </Typography>
        </div>
      </Grid>
      {/* Contact Button and Rights Reserved */}
      <Grid item xs={12} sm={4}>
        <div style={{ textAlign: "right" }}>
          <Button
            variant="outlined"
            endIcon={<ContactSupportIcon />}
            style={{ color: "#fff", borderColor: "#fff", marginRight: "10px" }}
          >
            Contact Us
          </Button>
          <Typography variant="body2" style={{ color: "#ccc" }}>
            © 2024 Job Search. All rights reserved.
          </Typography>
        </div>
      </Grid>
    </Grid>
  );
};

export default Footer;
