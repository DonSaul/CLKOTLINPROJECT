import CandidateProfileInfo from "../components/CandidateProfileInfo";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

const CandidateProfile = () => {
  const { userId } = useParams();

  return (
    <div>
      <h3>Candidate Profile</h3>

      <CandidateProfileInfo userId={userId}></CandidateProfileInfo>
    </div>
  );
};

export default CandidateProfile;
