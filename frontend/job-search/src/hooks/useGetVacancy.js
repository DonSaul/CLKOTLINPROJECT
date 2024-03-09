import { useQuery } from 'react-query';
import { ENDPOINTS } from '../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../helpers/constants';

const fetchVacancyById = async (id, token) => {
  const response = await fetch(`${ENDPOINTS.vacancy}/${id}`, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch vacancy information');
  }

  return response.json();
};

export const useGetVacancyById = (id) => {
  const token = localStorage.getItem(AUTH_TOKEN_NAME);

  return useQuery(['getVacancyById', id], () => fetchVacancyById(id, token), {
    enabled: !!token,
  });
};
