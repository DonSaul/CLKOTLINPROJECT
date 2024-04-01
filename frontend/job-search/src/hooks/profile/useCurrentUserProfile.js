import { useState } from 'react';
import { useMutation, useQuery } from 'react-query';
import { ENDPOINTS } from '../../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../../helpers/constants';
import { useAuth } from '../../helpers/userContext';
import { toast } from 'react-toastify';

const  fetchMyProfileInfo = async () => {
    const token = localStorage.getItem(AUTH_TOKEN_NAME);
        
    const response = await fetch(ENDPOINTS.profile, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
    });

        if (!response.ok) {
            throw new Error('Failed to fetch user profile');
        }

        return response.json();
    };
    
    export const useCurrentUserProfile = () => {
        let token = localStorage.getItem(AUTH_TOKEN_NAME);
        return useQuery(
            'getMyProfileInfo',
            fetchMyProfileInfo,
            {
                enabled: !!token,
            }
        );
    }
    
    const updateUserProfile = async (data) => {
        let token = localStorage.getItem(AUTH_TOKEN_NAME);
      
      
        const res = await fetch(`${ENDPOINTS.updateProfile}/${data.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          },
          body: JSON.stringify(data),
        });
      
        return res;
      };
    
      export const useUpdateProfileInfo = () => {
        return useMutation(updateUserProfile, {
          onSuccess: (res) => {
            
            console.log("onSuccess res:",res);
    
            if (res.status===403){
              toast.error('You are not allowed to do that'); 
            } else if (res.status===400){
              toast.error('Invalid profile data!'); 
            } else {
              toast.success("Profile updated successfully!")
            }
          },
      
          onMutate: async (data) => {
            console.log("onMutate data:",data);
            
            
          },
          onError: (_err, data, context) => {
            toast.error('Error updating profile!'); 
            console.log("Error on mutation",_err);
            console.log("Error data:",data);
          },
        });
      };

