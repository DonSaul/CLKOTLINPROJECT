import { useMutation } from  'react-query';
import { useQuery } from 'react-query';
import { ENDPOINTS } from "../helpers/endpoints";


const addUser = async (data) => {
  const res = await fetch(ENDPOINTS.register, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  return res;
};

export const useRegister = () => {
    return useMutation(addUser, {
      onSuccess: (res) => {
        console.log("onSuccess res:",res);
      },
  
      onMutate: async (data) => {
        console.log("onMutate data:",data);
        
        
      },
      onError: (_err, data, context) => {
        console.log("Error on mutation",_err);
        console.log("Error data:",data);
      },
    });
  };