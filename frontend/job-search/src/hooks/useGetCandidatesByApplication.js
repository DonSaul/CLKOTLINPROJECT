import { ENDPOINTS } from "../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../helpers/constants";

const fetchCandidatesByApplication = async (id, token) => {
  const response = await fetch(
    `${ENDPOINTS.getCandidatesByApplication}/${id}`,
    {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
  if (!response.ok) {
    throw new Error("Failed to fetch candidates by vacancy");
  }
  if (response.status === 204) {
    return null;
  }

  let responseData = await response.json();
  console.log("DADADAD: ", responseData);
  return responseData.data;
};

export const getCandidatesByApplication = async (id) => {
  const token = localStorage.getItem(AUTH_TOKEN_NAME);
  const candidates = await fetchCandidatesByApplication(id, token);
  return candidates;
};
