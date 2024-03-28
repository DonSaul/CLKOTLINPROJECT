
import { useNavigate } from "react-router-dom";
import { AUTH_TOKEN_NAME } from "../helpers/constants";

//Only for now, will use provider later
export const logout = () => {
  
    localStorage.removeItem(AUTH_TOKEN_NAME);

 //todo
  };
  

  export const getUserRole = (override=false,role) => {
    const authToken = localStorage.getItem(AUTH_TOKEN_NAME);

    if (override) {
        return role
    }


    if (authToken) {
      try {
        
        const decodedToken = JSON.parse(atob(authToken.split('.')[1]));
  
        //const userRole = decodedToken.role; 
        //const userRole = parseInt(decodedToken.role, 10);
        let roleId=-1
        const userRole = decodedToken.roles[0].authority
        console.log("dec token",decodedToken)
        console.log("user role",userRole)

        if (userRole==="candidate"){
            roleId=1
        } else if (userRole==="manager"){
            roleId=2
        }else if (userRole==="admin"){
            roleId=3
        }

        return roleId || null;
      } catch (error) {
        console.error("Error decoding  token:", error);
      }
    }
  
    return null; 
  };





export const isLoggedIn = () => {

    //return true
    return !!localStorage.getItem(AUTH_TOKEN_NAME);
};

 