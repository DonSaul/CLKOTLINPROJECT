import { ENDPOINTS } from '../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../helpers/constants';

const fetchVacancyByManager = async (token) => {
  const response = await fetch(`${ENDPOINTS.getVacanciesByManager}`, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });
  console.log("RESPONSE:  ", response.satus)
  
  if (!response.ok) {
    throw new Error('Failed to fetch vacancy information');
  }
  if (response.status === 204) {
    return null; // or any appropriate response
  }
  
  let responseData = await response.json();
  return responseData.data;
};

export const getVacancyByManager = async () => {
  const token = localStorage.getItem(AUTH_TOKEN_NAME);
  const vacancies = await fetchVacancyByManager(token);
  return vacancies;
};
