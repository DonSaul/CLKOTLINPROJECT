import { useMutation, useQuery } from 'react-query';
import { ENDPOINTS } from '../helpers/endpoints';

const fetchUser = async (token) => {
    const response = await fetch(ENDPOINTS.users, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  
    if (!response.ok) {
      throw new Error('Failed to fetch user information');
    }
  
    return response.json();
  };
  
export const useGetCurrentUser = () => {
const token = localStorage.getItem('jobSearchToken');

return useQuery('getCurrentUser', () => fetchUser(token), {
    enabled: !!token, 
});
};