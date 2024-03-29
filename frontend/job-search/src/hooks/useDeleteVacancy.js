import { useCallback } from "react";
import { ENDPOINTS } from "../helpers/endpoints";
import { AUTH_TOKEN_NAME } from "../helpers/constants";

const deleteVacancyById = async (id, token) => {
  const response = await fetch(`${ENDPOINTS.vacancy}/${id}`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    method: "DELETE",
  });

  if (response.status !== 204) {
    console.log(response.body);
    throw new Error("Failed to delete vacancy1");
  }
};

export const useDeleteVacancy = () => {
  const token = localStorage.getItem(AUTH_TOKEN_NAME);

  const deleteVacancy = useCallback(
    async (id) => {
      try {
        await deleteVacancyById(id, token);
      } catch (error) {
        console.error(`Error deleting vacancy: ${error.message}`);
        throw error;
      }
    },
    [token]
  );

  return { mutate: deleteVacancy }; // Devuelve una función de mutación
};
