
const getDecodedToken = (token) => {
    if (!token) {
      throw new Error('Token is required');
    }
  
    try {
      const payload = token.split('.')[1];
      const decodedPayload = atob(payload);
      return JSON.parse(decodedPayload);
    } catch (error) {
      console.error('Error decoding token:', error);
      throw new Error('Failed to decode token');
    }
  };
  
  const getEmailFromToken = (token) => {
    try {
      const decodedToken = getDecodedToken(token);
      return decodedToken.sub || null;
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  };
  
  const getRoleFromToken = (token) => {
    if (token) {
        try {
          const decodedToken = getDecodedToken(token);
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
  
  export { getEmailFromToken, getRoleFromToken };