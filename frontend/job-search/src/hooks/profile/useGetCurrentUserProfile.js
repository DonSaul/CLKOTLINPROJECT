import { useQuery } from 'react-query';
import { ENDPOINTS } from '../../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../../helpers/constants';

const fetchMyProfileInfo = async (token) => {
  const response = await fetch(ENDPOINTS.profile, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch user profile');
  }

  const responseData = await response.json();
  return responseData;
};

export const useGetCurrentUserProfile = () => {
  const token = localStorage.getItem(AUTH_TOKEN_NAME);

  return useQuery('getMyProfileInfo', () => fetchMyProfileInfo(token), {
    enabled: !!token,
  });
};