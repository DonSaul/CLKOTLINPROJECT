
import { useNavigate } from "react-router-dom";
import { AUTH_TOKEN } from "../helpers/constants";
//Only for now, will use provider later
export const logout = () => {
  
    localStorage.removeItem(AUTH_TOKEN);

 //todo
  };
  

export const isLoggedIn = () => {
    return !!localStorage.getItem(AUTH_TOKEN);
};

 