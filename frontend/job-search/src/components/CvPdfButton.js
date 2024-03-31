import React from "react";
import { Button } from "@mui/material";
import { useGetUserPdf } from "../hooks/useGetPdf";
import { ROLES } from "../helpers/constants";

const CvPdfButton = ({ id, roleId }) => {
  const { pdf, isLoading: isLoadingPdf, isError: isErrorPdf } =
    useGetUserPdf(roleId === ROLES.CANDIDATE ? null : id);

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
