import { useQuery } from "react-query";
import { ENDPOINTS } from "../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../helpers/constants";
const fetchUser = async () => {
  let token = localStorage.getItem(AUTH_TOKEN_NAME);

  const response = await fetch(ENDPOINTS.users, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error("Failed to fetch user information");
  }

  return response.json();
};

export const useGetCurrentUser = () => {
  const token = localStorage.getItem("jobSearchToken");

  return useQuery("getCurrentUser", () => fetchUser(token), {
    enabled: !!token,
  });
};
