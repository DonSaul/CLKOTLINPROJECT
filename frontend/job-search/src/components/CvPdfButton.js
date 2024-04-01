import React from "react";
import { Button } from "@mui/material";
import { useGetUserPdf } from "../hooks/useGetPdf";
import { ROLES } from "../helpers/constants";
import { useAuth } from "../helpers/userContext";

const CvPdfButton = ({ id }) => {
  const { getUserRole } = useAuth();
  const { pdf, isLoading: isLoadingPdf, isError: isErrorPdf } =
    useGetUserPdf(getUserRole === ROLES.CANDIDATE ? null : id);

  const handleGetPdf = () => {
    if (pdf) {
      const pdfUrl = URL.createObjectURL(pdf);
      window.open(pdfUrl, "_blank");
    }
  };

  return (
    <Button
      type="button"
      variant="contained"
      color="primary"
      onClick={handleGetPdf}
      disabled={isLoadingPdf || isErrorPdf}
      sx={{ mx: 1 }}
    >
      View CV on PDF
    </Button>
  );
};

export default CvPdfButton;
