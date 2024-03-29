import { useQuery } from "react-query";
import { ENDPOINTS } from "../helpers/endpoints";

const fetchSkills = async () => {
  const response = await fetch(ENDPOINTS.getSkills);
  if (!response.ok) {
    throw new Error("Failed to fetch skills");
  }
  return response.json();
};

const useSkills = () => {
  return useQuery("skills", fetchSkills);
};

export default useSkills;
