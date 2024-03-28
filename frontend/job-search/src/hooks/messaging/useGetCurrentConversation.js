import { useQuery } from 'react-query';
import { ENDPOINTS } from '../../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../../helpers/constants';


const fetchCurrentConversation = async (email) => {
    const token = localStorage.getItem(AUTH_TOKEN_NAME);
    const response = await fetch(`${ENDPOINTS.getCurrentConversationMessages}?email=${email}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      }
    });
  
    if (!response.ok) {
      throw new Error('Failed to fetch conversations');
    }
  
    return response.json();
  };

const useGetCurrentConversation = (email) => {

    return useQuery('getCurrentConversation', () => fetchCurrentConversation(email),{
        manual: true,
        enabled: false,
    });
  };

export default useGetCurrentConversation;