import { useQuery } from 'react-query';
import { ENDPOINTS } from '../../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../../helpers/constants';

const fetchCandidateProfileById = async (id, token) => {
  const response = await fetch(`${ENDPOINTS.candidateProfile}/${id}`, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch candidate information');
  }
  console.log("resp", response)
  let responseData = await response.json()
  console.log("profile", responseData)
  return responseData;
};

export const useGetCandidateProfile = (id) => {
  const token = localStorage.getItem(AUTH_TOKEN_NAME);

  return useQuery(['getCandidateById', id], () => fetchCandidateProfileById(id, token), {
    enabled: !!token,
  });
};
