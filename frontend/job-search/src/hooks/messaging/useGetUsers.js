import { useQuery } from "react-query";
import { ENDPOINTS } from "../../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../../helpers/constants";

const fetchUsers = async () => {
  let token = localStorage.getItem(AUTH_TOKEN_NAME);
  const response = await fetch(ENDPOINTS.getUsers, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });
  if (!response.ok) {
    throw new Error("Failed to fetch users");
  }
  return response.json();
};

const useGetUsers = () => {
  return useQuery("getUsers", fetchUsers);
};

export default useGetUsers;
