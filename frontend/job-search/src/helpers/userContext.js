import { createContext, useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AUTH_TOKEN_NAME } from './constants';
import { isLoggedIn as checkIsLoggedIn } from '../api/login';
import { getEmailFromToken,getRoleFromToken } from './tokenHelper';


const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(checkIsLoggedIn());
 // const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem(AUTH_TOKEN_NAME);
    setUser(null);
    setIsLoggedIn(false);
   
  };

  const login = (data) =>{

    ;
    setUser({
        email:getEmailFromToken(localStorage.getItem(AUTH_TOKEN_NAME)),
        roleId:getRoleFromToken(localStorage.getItem(AUTH_TOKEN_NAME))
    });
    setIsLoggedIn(true);
    

    console.log("You are (probably):",user);
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
    user,
    setUser,
    logout,
    isLoggedIn,
    getUserRole,
    login
  };

  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  return useContext(AuthContext);
};
