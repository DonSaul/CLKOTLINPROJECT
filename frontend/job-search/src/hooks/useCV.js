import { useMutation } from  'react-query';
import { useQuery } from 'react-query';
import { ENDPOINTS } from "../helpers/endpoints";


const addCV = async (data) => {
  const res = await fetch(ENDPOINTS.submitCV, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  return res;
};

export const useCV = () => {
    return useMutation(addCV, {
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

//get cv of user
  const fetchCV = async (id) => {
    const response = await fetch(`${ENDPOINTS.submitCV}/${id}`);
    if (!response.ok) {
      throw new Error('Failed to fetch cv');
    }
    return response.json();
  };
  
  export const useGetCurrentUserCv = (id) => {
    return useQuery(['getCv',id],()=> fetchCV(id));
  };
  
