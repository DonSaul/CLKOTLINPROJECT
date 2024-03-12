import { useMutation } from  'react-query';
import { useQuery } from 'react-query';
import { ENDPOINTS } from "../helpers/endpoints";
import { toast } from 'react-toastify';

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
        if (res.status==403){
          toast.error("You are not allowed to do that");
        }else if(res.status==400){
          toast.error("You are not allowed to do that");
        } else {
          toast.success("Account created!, you can now login to your account");
        }
      },
  
      onMutate: async (data) => {
        console.log("onMutate data:",data);
        
        
      },
      onError: (_err, data, context) => {
        toast.error("Error creating account!");
        console.log("Error on mutation",_err);
        console.log("Error data:",data);
      },
    });
  };