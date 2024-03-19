import { ENDPOINTS } from '../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../helpers/constants';

const deleteVacancyById = async (id, token) => {
  const response = await fetch(`${ENDPOINTS.vacancy}/${id}`, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });

  if (response.status !== 204) {
    throw new Error('Failed to delete vacancy');
  }
  let responseData = await response.json()

  return responseData.data;
};

export const useDeleteVacancy = (id) => {
  const token = localStorage.getItem(AUTH_TOKEN_NAME);
  return deleteVacancyById(id, token)
};
