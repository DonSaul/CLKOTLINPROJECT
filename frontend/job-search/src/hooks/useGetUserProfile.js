import { ENDPOINTS } from "../helpers/endpoints"


const fetchUserProfile = async(token) => {
    try {
        const response = await fetch(ENDPOINTS.userProfile, {
            headers: {
                'Content-Type': `application/json`,
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Failed to fetch user profile information');
          }
      
          const userProfileData = await response.json();
      
          return userProfileData;
        } catch (error) {
          throw new Error('Failed to fetch user profile information');
        }
      };
      
      export const useGetUserProfile = () => {
        const token = localStorage.getItem(AUTH_TOKEN_NAME);
      
        return useQuery('getUserProfile', () => fetchUserProfile(token), {
          enabled: !!token,
        });
      };
