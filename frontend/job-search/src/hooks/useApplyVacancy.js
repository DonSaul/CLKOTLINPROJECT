import { useMutation } from  'react-query';
import { useQuery } from 'react-query';
import { ENDPOINTS } from "../helpers/endpoints";
import { AUTH_TOKEN_NAME } from '../helpers/constants';
import { toast } from 'react-toastify';


const addApplication = async (data) => {
  let token = localStorage.getItem(AUTH_TOKEN_NAME);


  const res = await fetch(ENDPOINTS.application, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify(data),
  });

  return res;
};

export const useApplyVacancy = () => {
    return useMutation(addApplication, {
      onSuccess: (res) => {
        
        console.log("onSuccess res:",res);

        if (res.status===403){
          toast.error('You are not allowed to do that'); 
        } else{
          toast.success("Applied successfully!")
        }

      },
  
      onMutate: async (data) => {
        console.log("onMutate data:",data);
        
        
      },
      onError: (_err, data, context) => {
        toast.error("Error on application!")
        console.log("Error on mutation",_err);
        console.log("Error data:",data);
      },
    });
  };
