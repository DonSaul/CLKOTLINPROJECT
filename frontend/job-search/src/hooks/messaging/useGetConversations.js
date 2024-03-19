import { useQuery } from 'react-query';
import { ENDPOINTS } from '../../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../../helpers/constants';


const fetchConversations = async () => {
  let token= localStorage.getItem(AUTH_TOKEN_NAME);
  const response = await fetch(ENDPOINTS.getConversations,{
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    }});
  if (!response.ok) {
    throw new Error('Failed to fetch conversations');
  }
  return response.json();
};

const useGetConversations = () => {
  return useQuery('getConversations', fetchConversations);
};

export default useGetConversations;