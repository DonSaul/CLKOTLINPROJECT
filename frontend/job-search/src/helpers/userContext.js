import { createContext, useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AUTH_TOKEN_NAME } from './constants';
import { getEmailFromToken,getFirstNameFromToken,getLastNameFromToken,getRoleFromToken } from './tokenHelper';
import { toast } from 'react-toastify';
import { queryClient } from './queryClient';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const logout = () => {
    localStorage.removeItem(AUTH_TOKEN_NAME);
    setUser(null);
    //setIsLoggedIn(false);
    queryClient.clear();
    toast.success("You are now logged out!");
   
  };

   const isLoggedIn = () => {

    return !!localStorage.getItem(AUTH_TOKEN_NAME);
};

  const login = (data) =>{

    
    setUser({
        email:getEmailFromToken(localStorage.getItem(AUTH_TOKEN_NAME)),
        roleId:getRoleFromToken(localStorage.getItem(AUTH_TOKEN_NAME))
    });
   
  }

  const getUserEmail = () =>{
    return getEmailFromToken(localStorage.getItem(AUTH_TOKEN_NAME));
  }
  const getUserFirstName =() =>{
    return getFirstNameFromToken(localStorage.getItem(AUTH_TOKEN_NAME));
  }
  const getUserLastName =() =>{
    return getLastNameFromToken(localStorage.getItem(AUTH_TOKEN_NAME));
  }


  const getUserRole = (override = false, role) => {
    const authToken = localStorage.getItem(AUTH_TOKEN_NAME);

    if (override) {
      return role;
    }

    if (authToken) {
      try {
        const decodedToken = JSON.parse(atob(authToken.split('.')[1]));
        const userRole = decodedToken.roles[0].authority;
        let roleId = -1;

        if (userRole === 'candidate') {
          roleId = 1;
        } else if (userRole === 'manager') {
          roleId = 2;
        } else if (userRole === 'admin') {
          roleId = 3;
        }

        return roleId || null;
      } catch (error) {
        console.error('Error decoding token:', error);
      }
    }

    return null;
  };

  const contextValue = {
    logout,
    isLoggedIn,
    getUserRole,
    login,
    getUserEmail,
    getUserFirstName,
    getUserLastName
  };

  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  return useContext(AuthContext);
};
