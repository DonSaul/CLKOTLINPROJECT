import { useMutation } from  'react-query';
import { useQuery } from 'react-query';
import { ENDPOINTS } from '../../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../../helpers/constants';
import { toast } from 'react-toastify';


const addMessage = async (data) => {
    let token = localStorage.getItem(AUTH_TOKEN_NAME);
  
  
    const res = await fetch(ENDPOINTS.sendMessage, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(data),
    });
  
    return res;
  };
  
  export const useSendMessage = () => {
      return useMutation(addMessage, {
        onSuccess: (res) => {
          
          console.log("onSuccess res:",res);
  
          if (res.status===403){
            toast.error('You are not allowed to do that'); 
          } else{
            //toast.success("CV created successfully!")
            console.log("message sent",res.status);
          }
  
        },
    
        onMutate: async (data) => {
          console.log("onMutate data:",data);
          
          
        },
        onError: (_err, data, context) => {
          toast.error("Error sending message!")
          console.log("Error on mutation",_err);
          console.log("Error data:",data);
        },
      });
    };