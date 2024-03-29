import { useQuery } from "react-query";
import { ENDPOINTS } from "../helpers/endpoints";

const fetchJobFamilies = async () => {
  const response = await fetch(ENDPOINTS.getJobFamilies);
  if (!response.ok) {
    throw new Error("Failed to fetch job families");
  }
  return response.json();
};

const useJobFamily = () => {
  return useQuery("jobFamily", fetchJobFamilies);
};

export default useJobFamily;
